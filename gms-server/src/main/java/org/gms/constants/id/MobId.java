package org.gms.constants.id;

import java.util.ArrayList;
import java.util.List;

public class MobId {
    public static final int ARPQ_BOMB = 9300166;
    public static final int GIANT_CAKE = 9400606;
    public static final int TRANSPARENT_ITEM = 9300216;

    public static final int GREEN_MUSHROOM = 1110100;
    public static final int DEJECTED_GREEN_MUSHROOM = 1110130;
    public static final int GREEN_MUSHROOM_QUEST = 9101000;
    public static final int ZOMBIE_MUSHROOM = 2230101;
    public static final int ANNOYED_ZOMBIE_MUSHROOM = 2230131;
    public static final int ZOMBIE_MUSHROOM_QUEST = 9101001;
    public static final int GHOST_STUMP = 1140100;
    public static final int SMIRKING_GHOST_STUMP = 1140130;
    public static final int GHOST_STUMP_QUEST = 9101002;

    public static final int PAPULATUS_CLOCK = 8500001;
    public static final int HIGH_DARKSTAR = 8500003;
    public static final int LOW_DARKSTAR = 8500004;

    public static final int PIANUS_R = 8510000;
    public static final int BLOODY_BOOM = 8510100;

    public static final int PINK_BEAN = 8820001;

    public static final int ZAKUM_1 = 8800000;
    public static final int ZAKUM_2 = 8800001;
    public static final int ZAKUM_3 = 8800002;
    public static final int ZAKUM_ARM_1 = 8800003;
    public static final int ZAKUM_ARM_2 = 8800004;
    public static final int ZAKUM_ARM_3 = 8800005;
    public static final int ZAKUM_ARM_4 = 8800006;
    public static final int ZAKUM_ARM_5 = 8800007;
    public static final int ZAKUM_ARM_6 = 8800008;
    public static final int ZAKUM_ARM_7 = 8800009;
    public static final int ZAKUM_ARM_8 = 8800010;

    public static boolean isZakumArm(int mobId) {
        return mobId >= ZAKUM_ARM_1 && mobId <= ZAKUM_ARM_8;
    }

    public static final int HORNTAIL_PREHEAD_LEFT = 8810000;
    public static final int HORNTAIL_PREHEAD_RIGHT = 8810001;
    public static final int HORNTAIL_HEAD_A = 8810002;
    public static final int HORNTAIL_HEAD_B = 8810003;
    public static final int HORNTAIL_HEAD_C = 8810004;
    public static final int HORNTAIL_HAND_LEFT = 8810005;
    public static final int HORNTAIL_HAND_RIGHT = 8810006;
    public static final int HORNTAIL_WINGS = 8810007;
    public static final int HORNTAIL_LEGS = 8810008;
    public static final int HORNTAIL_TAIL = 8810009;
    public static final int DEAD_HORNTAIL_MIN = 8810010;
    public static final int DEAD_HORNTAIL_MAX = 8810017;
    public static final int HORNTAIL = 8810018;
    public static final int SUMMON_HORNTAIL = 8810026;

    public static boolean isDeadHorntailPart(int mobId) {
        return mobId >= DEAD_HORNTAIL_MIN && mobId <= DEAD_HORNTAIL_MAX;
    }

    public static final int SCARLION_STATUE = 9420546;
    public static final int SCARLION = 9420547;
    public static final int ANGRY_SCARLION = 9420548;
    public static final int FURIOUS_SCARLION = 9420549;
    public static final int TARGA_STATUE = 9420541;
    public static final int TARGA = 9420542;
    public static final int ANGRY_TARGA = 9420543;
    public static final int FURIOUS_TARGA = 9420544;

    // Catch mobs
    public static final int TAMABLE_HOG = 9300101;
    public static final int GHOST = 9500197;
    public static final int ARPQ_SCORPION = 9300157;
    public static final int LOST_RUDOLPH = 9500320;
    public static final int KING_SLIME_DOJO = 9300187;
    public static final int FAUST_DOJO = 9300189;
    public static final int MUSHMOM_DOJO = 9300191;
    public static final int POISON_FLOWER = 9300175;
    public static final int P_JUNIOR = 9500336;

    // Friendly mobs
    public static final int WATCH_HOG = 9300102;
    public static final int MOON_BUNNY = 9300061;
    public static final int TYLUS = 9300093;
    public static final int JULIET = 9300137;
    public static final int ROMEO = 9300138;
    public static final int DELLI = 9300162;
    public static final int GIANT_SNOWMAN_LV1_EASY = 9400322;
    public static final int GIANT_SNOWMAN_LV1_MEDIUM = 9400327;
    public static final int GIANT_SNOWMAN_LV1_HARD = 9400332;
    public static final int GIANT_SNOWMAN_LV5_EASY = 9400326;
    public static final int GIANT_SNOWMAN_LV5_MEDIUM = 9400331;
    public static final int GIANT_SNOWMAN_LV5_HARD = 9400336;

    // Dojo
    private static final int DOJO_BOSS_MIN = 9300184;
    private static final int DOJO_BOSS_MAX = 9300215;

    public static boolean isDojoBoss(int mobId) {
        return mobId >= DOJO_BOSS_MIN && mobId <= DOJO_BOSS_MAX;
    }


    public static final int 红蜗牛王 = 2220000;
    public static final int 浮士德 = 5220002;
    public static final int 树妖王 = 3220000;
    public static final int 蘑菇王 = 6130101;
    public static final int 僵尸蘑菇王 = 6300005;
    public static final int 巨型蜈蚣 = 5220004;
    public static final int 大宇 = 3220001;
    public static final int 歇尔夫 = 4220001;
    public static final int 提莫 = 5220003;
    public static final int 吉米拉 = 8220002;
    public static final int 九尾狐 = 7220001;
    public static final int 多尔 = 6220000;
    public static final int 小吃店 = 8220009;
    public static final int 朱诺 = 6220001;
    public static final int 巨居蟹 = 5220001;
    public static final int 肯德熊 = 7220000;
    public static final int 艾利杰 = 8220000;
    public static final int 妖怪禅师 = 7220002;
    public static final int 蝙蝠怪 = 8130100;
    public static final int 驮狼雪人 = 8220001;
    public static final int 皮亚奴斯左 = 8520000;
    public static final int 皮亚奴斯右 = 8510000;
    public static final int 多多 = 8220004;
    public static final int 玄冰独角兽 = 8220005;
    public static final int 雷卡 = 8220006;
    public static final int 大海兽 = 8220003;

    public static boolean isAreaBoss(int mobId) {
        return switch (mobId) {
            case 2220000,//红蜗牛王, mobTime:180, 104000400, 海岸草丛Ⅲ
                 5220002,//浮士德, mobTime:180, 100040106, 巫婆森林Ⅱ
                 3220000,//树妖王, mobTime:180, 101030404, 东部岩山Ⅴ
                 6130101,//蘑菇王, mobTime:30, 100000005, 蘑菇公园Ⅲ
                 6300005,//僵尸蘑菇王, mobTime:30, 105070002, 蘑菇王之墓
                 5220004,//巨型蜈蚣, mobTime:180, 251010102, 八十年药草地
                 3220001,//大宇, mobTime:180, 260010201, 仙人掌爸爸沙漠
                 4220000,//歇尔夫, mobTime:180, 230020100, 海草之塔
                 4220001,//歇尔夫, mobTime:180, 230020100, 海草之塔
                 5220003,//提莫, mobTime:180, 220050000, 丢失的时间<1>
                 8220002,//吉米拉, mobTime:180, 261030000, 研究所地下秘密通道
                 7220001,//九尾狐, mobTime:180, 222010310, 月岭
                 6220000,//多尔, mobTime:180, 107000300, 鳄鱼潭Ⅰ
                 8220009,//小吃店, mobTime:180, 105090310, 龙族之地
                 6220001,//朱诺, mobTime:180, 221040301, 哥雷草原
                 5220000,//巨居蟹, mobTime:180, 110040000, 阳光沙滩
                 5220001,//巨居蟹, mobTime:180, 110040000, 阳光沙滩
                 7220000,//肯德熊, mobTime:180, 250010304, 流浪熊的地盘
                 8220000,//艾利杰, mobTime:180, 200010300, 天空楼梯Ⅱ
                 7220002,//妖怪禅师, mobTime:180, 250010504, 妖怪森林2
                 8130100,//蝙蝠怪, mobTime:180, 105090900, 被诅咒的寺院
                 8220001,//驮狼雪人, mobTime:10, 211040101, 雪人谷
                 8520000,//皮亚奴斯, mobTime:2160, 230040420, 皮亚奴斯洞穴
                 8510000,//皮亚奴斯, mobTime:1440, 230040420, 皮亚奴斯洞穴
                 8220004,//多多, mobTime:60, 270010500, 追忆之路5
                 8220005,//玄冰独角兽, mobTime:60, 270020500, 后悔之路5
                 8220006,//雷卡, mobTime:60, 270030500, 忘却之路5
                 8220003//大海兽 , mobTime:180, 240040401, 大海兽 峡谷
                    -> true;
            default -> false;
        };
    }
}
