package org.gms.server.maps;

import org.gms.client.Character;
import org.gms.config.GameConfig;
import org.gms.server.life.Monster;
import org.gms.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class SummonSamsara {
    private static final Logger log = LoggerFactory.getLogger(SummonSamsara.class);

    // 輪迴怪物 代碼
    public static int[] REINCARNATION_MOB = new int[]{9999999, 9900090, 9900091, 9900092, 9900093, 9900094, 9900095, 9900096, 9900097, 9900098}; // 怪物代碼, 倍率
    public static double isMonsterSpawn(MapleMap map) { // 回傳生怪數量倍率
        double addition = 1;

        // 判斷地圖有boss 回傳倍率1
        if (map.countBosses() > 0 && isSamsaraMobExist(map)) {
            map.broadcastStringMessage(5,"[轮回碑石] 当前地图存在首领BOSS，请先前往击杀后轮回才会生效");
            return 1;
        }

        if (isSamsaraMobExist(map)) { // 判斷是否有輪迴
            int mobId = getSummonMobId(map);
            float baseRate = GameConfig.getServerFloat("samsara_stele_rate");
            switch (mobId) {
                case 9900090:
                case 9900091:
                case 9900092:
                case 9900093:
                case 9900094:
                case 9900095:
                case 9900096:
                case 9900097:
                case 9900098:
                    addition = addition * baseRate + GameConfig.getServerFloat("samsara_stele_rate_special");
                    break;
                default:
                    addition *= baseRate;
                    break;
            }

        }
        return addition;
    }

    private static int getSummonMobId(MapleMap map){
        for (Integer mobId : REINCARNATION_MOB) {
            if (map.getMonsterById(mobId) != null) return mobId;
        }
        return -1;
    }

    public static boolean isSamsaraMob(int mobid) {
        for (Integer samsaraMobId : REINCARNATION_MOB) {
            if (mobid == samsaraMobId) return true;
        }
        return false;
    }

    public static boolean isSamsaraMobExist(MapleMap map) {
        for (Integer mobId : REINCARNATION_MOB) {
            if (map.getMonsterById(mobId) != null) return true;
        }
        return false;
    }

    public static int getSaramaMobId(int accid) {
        try (Connection con = DatabaseConnection.getConnection()) {
            // 使用?占位符，避免SQL注入
            try (PreparedStatement ps = con.prepareStatement("SELECT samsara_id FROM accounts where id = ?")) {
                // 设置参数（索引从1开始）
                ps.setInt(1, accid);
                try (ResultSet rs = ps.executeQuery()) {
                    // 检查是否有查询结果
                    if (rs.next()) {
                        return rs.getInt("samsara_id");
                    } else {
                        // 没有找到对应记录时的处理（根据业务需求调整）
                        log.error("未找到accid={}对应的samsara_id", accid);
                        return 9999999; // 或其他默认值
                    }
                }
            }
        } catch (SQLException se) {
            // 更通用的错误信息
            log.error("查询samsara_id时发生数据库异常", se);
        }
        return 9999999;
    }

    public static void updateSamsaraId(int accid, int samsaraId) {
        try (Connection con = DatabaseConnection.getConnection()) {
            // 使用?占位符
            try (PreparedStatement ps = con.prepareStatement("UPDATE accounts SET samsara_id = ? WHERE id = ?")) {
                // 设置参数
                ps.setInt(1, samsaraId);
                ps.setInt(2, accid);
                // 执行更新操作（使用executeUpdate()）
                int affectedRows = ps.executeUpdate();
                // 可选：记录更新结果
                if (affectedRows == 0) {
                    log.error("updateSamsaraId更新失败，未找到accid={}的记录", accid);
                }
            }
        } catch (SQLException se) {
            log.error("更新samsara_id时发生数据库异常", se);
        }
    }

}