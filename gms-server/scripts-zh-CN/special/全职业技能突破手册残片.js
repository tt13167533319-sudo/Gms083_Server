let status;
let chr;
let say = "";
let sel;
let selList;
let itemId = 2430044;
let needNum = [10, 20, 30, 40];
let needLevel = [10, 30, 70, 120];
let rate = [3, 5, 7, 10];
let specialItem = [2430034, 2430035, 2430036, 2430037, 2430038];

function start() {
    chr = cm.getPlayer();
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && type > 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }

        if (status == 0) {
            say = getTitle();
            say += "#d[道具信息]   #i" + itemId + "#    #b#z" + itemId + "# #rx #c" + itemId + "#\r\n";
            say += "#L0#" + getIcon(1) + "      #d抽取一转突破技能手册\t\t消耗#i" + itemId + "# #rx " + needNum[0] + "\r\n\r\n";
            say += "\t#i2430034##i2430035##i2430036##i2430037##i2430038##r x 1 #d(" + rate[0] + "%)\t#fUI/UIWindow.img/QuestIcon/5/0##r x 1 #d(" + (100 - rate[0]) + "%)#l\r\n\r\n";
            say += "#L1#" + getIcon(2) + "    #d抽取二转突破技能手册\t\t消耗#i" + itemId + "# #rx " + needNum[1] + "\r\n\r\n";
            say += "\t#i2430034##i2430035##i2430036##i2430037##i2430038##r x 2 #d(" + rate[1] + "%)\t#fUI/UIWindow.img/QuestIcon/5/0##r x 1 #d(" + (100 - rate[1]) + "%)#l\r\n\r\n";
            say += "#L2#" + getIcon(3) + "   #d抽取三转突破技能手册\t\t消耗#i" + itemId + "# #rx " + needNum[2] + "\r\n\r\n";
            say += "\t#i2430034##i2430035##i2430036##i2430037##i2430038##r x 3 #d(" + rate[2] + "%)\t#fUI/UIWindow.img/QuestIcon/5/0##r x 1 #d(" + (100 - rate[2]) + "%)#l\r\n\r\n";
            say += "#L3#" + getIcon(4) + "   #d抽取四转突破技能手册\t\t消耗#i" + itemId + "# #rx " + needNum[3] + "\r\n\r\n";
            say += "\t#i2430034##i2430035##i2430036##i2430037##i2430038##r x 4 #d(" + rate[3] + "%)\t#fUI/UIWindow.img/QuestIcon/5/0##r x 1 #d(" + (100 - rate[3]) + "%)#l\r\n\r\n";
            say += "#L4#test#l";
            cm.sendSimple(say, 2);
        } else if (status == 1) {
            sel = selection;
            say = getTitle();
            if (sel == 4) {
                for (let i = 0; i < skillBreakList[3].length; i++) {
                    cm.gainItem(skillBreakList[3][i].itemid, 5);
                }
            }
            if (!cm.hasItem(itemId, needNum[sel])) {
                cm.sendOk(say + "#d#i" + itemId + ":#道具不足 #r" + needNum[sel] + " #d个，请确认后再试", 2);
            } else if (chr.getLevel() < needLevel[sel]) {
                cm.sendOk(say + "#d等级不足 #r" + needLevel[sel] + "#d，无法抽取", 2);
            } else if (chr.getFreeSlots(2) < 1) {
                cm.sendOk(say + "#d背包#r消耗栏#d空间不足，无法抽取，请整理后再试", 2);
            } else {
                let rand = Math.random() * 100;
                let drawItemId;
                let drawItemNum;
                cm.gainItem(itemId, -needNum[sel]);
                if (rand < rate[sel]) {
                    rand = Math.floor(Math.random() * (rate.length + 1));
                    drawItemId = specialItem[rand];
                    drawItemNum = sel + 1;
                } else {
                    selList = skillBreakList[sel];
                    let item = drawItem(selList);
                    drawItemId = item.itemid;
                    drawItemNum = item.num;
                    if (drawItemId == 2431117) cm.gainItem(2431118, 1);
                    else if (drawItemId == 2431118) cm.gainItem(2431117, 1);
                }

                cm.gainItem(drawItemId, drawItemNum);
                cm.playSound("Romio/discovery");
                let item = cm.makeItemWithId(drawItemId);
                cm.itemMessage("[突破手册] : " + " x" + drawItemNum + "  是玩家 < " + chr.getName() + " > 抽取的道具  恭喜TA吧~", item);
            }
            cm.dispose();
        } else {

            cm.dispose();
        }
    }
}

function getTitle() {
    let icon = "#fUI/UIWindow.img/Customized/title#";
    let say = "\t\t\t" + icon + "\t\t\t\t#d#e童心大家庭\t\t\t\t" + icon + "\r\n\r\n#n";
    return say;
}

function getIcon(type) {
    return "#fUI/UIWindow.img/Skill/Tab/enabled/" + type + "#";
}

function drawItem(items) {
    // 计算总权重
    const totalWeight = items.reduce((sum, item) => sum + (item.chance || 0), 0);
    // if (player.isGm()) player.dropMessage(1, totalWeight);
    // 生成随机数
    let random = Math.random() * totalWeight;

    // 根据权重累积判断
    for (const item of items) {
        const weight = item.chance || 0;
        if (weight <= 0) continue;

        if (random < weight) {
            return item;
        }
        random -= weight;
    }

    // 理论上不会执行到这里，但为了确保函数有返回值
    return items[items.length - 1];
}

let skillBreakList = [
    [
        //战士一转通用
        { itemid: 2431000, chance: 1000, num: 1 },//强力攻击
        { itemid: 2431001, chance: 1000, num: 1 },//群体攻击
        //魔法师一转通用
        { itemid: 2431037, chance: 1000, num: 1 },//魔法盾
        { itemid: 2431038, chance: 1000, num: 1 },//魔法双击
        //弓箭手一转通用
        { itemid: 2431064, chance: 1000, num: 1 },//强力箭
        { itemid: 2431065, chance: 1000, num: 1 },//二连射
        //飞侠一转通用
        { itemid: 2431083, chance: 1000, num: 1 },//二连击
        { itemid: 2431084, chance: 1000, num: 1 },//双飞斩
        //海盗一转通用
        { itemid: 2431102, chance: 1000, num: 1 },//半月踢
        { itemid: 2431103, chance: 1000, num: 1 },//双弹射击
    ],

    [
        //剑客
        { itemid: 2431002, chance: 208, num: 1 },//终极剑
        { itemid: 2431003, chance: 208, num: 1 },//终极斧
        { itemid: 2431004, chance: 416, num: 1 },//愤怒之火
        //准骑士
        { itemid: 2431012, chance: 208, num: 1 },//终极剑
        { itemid: 2431013, chance: 208, num: 1 },//终极钝器
        { itemid: 2431014, chance: 416, num: 1 },//吸收之盾
        //枪战士
        { itemid: 2431027, chance: 208, num: 1 },//终极枪
        { itemid: 2431028, chance: 208, num: 1 },//终极矛
        { itemid: 2431029, chance: 416, num: 1 },//极限防御
        //火毒2转
        { itemid: 2431020, chance: 416, num: 1 },//精神力
        { itemid: 2431040, chance: 416, num: 1 },//火焰箭
        //冰雷2转
        { itemid: 2431039, chance: 416, num: 1 },//精神力
        { itemid: 2431048, chance: 416, num: 1 },//雷电术
        //牧师
        { itemid: 2431054, chance: 416, num: 1 },//祝福
        { itemid: 2431055, chance: 416, num: 1 },//圣箭术
        //猎手
        { itemid: 2431066, chance: 416, num: 1 },//终极弓
        { itemid: 2431067, chance: 416, num: 1 },//爆炸箭
        //弩弓手
        { itemid: 2431075, chance: 416, num: 1 },//终极弩
        { itemid: 2431076, chance: 416, num: 1 },//穿透箭
        //刺客
        { itemid: 2431085, chance: 416, num: 1 },//精准暗器
        { itemid: 2431086, chance: 416, num: 1 },//强力投掷
        //侠客
        { itemid: 2431094, chance: 416, num: 1 },//精准短刀
        { itemid: 2431095, chance: 416, num: 1 },//回旋斩
        //拳手
        { itemid: 2431104, chance: 416, num: 1 },//百裂拳
        { itemid: 2431105, chance: 416, num: 1 },//精准拳
        //火枪手
        { itemid: 2431113, chance: 416, num: 1 },//精准枪
        { itemid: 2431114, chance: 416, num: 1 },//快枪手
    ],

    [
        //勇士
        { itemid: 2431005, chance: 278, num: 1 },//斗气集中
        { itemid: 2431006, chance: 278, num: 1 },//防御崩坏
        { itemid: 2431007, chance: 278, num: 1 },//虎咆哮
        //骑士
        { itemid: 2431015, chance: 278, num: 1 },//属性攻击
        { itemid: 2431016, chance: 139, num: 3 },//属性之剑
        { itemid: 2431017, chance: 139, num: 3 },//属性钝器
        { itemid: 2431022, chance: 278, num: 1 },//防御崩坏
        //龙骑士
        { itemid: 2431030, chance: 139, num: 1 },//枪连击
        { itemid: 2431031, chance: 139, num: 1 },//矛连击
        { itemid: 2431032, chance: 278, num: 1 },//龙咆哮
        { itemid: 2431033, chance: 278, num: 1 },//力量崩坏
        //火毒3转
        { itemid: 2431021, chance: 278, num: 1 },//魔力激化
        { itemid: 2431042, chance: 278, num: 1 },//末日烈焰
        { itemid: 2431043, chance: 278, num: 1 },//火毒合击
        //冰雷3转
        { itemid: 2431041, chance: 278, num: 1 },//魔力激化
        { itemid: 2431049, chance: 278, num: 1 },//冰咆哮
        { itemid: 2431050, chance: 278, num: 1 },//冰雷合击
        //祭司
        { itemid: 2431056, chance: 278, num: 1 },//圣箭术
        { itemid: 2431057, chance: 278, num: 1 },//神圣祈祷
        { itemid: 2431058, chance: 278, num: 1 },//圣光
        //射手
        { itemid: 2431068, chance: 278, num: 1 },//烈火箭
        { itemid: 2431069, chance: 278, num: 1 },//箭雨
        { itemid: 2431070, chance: 278, num: 1 },//箭扫射
        //游侠
        { itemid: 2431077, chance: 278, num: 1 },//寒冰箭
        { itemid: 2431078, chance: 278, num: 1 },//升龙弩
        { itemid: 2431079, chance: 278, num: 1 },//箭扫射
        //无影人
        { itemid: 2431087, chance: 278, num: 1 },//聚财术
        { itemid: 2431088, chance: 278, num: 1 },//影分身
        { itemid: 2431089, chance: 278, num: 1 },//多重飞镖
        //独行侠
        { itemid: 2431096, chance: 278, num: 1 },//回旋斩
        { itemid: 2431097, chance: 278, num: 1 },//金钱护盾
        { itemid: 2431098, chance: 278, num: 1 },//分身术
        //斗士
        { itemid: 2431106, chance: 278, num: 1 },//百裂拳
        { itemid: 2431107, chance: 278, num: 1 },//能量获得
        { itemid: 2431108, chance: 278, num: 1 },//碎石乱击
        //大副
        { itemid: 2431115, chance: 278, num: 1 },//三连射杀
        { itemid: 2431116, chance: 278, num: 1 },//章鱼炮台
        { itemid: 2431117, chance: 139, num: 1 },//烈焰喷射
        { itemid: 2431118, chance: 139, num: 1 },//寒冰喷射
    ],

    [
        //英雄
        { itemid: 2431008, chance: 208, num: 1 },//稳如泰山
        { itemid: 2431009, chance: 208, num: 1 },//进阶斗气
        { itemid: 2431010, chance: 208, num: 1 },//轻舞飞扬
        { itemid: 2431011, chance: 208, num: 1 },//葵花宝典
        //圣骑士
        { itemid: 2431023, chance: 208, num: 1 },//寒冰掌
        { itemid: 2431018, chance: 208, num: 1 },//稳如泰山
        { itemid: 2431024, chance: 104, num: 1 },//圣灵之剑
        { itemid: 2431025, chance: 104, num: 1 },//圣灵之锤
        { itemid: 2431026, chance: 208, num: 1 },//连环环破
        //黑骑士
        { itemid: 2431034, chance: 208, num: 1 },//阿基里斯
        { itemid: 2431019, chance: 208, num: 1 },//稳如泰山
        { itemid: 2431035, chance: 208, num: 1 },//恶龙附身
        { itemid: 2431036, chance: 208, num: 1 },//灵魂祝福
        //火毒4转
        { itemid: 2431044, chance: 208, num: 1 },//火凤球
        { itemid: 2431045, chance: 208, num: 1 },//终极无限
        { itemid: 2431046, chance: 208, num: 1 },//连环爆破
        { itemid: 2431047, chance: 208, num: 1 },//天降落星
        //冰雷4转
        { itemid: 2431051, chance: 208, num: 1 },//冰凤球
        { itemid: 2431124, chance: 208, num: 1 },//终极无限
        { itemid: 2431052, chance: 208, num: 1 },//链环闪电
        { itemid: 2431053, chance: 208, num: 1 },//落冰霜破
        //主教
        { itemid: 2431059, chance: 104, num: 1 },//圣灵之盾
        { itemid: 2431060, chance: 104, num: 1 },//复活术
        { itemid: 2431061, chance: 208, num: 1 },//终极无限
        { itemid: 2431062, chance: 208, num: 1 },//光芒飞箭
        { itemid: 2431063, chance: 208, num: 1 },//圣光普照
        //神射手
        { itemid: 2431071, chance: 208, num: 1 },//稳如泰山
        { itemid: 2431072, chance: 208, num: 1 },//火眼晶晶
        { itemid: 2431073, chance: 208, num: 1 },//暴风箭雨
        { itemid: 2431074, chance: 208, num: 1 },//集中精力
        //箭神
        { itemid: 2431080, chance: 208, num: 1 },//神弩手
        { itemid: 2431081, chance: 208, num: 1 },//无尽神弩
        { itemid: 2431125, chance: 208, num: 1 },//火眼晶晶
        { itemid: 2431082, chance: 208, num: 1 },//刺眼箭
        //隐士
        { itemid: 2431090, chance: 208, num: 1 },//假动作
        { itemid: 2431091, chance: 208, num: 1 },//暗器伤人
        { itemid: 2431092, chance: 208, num: 1 },//三连环光击破
        { itemid: 2431093, chance: 208, num: 1 },//武器用毒液
        //侠盗
        { itemid: 2431099, chance: 208, num: 1 },//暗杀
        { itemid: 2431100, chance: 208, num: 1 },//一出双击
        { itemid: 2431101, chance: 208, num: 1 },//烟雾弹
        { itemid: 2431126, chance: 208, num: 1 },//武器用毒液
        //冲锋队长
        { itemid: 2431109, chance: 208, num: 1 },//潜龙出渊
        { itemid: 2431110, chance: 208, num: 1 },//超级变身
        { itemid: 2431111, chance: 208, num: 1 },//金手指
        { itemid: 2431112, chance: 208, num: 1 },//极速领域
        //船长
        { itemid: 2431119, chance: 208, num: 1 },//导航辅助
        { itemid: 2431120, chance: 104, num: 1 },//武装
        { itemid: 2431121, chance: 104, num: 1 },//金属风暴
        { itemid: 2431122, chance: 208, num: 1 },//急速射
        { itemid: 2431123, chance: 208, num: 1 },//重量炮击
    ]
];