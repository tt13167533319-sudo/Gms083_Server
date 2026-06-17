/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gms.client.autoban;

import org.gms.client.Character;
import org.gms.config.GameConfig;
import org.gms.net.server.Server;
import org.gms.server.life.Monster;
import org.gms.util.PacketCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kevintjuh93
 */
public class AutobanManager {
    private static final Logger log = LoggerFactory.getLogger(AutobanManager.class);

    private final Character chr;
    private final Map<AutobanFactory, Integer> points = new HashMap<>();
    private final Map<AutobanFactory, Long> lastTime = new HashMap<>();
    private int misses = 0;
    private int lastmisses = 0;
    private int samemisscount = 0;
    private final long[] spam = new long[20];
    private final long[] timestamp = new long[20];
    private final byte[] timestampcounter = new byte[20];
    private int monsterMoveCount = 0;
    // 定义坐标范围阈值（单位：游戏内坐标单位，可根据实际情况调整）
    private static int MOVE_RANGE_THRESHOLD = GameConfig.getServerShort("monster_pull_point_range");
    // 阈值的平方（避免开方运算，提高效率）
    private static int THRESHOLD_SQUARED = MOVE_RANGE_THRESHOLD * MOVE_RANGE_THRESHOLD;
    private static int monsterMoveCount_max = GameConfig.getServerByte("monster_pull_point_count");

    public AutobanManager(Character chr) {
        this.chr = chr;
    }

    public void addPoint(AutobanFactory fac, String reason) {
        if (GameConfig.getServerBoolean("use_auto_ban")) {
            if (chr.isBanned()) {
                return;
            }

            if (lastTime.containsKey(fac)) {
                if (lastTime.get(fac) < (Server.getInstance().getCurrentTime() - fac.getExpire())) {
                    points.put(fac, points.get(fac) / 2); //So the points are not completely gone.
                }
            }
            if (fac.getExpire() != -1) {
                lastTime.put(fac, Server.getInstance().getCurrentTime());
            }

            if (points.containsKey(fac)) {
                points.put(fac, points.get(fac) + 1);
            } else {
                points.put(fac, 1);
            }

            if (points.get(fac) >= fac.getMaximum()) {
                if (!chr.isGM()) chr.autoBan(reason);
            } else {
                // 发送玩家提示（警察提示框）
                chr.sendPacket(PacketCreator.sendPolice("检测到异常行为，请珍惜账号，多次违规账号将自动封禁"));
                Server.getInstance().broadcastMessage(chr.getWorld(), PacketCreator.serverNotice(6, "[违规警告] 玩家 <" + chr.getName() + "> 由于违反游戏规则，被系统警告，多次触发警告后将自动封号"));

                // 中断当前操作
//                TimerManager.getInstance().schedule(() -> chr.getClient().disconnect(false, false), 3000);
                chr.enableActions();
            }
        }
        if (GameConfig.getServerBoolean("use_auto_ban_log")) {
            log.info("自动封号(累计点数) - 账号 [{}({})]，角色 [{}({})]，地图 [{}({})]，原因 [{}] [{}] （{}/{}）", chr.getClient().getAccountName(), chr.getClient().getAccID(), chr.getName(), chr.getId(), chr.getMap().getMapName(), chr.getMapId(), fac.name(), reason, points.get(fac), fac.getMaximum());
        }

    }

public void checkMoveMonster(Monster monster, Character chr) {
    Point monsterPos = monster.getPosition();
    int oid = monster.getObjectId();
    // 获取玩家当前位置
    Point playerPos = chr.getPosition();
    if (playerPos == null) {
        return; // 玩家位置无效时不检测
    }

    // 计算怪物与玩家的距离平方（避免开方运算，提高性能）
    int dx = monsterPos.x - playerPos.x;
    int dy = monsterPos.y - playerPos.y;
    long distanceToPlayerSquared = (long) dx * dx + (long) dy * dy;
//    log.info("distanceToPlayerSquared==" + distanceToPlayerSquared + "\r\n");
//    log.info("monsterMoveCount==" + monsterMoveCount + "\r\n");

    // 初始化计数（第一次检测时）
    if (this.monsterMoveCount == 0) {
        this.monsterMoveCount = 1;
        return;
    }

    // 判断怪物是否在玩家周围的阈值范围内
    if (distanceToPlayerSquared <= THRESHOLD_SQUARED) {
        // 在玩家附近，增加计数
        ++this.monsterMoveCount;
        // 超过最大允许次数，判定为异常（吸怪）
        if (this.monsterMoveCount > monsterMoveCount_max) {
            this.monsterMoveCount = 0; // 重置计数，避免重复处罚
            AutobanFactory.MONSTERPULL.addPoint(
                    chr.getAutoBanManager(),
                    "尝试使用吸怪"
            );
        }
    } else {
        // 远离玩家，重置计数（正常移动）
        this.monsterMoveCount = 1;
    }
}

    /**
     * FastAtk checker type
     * 1: CloseRangeDamageHandler.java
     * 2: RangedAttackHandler.java
     * 3: MagicDamageHandler.java
     * 4: SummonDamageHandler.java
     */
    public void checkFastAtk(Character chr, int type) {
        long lastSpam = this.getLastSpam(type);
        long currentTime = System.nanoTime(); // 纳秒级时间戳（精度更高）

        if (lastSpam <= 0) {
            this.spam(type, currentTime); // 存储纳秒级时间戳
        } else {
            // 计算纳秒级时间差（转换为毫秒用于判断，1毫秒=1e6纳秒）
            long timeElapsedNanos = currentTime - lastSpam;
            long timeElapsedMs = timeElapsedNanos / 1_000_000;

            // 判定逻辑：既用毫秒级阈值，又排除极端的0纳秒情况
            if (timeElapsedMs < 300 && timeElapsedNanos > 0) {
                AutobanFactory.FAST_ATTACK.addPoint(this,
                        "攻击频率过高，实际延迟：" + timeElapsedMs + "ms，TYPE：" + type);
            } else if (timeElapsedNanos == 0) {
                // 同一纳秒内的调用（极罕见），仅记录不处罚或轻罚
                log.warn("玩家[{}]出现纳秒级连续调用，可能为高频操作", chr.getName());
                // 可选：累计轻微违规点，避免单次误判
                // AutobanFactory.FAST_ATTACK.addPoint(this, "...", 1); // 点数设为1（低权重）
            }

            this.spam(type, currentTime); // 更新为当前纳秒时间戳
        }
    }

    public void addMiss() {
        this.misses++;
    }

    public void resetMisses() {
        if (this.lastmisses == misses && misses > 6) {
            samemisscount++;
            log.info("重复miss模式检测 - 角色 {} 重复miss次数: {} (累计: {})",
                    Character.makeMapleReadable(chr.getName()), misses, samemisscount);
        }
        if (samemisscount > 4) {
            AutobanFactory.MISS_GODMODE.addPoint(this,
                    "重复miss模式作弊 - 累计" + samemisscount + "次异常重复miss");
        } else if (samemisscount > 0) {
            this.lastmisses = misses;
        }
        this.misses = 0;
    }

    //Don't use the same type for more than 1 thing
    public void spam(int type) {
        this.spam[type] = Server.getInstance().getCurrentTime();
    }

    public void spam(int type, long timestamp) {
        this.spam[type] = timestamp;
    }

    public long getLastSpam(int type) {
        return this.spam[type];
    }


    /**
     * Timestamp checker
     *
     * <code>type</code>:<br>
     * 1: Pet Food<br>
     * 2: InventoryMerge<br>
     * 3: InventorySort<br>
     * 4: SpecialMove<br>
     * 5: UseCatchItem<br>
     * 6: Item Drop<br>
     * 7: Chat<br>
     * 8: HealOverTimeHP<br>
     * 9: HealOverTimeMP<br>
     *
     * @param type type
     * @return Timestamp checker
     */
    public void setTimestamp(int type, long time, int times) {
        if (this.timestamp[type] == time) {
            this.timestampcounter[type]++;
            if (this.timestampcounter[type] >= times) {
                if (GameConfig.getServerBoolean("use_auto_ban")) {
                    chr.getClient().disconnect(false, false);
                }

                log.info("Autoban - Chr {} was caught spamming TYPE {} and has been disconnected", chr, type);
            }
        } else {
            this.timestamp[type] = time;
            this.timestampcounter[type] = 0;
        }
    }
}
