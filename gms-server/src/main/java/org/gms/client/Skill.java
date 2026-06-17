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
package org.gms.client;

import org.gms.server.StatEffect;
import org.gms.server.life.Element;

import java.util.ArrayList;
import java.util.List;

public class Skill {
    private final int id;
    private final List<StatEffect> effects = new ArrayList<>();
    private Element element;
    private int animationTime;
    private final int job;
    private boolean action;
    private String name;

    public Skill(int id) {
        this.id = id;
        this.job = id / 10000;
    }

    public int getId() {
        return id;
    }

    public StatEffect getEffect(int level) {
        return effects.get(level - 1);
    }

    public int getBreakMaxLevel() {
        return effects.size();
    }

    public int getMaxLevel() {
        if (isBreakSkill()) {
            if (id == 2301005 || id == 4201005 || id == 5001001) return effects.size() - 4;
            else if (id == 2321005 || id == 2321006 || id == 5221006 || id == 5221004) return effects.size() - 1;
            else return effects.size() - 2;
        }
        else return effects.size();
    }

    public boolean isFourthJob() {
        if (job == 2212) {
            return false;
        }
        if (id == 22170001 || id == 22171003 || id == 22171004 || id == 22181002 || id == 22181003) {
            return true;
        }
        return job % 10 == 2;
    }

    public boolean isBreakSkill() {
        return switch (id) {
            case
                    1001004, 1001005, //战士 100

                    1100002, 1100003, 1101006, //剑客 110
                    1111002, 1111007, 1111008, //勇士111
                    1121002, 1120003, 1121008, 1121010, //英雄112

                    1200002, 1200003, 1201006, //准骑士120
                    1211002, 1211003, 1211004, 1211005, 1211006, 1211007, 1211008, 1211009, //骑士121
                    1220006, 1221002, 1221003, 1221004, 1221009, //圣骑士122

                    1300002, 1300003, 1301006, //枪战士130
                    1311001, 1311002, 1311006, 1311007, //龙骑士131
                    1320005, 1321002, 1320006, 1320009, //黑骑士132

                    2001002, 2001005, //魔法师200

                    2101001, 2101004,//火毒210
                    2110001, 2111002, 2111006,//火毒211
                    2121003, 2121004, 2121006, 2121007,//火毒212

                    2201001, 2201005,//冰雷220
                    2210001, 2211002, 2211006,//冰雷221
                    2221003, 2221004, 2221006, 2221007,//冰雷222

                    2301004, 2301005,//牧师230
                    2311003, 2311004,//祭祀231
                    2321005, 2321006, 2321004, 2321007, 2321008,//主教232

                    3000001, 3001005, //弓箭手300

                    3100001, 3101005, //猎手310
                    3111003, 3111004, 3111006, //射手311
                    3120005, 3121002, 3121004, 3121008, //神射手312

                    3200001, 3201005,//弩弓手320
                    3211003, 3211004, 3211006,//游侠321
                    3220004, 3221003, 3221002, 3221006, //箭神322

                    4001334, 4001344,//飞侠400

                    4100000, 4100001,//刺客410
                    4111001, 4111002, 4111005,//无影人411
                    4120002, 4121006, 4121007, 4120005,//隐士412

                    4200000, 4201005, //侠客420
                    4211004, 4211005, //独行客421
                    4221001, 4221007, 4221006, 4220005, //侠盗422

                    5001002, 5001003, //海盗500

                    5001001, 5100001, //拳手510
                    5110001, 5111006, //斗士511
                    5121001, 5121003, 5121004, 5121009, //冲锋队长512

                    5200000, 5201001,//火枪手520
                    5210000, 5211001, 5211004, 5211005,//大副521
                    5220011, 5221006, 5221004, 5221007, 5221008//船长522
                    -> true;
            default -> false;
        };
    }

    public void setElement(Element elem) {
        element = elem;
    }

    public Element getElement() {
        return element;
    }

    public int getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(int time) {
        animationTime = time;
    }

    public void incAnimationTime(int time) {
        animationTime += time;
    }

    public boolean isBeginnerSkill() {
        return id % 10000000 < 10000;
    }

    public void setAction(boolean act) {
        action = act;
    }

    public boolean getAction() {
        return action;
    }

    public void addLevelEffect(StatEffect effect) {
        effects.add(effect);
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }
}