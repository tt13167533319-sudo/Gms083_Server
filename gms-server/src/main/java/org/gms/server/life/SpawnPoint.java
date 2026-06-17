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
package org.gms.server.life;

import lombok.extern.slf4j.Slf4j;
import org.gms.client.Character;
import org.gms.config.GameConfig;
import org.gms.net.server.Server;
import org.gms.server.maps.MapleMap;
import org.gms.server.maps.SummonSamsara;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class SpawnPoint {
    private final int monster;
    private final int mobTime;
    private final int team;
    private final int fh;
    private final int f;
    private final Point pos;
    private long nextPossibleSpawn;
    private long mobInterval = 5000;
    private final AtomicInteger spawnedMonsters = new AtomicInteger(0);
    private final boolean immobile;
    private boolean denySpawn = false;
    private final MapleMap map;

    public SpawnPoint(final Monster monster, Point pos, boolean immobile, int mobTime, long mobInterval, int team, MapleMap map) {
        this.monster = monster.getId();
        this.pos = new Point(pos);
        this.mobTime = mobTime;
        this.team = team;
        this.fh = monster.getFh();
        this.f = monster.getF();
        this.immobile = immobile;
        this.mobInterval = mobInterval;
        this.map = map;
        this.nextPossibleSpawn = Server.getInstance().getCurrentTime();
    }

    public int getSpawned() {
        return spawnedMonsters.intValue();
    }

    public void setDenySpawn(boolean val) {
        denySpawn = val;
    }

    public boolean getDenySpawn() {
        return denySpawn;
    }

    public boolean shouldSpawn() {
        // 基础条件：禁止刷新或时间未到，直接返回false
        if (denySpawn || mobTime < 0) {
            return false;
        }

        // 获取当前刷新点的怪物信息，判断是否为BOSS
        Monster monsterInfo = LifeFactory.getMonster(monster);
        boolean isBoss = monsterInfo != null && monsterInfo.isBoss();

        // 刷新限制逻辑：
        if (isBoss) {
            // BOSS：最多同时存在1只
            if (spawnedMonsters.get() >= 1) {
                return false;
            }
        } else {
            // 普通怪物：受maxMonsters限制
            if (spawnedMonsters.get() >= GameConfig.getServerInt("samsara_stele_max") || (spawnedMonsters.get() >= (GameConfig.getServerFloat("samsara_stele_rate") + GameConfig.getServerFloat("samsara_stele_rate_special")))) {
                return false;
            }
        }

        // 检查是否到达下次刷新时间
        return nextPossibleSpawn <= Server.getInstance().getCurrentTime();
    }

    public boolean shouldForceSpawn() {
        return mobTime >= 0 && spawnedMonsters.get() <= 0;
    }

    public Monster getMonster() {
        Monster mob = new Monster(LifeFactory.getMonster(monster));
        mob.setPosition(new Point(pos));
        mob.setTeam(team);
        mob.setFh(fh);
        mob.setF(f);
        spawnedMonsters.incrementAndGet();
        mob.addListener(new MonsterListener() {
            @Override
            public void monsterKilled(int aniTime) {
                nextPossibleSpawn = Server.getInstance().getCurrentTime();
                if(!mob.isBoss()) {
                    if (mobTime > 0) {
                        nextPossibleSpawn += SECONDS.toMillis(mobTime);
                    } else {
                        if (SummonSamsara.isSamsaraMobExist(map)) nextPossibleSpawn = Server.getInstance().getCurrentTime() + GameConfig.getServerShort("samsara_stele_time");
                        else nextPossibleSpawn += GameConfig.getServerLong("respawn_interval");
                    }
                }else {
                    if (mobTime > 0) {
                        nextPossibleSpawn += SECONDS.toMillis(mobTime);
                    } else {
                        nextPossibleSpawn += GameConfig.getServerLong("respawn_interval");
                    }
                }
                Date date = new Date(nextPossibleSpawn);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//                if(MobId.isAreaBoss(mob.getId())) {
//                    log.info("nextPossibleSpawn == " + nextPossibleSpawn);
//                    log.info("Boss " + mob.getId() + " will respawned at " + sdf.format(date));
//                    log.info("mobID {},//{}, mobTime:{}, {}, {}", mob.getId(), mob.getName(), mobTime, map.getId(), map.getMapName());
//                }
                spawnedMonsters.decrementAndGet();
            }

            @Override
            public void monsterDamaged(Character from, int trueDmg) {}

            @Override
            public void monsterHealed(int trueHeal) {}
        });

        //============================================================================================================================
        // 动态计算新的重生间隔
        long newInterval;
        if (SummonSamsara.isSamsaraMobExist(map)) {
            // 存在轮回石碑怪物，使用特殊间隔
            newInterval = GameConfig.getServerShort("samsara_stele_time");
        } else {
            // 默认间隔
            newInterval = GameConfig.getServerLong("respawn_interval");
        }

        // 调用Server的方法更新全局重生任务间隔
        Server.getInstance().updateRespawnInterval(newInterval);
        //============================================================================================================================

        if (mobTime == 0) {
            if(SummonSamsara.isSamsaraMobExist(map)) nextPossibleSpawn = Server.getInstance().getCurrentTime() + GameConfig.getServerShort("samsara_stele_time");
            else nextPossibleSpawn = Server.getInstance().getCurrentTime() + GameConfig.getServerLong("respawn_interval");
        }
        return mob;
    }

    public int getMonsterId() {
        return monster;
    }

    public Point getPosition() {
        return pos;
    }

    public final int getF() {
        return f;
    }

    public final int getFh() {
        return fh;
    }

    public int getMobTime() {
        return mobTime;
    }

    public int getTeam() {
        return team;
    }
}
