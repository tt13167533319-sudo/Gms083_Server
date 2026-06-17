/*
 This file is part of the OdinMS Maple Story Server
 Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
 Matthias Butz <matze@odinms.de>
 Jan Christian Meyer <vimes@odinms.de>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as
 published by the Free Software Foundation version 3 as published by
 the Free Software Foundation. You may not use, modify or distribute
 this program under any other version of the GNU Affero General Public
 License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gms.net.server.handlers.login;

import org.gms.client.Client;
import org.gms.client.DefaultDates;
import org.gms.config.GameConfig;
import org.gms.net.PacketHandler;
import org.gms.net.packet.InPacket;
import org.gms.net.server.Server;
import org.gms.net.server.coordinator.session.Hwid;
import org.gms.util.BCrypt;
import org.gms.util.DatabaseConnection;
import org.gms.util.HexTool;
import org.gms.util.PacketCreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Calendar;

public final class LoginPasswordHandler implements PacketHandler {
    private static final Logger log = LoggerFactory.getLogger(LoginPasswordHandler.class);

    @Override
    public boolean validateState(Client c) {
        return !c.isLoggedIn();
    }

    @Override
    public final void handlePacket(InPacket p, Client c) {
        String remoteHost = c.getRemoteAddress();
        if (remoteHost.contentEquals("null")) {
            c.sendPacket(PacketCreator.getLoginFailed(14));          // thanks Alchemist for noting remoteHost could be null
            return;
        }

        String login = p.readString();
        String pwd = p.readString();
        c.setAccountName(login);

        p.skip(6);   // localhost masked the initial part with zeroes...
        byte[] hwidNibbles = p.readBytes(4);
        Hwid hwid = new Hwid(HexTool.toCompactHexString(hwidNibbles));
        String IP = c.getRemoteAddress();
        int loginok = c.login(login, pwd, hwid);
        if (GameConfig.getServerBoolean("open_login_error_log") && loginok != 0){
            switch (loginok){
                case 3: log.warn("账号 [{}] 尝试登录，hwid[{}]，IP[{}]，但账号已被封禁，登录失败", login,hwid, IP); break;
                case 5: log.warn("账号 [{}] 尝试登录，hwid[{}]，IP[{}]，但账号未注册，登录失败", login,hwid, IP); break;
                case 7: log.info("账号 [{}] 尝试登录，hwid[{}]，IP[{}]，但loggedin = 2，登录失败", login,hwid, IP);; break;
                case 17: log.warn("账号 [{}] 尝试登录，hwid[{}]，IP[{}]，REMOTE_LOGGEDIN(无效主机信息或账号状态异常)，登录失败", login,hwid, IP); break;
                case 13: log.warn("账号 [{}] 尝试登录，hwid[{}]，IP[{}]，REMOTE_REACHED_LIMIT(本地多开或多地登录超限)，登录失败", login,hwid, IP); break;
                case 10: log.warn("账号 [{}] 尝试登录，hwid[{}]，IP[{}]，REMOTE_PROCESSING(服务器处理中或世界/频道满员)，登录失败", login,hwid, IP); break;
                case 16: log.warn("账号 [{}] 尝试登录，hwid[{}]，IP[{}]，MANY_ACCOUNT_ATTEMPTS(尝试次数过多)，登录失败", login,hwid, IP); break;
                case 8: log.warn("账号 [{}] 尝试登录，hwid[{}]，IP[{}]，未知错误(loginok = {})，登录失败", login,hwid,loginok, IP); break;
            }
        }
        if (GameConfig.getServerBoolean("automatic_register") && loginok == 5) {
            // 注册限制
            String hwidString = Hwid.formatHwid(String.valueOf(hwid));
            if (isRegAccount(IP, hwidString)) {
                log.warn("账号 [{}] 尝试自动注册，hwid[{}]，IP[{}]，但已存在注册记录，注册失败", login, hwidString, IP);
                c.sendPacket(PacketCreator.serverNotice(1,"注册账号数量已达上限"));
                c.sendPacket(PacketCreator.getLoginFailed(1));
                return;
            }
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO accounts (name, password, birthday, tempban, ip_reg, hwid_reg) VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) { //Jayd: Added birthday, tempban
                ps.setString(1, login);
                ps.setString(2, GameConfig.getServerBoolean("bcrypt_migration") ? BCrypt.hashpw(pwd, BCrypt.gensalt(12)) : BCrypt.hashpwSHA512(pwd));
                ps.setDate(3, Date.valueOf(DefaultDates.getBirthday()));
                ps.setTimestamp(4, Timestamp.valueOf(DefaultDates.getTempban()));
                ps.setString(5, c.getRemoteAddress());
                ps.setString(6, hwidString);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    rs.next();
                    c.setAccID(rs.getInt(1));
                }
            } catch (SQLException | NoSuchAlgorithmException e) {
                c.setAccID(-1);
                e.printStackTrace();
            } finally {
                loginok = c.login(login, pwd, hwid);
            }
        }

        if (GameConfig.getServerBoolean("bcrypt_migration") && (loginok <= -10)) { // -10 means migration to bcrypt, -23 means TOS wasn't accepted
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("UPDATE accounts SET password = ? WHERE name = ?;")) {
                ps.setString(1, BCrypt.hashpw(pwd, BCrypt.gensalt(12)));
                ps.setString(2, login);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                loginok = (loginok == -10) ? 0 : 23;
            }
        }

        if (c.hasBannedIP() || c.hasBannedMac()) {
            c.sendPacket(PacketCreator.getLoginFailed(3));
            return;
        }
        Calendar tempban = c.getTempBanCalendarFromDB();
        if (tempban != null) {
            if (tempban.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
                c.sendPacket(PacketCreator.getTempBan(tempban.getTimeInMillis(), c.getGReason()));
                return;
            }
        }
        if (loginok == 3) {
            c.sendPacket(PacketCreator.getPermBan(c.getGReason()));//crashes but idc :D
            return;
        } else if (loginok != 0) {
            if (loginok == 13) {
                c.sendPacket(PacketCreator.serverNotice(1,"多开登录受限或多地登录超限"));
                c.sendPacket(PacketCreator.getLoginFailed(1));
            }
            else c.sendPacket(PacketCreator.getLoginFailed(loginok));
            return;
        }
        if (c.finishLogin() == 0) {
            c.checkChar(c.getAccID());
            login(c);
            updateIpRecord(login, IP);
        } else {
            c.sendPacket(PacketCreator.getLoginFailed(7));
        }
    }

    private static void updateIpRecord(String name, String ip) {
        String sql = "UPDATE accounts SET ip = ? WHERE name = ?;";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ip);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("更新IP记录失败", e);
            throw new RuntimeException(e);
        }
    }

    private static void login(Client c) {
        c.sendPacket(PacketCreator.getAuthSuccess(c));//why the fk did I do c.getAccountName()?
        Server.getInstance().registerLoginState(c);
    }

    private static boolean isRegAccount(String ip, String hwid) {
        String sql = "SELECT id FROM accounts WHERE ip_reg = ? or hwid_reg = ?"; // 数据库查询SQL

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ip);
            ps.setString(2, hwid);
            ResultSet rs = ps.executeQuery();

            if (rs.next())  return true;
        } catch (SQLException e) {
            log.error("查询账号注册状态失败", e);
        }

        return false;
    }

    /**
     * 通过账号ID查询GM等级
     * @param accId 账号ID（从Client的c.getAccID()获取）
     * @return GM等级（0=普通玩家，≥1=GM；查询失败返回0）
     */
    private int getAccountGmLevel(int accId) {
        int gmLevel = 0; // 默认普通玩家
        String sql = "SELECT gm_level FROM accounts WHERE id = ?"; // 数据库查询SQL

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, accId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // 读取gm_level字段，若为null则视为0（普通玩家）
                gmLevel = rs.getInt("gm_level");
                if (rs.wasNull()) {
                    gmLevel = 0;
                }
            }
        } catch (SQLException e) {
            // 数据库查询异常，默认视为普通玩家，同时记录日志
            log.error("查询账号ID[{}]的GM等级失败", accId, e);
            gmLevel = 0;
        }
        return gmLevel;
    }

}
