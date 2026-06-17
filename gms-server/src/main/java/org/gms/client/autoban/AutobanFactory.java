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

package org.gms.client.autoban;

import org.gms.client.Character;
import org.gms.config.GameConfig;
import org.gms.net.server.Server;
import org.gms.util.PacketCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.concurrent.TimeUnit.*;

/**
 * @author kevintjuh93
 */
public enum AutobanFactory {
    MOB_COUNT,
    GENERAL(10,MINUTES.toMillis(10)),
    FIX_DAMAGE,
    DAMAGE_HACK(10, MINUTES.toMillis(30)),
    DISTANCE_HACK(5, MINUTES.toMillis(30)),
    PORTAL_DISTANCE(2, MINUTES.toMillis(5)),
    PACKET_EDIT,
    ACC_HACK,
    CREATION_GENERATOR,
    HIGH_HP_HEALING,
    FAST_HP_HEALING(15),
    FAST_MP_HEALING(20, SECONDS.toMillis(30)),
    GACHA_EXP,
    TUBI(20, SECONDS.toMillis(15)),
    SHORT_ITEM_VAC,
    ITEM_VAC,
    FAST_ITEM_PICKUP(5, SECONDS.toMillis(30)),
    FAST_ATTACK(3, MINUTES.toMillis(5)),
    MPCON(25, SECONDS.toMillis(30)),
    MISS_GODMODE(3, MINUTES.toMillis(30)), // 累计3次MISS触发封号，30分钟内点数衰减
    PICKUPITEM_FARAWAY(5, MINUTES.toMillis(5)),
    MONSTERPULL(5, MINUTES.toMillis(1)),;

    private static final Logger log = LoggerFactory.getLogger(AutobanFactory.class);
    private static final Set<Integer> ignoredChrIds = new HashSet<>();

    private final int points;
    private final long expiretime;

    AutobanFactory() {
        this(1, -1);
    }

    AutobanFactory(int points) {
        this.points = points;
        this.expiretime = -1;
    }

    AutobanFactory(int points, long expire) {
        this.points = points;
        this.expiretime = expire;
    }

    public int getMaximum() {
        return points;
    }

    public long getExpire() {
        return expiretime;
    }

    public void addPoint(AutobanManager ban, String reason) {
        ban.addPoint(this, reason);
    }

    public void alert(Character chr, String reason) {
        if (GameConfig.getServerBoolean("use_auto_ban")) {
            if (chr != null && isIgnored(chr.getId())) {
                return;
            }
            Server.getInstance().broadcastGMMessage((chr != null ? chr.getWorld() : 0), PacketCreator.sendYellowTip((chr != null ? Character.makeMapleReadable(chr.getName()) : "") + " [自动封号] 可能使用非法程序 [" + this.name() + "] 原因" + reason));
        }
        if (GameConfig.getServerBoolean("use_auto_ban_log")) {
//            final String chrName = chr != null ? Character.makeMapleReadable(chr.getName()) : "";
//            log.info("自动封号(警告) - 角色 {} 原因 [{}]  [{}]", chrName, this.name(), reason);
            log.info("自动封号(警告) - 账号 [{}({})]，角色 [{}({})]，地图 [{}({})]，原因 [{}]", chr != null ? chr.getClient().getAccountName() : "", chr != null ? chr.getClient().getAccID() : -1, chr != null ? chr.getName() : "", chr != null ? chr.getId() : -1, chr != null ? chr.getMap().getMapName() : "", chr != null ? chr.getMapId() : -1, reason);
        }
    }

    public void autoban(Character chr, String reason) {
        if (GameConfig.getServerBoolean("use_auto_ban")) {
            chr.autoBan("自动封号 for (" + this.name() + ": " + reason + ")");
//            log.info("自动封号（封号） - 角色 {} 原因 [{}]", chr.getName(), reason);
            log.info("自动封号(封号) - 账号 [{}({})]，角色 [{}({})]，地图 [{}({})]，原因 [{}]", chr != null ? chr.getClient().getAccountName() : "", chr != null ? chr.getClient().getAccID() : -1, chr != null ? chr.getName() : "", chr != null ? chr.getId() : -1, chr != null ? chr.getMap().getMapName() : "", chr != null ? chr.getMapId() : -1, reason);
        }
    }

    /**
     * Toggle ignored status for a character id.
     * An ignored character will not trigger GM alerts.
     *
     * @return new status. true if the chrId is now ignored, otherwise false.
     */
    public static boolean toggleIgnored(int chrId) {
        if (ignoredChrIds.contains(chrId)) {
            ignoredChrIds.remove(chrId);
            return false;
        } else {
            ignoredChrIds.add(chrId);
            return true;
        }
    }

    private static boolean isIgnored(int chrId) {
        return ignoredChrIds.contains(chrId);
    }

    public static Collection<Integer> getIgnoredChrIds() {
        return ignoredChrIds;
    }
}
