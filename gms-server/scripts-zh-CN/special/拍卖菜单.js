let n3 = "#fUI/UIWindow/Quest/icon2/0#";//蓝色小箭头
let ttt3 = "#fUI/UIWindow.img/Customized/server/actionLabel/0#";
let ttt4 = "#fUI/UIWindow.img/Customized/server/Bonus/0#";
let ttt5 = "#fUI/UIWindow.img/Customized/server/masterLabel/0#";

let status;
let chr;
let say = "";
let date = new Date();
let year = date.getFullYear();
let month = date.getMonth();
let day = date.getDate();
let hour = date.getHours();
let min = date.getMinutes();
let sec = date.getSeconds();
let week = date.getDay();
let weekCN = getWeekCN(week);

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
            if (chr.getEventInstance() != null) {
                chr.dropMessage(6, "副本中无法使用该功能！");
                cm.dispose();
            } else {
                say = getTitle();
                say += "   #b当前服务器时间:" + "#r" + "<" + year + "年" + (month + 1) + "月" + day + "日  " + hour + ":" + min + ":" + sec + "  " + weekCN + ">" + "\r\n";
                say += "#L1#" + n3 + "#d返回主城#l" + "#L2#" + n3 + "#d万能传送#l" + "#L3#" + n3 + "#d综合服务#l" + "#L4#" + n3 + "#d伤害测试#l\r\n\r\n";
                say += "#L21#" + n3 + "#d金币平台#l" + "#L6#" + n3 + "#r每日必做#l" + "#L7#" + n3 + "#d新手中心#l" + "#L8#" + n3 + "#d排行榜单#l\r\n\r\n";
                say += "#L9#" + n3 + "#d副本中心#l" + "#L10#" + n3 + "#d游戏商店#l" + "#L12#" + n3 + "#d师徒中心#l" + "#L11#" + n3 + "#d义结金兰#l\r\n\r\n";
                say += "#L13#" + n3 + "#d赞助中心#l" + "#L15#" + n3 + "#d装备评分#l" + "#L16#" + n3 + "#d清理背包#l" + "#L19#" + n3 + "#d回收中心#l\r\n\r\n"
                say += "#L99#" + ttt3 + " #r更新公告#l " + "#L20#" + ttt5 + " #d账户信息#l " + "#L100#" + ttt4 + " #b活动中心#l\r\n\r\n"
                say += "#L999#==========================================#d#l\r\n";
                say += "#L1000#清除背包#l\r\n";
                say += "#L1002#学习骑宠#l\r\n";
                say += "#L1003#骑宠道具#l\r\n";
                say += "#L1004#披风道具#l\r\n";
                say += "#L1001#自动挂机#l";
                cm.sendSimple(say, 2);
            }
        } else if (status == 1) {
            switch (selection) {
                case 1: break;
                case 2: break;
                case 4:
                    cm.dispose();
                    cm.warp(993184000);
                    break;
                case 1000:
                    cm.dispose();
                    cm.openNpc("一键删除道具");
                    break;
                case 1001:
                    chr.startAutoAttack(3000, 1, 10, true);
                    cm.dispose();
                    break;
                case 1002:
                    chr.changeSkillLevel(1004, 1);
                    cm.dispose();
                    break;
                case 1003:
                    // 定义需要排除的物品ID数组
                    const excludeIds = [
                        1902005, 1902006, 1902007,
                        1902105, 1902106, 1902107,
                        1902205, 1902206, 1902207,
                        1902305, 1902306, 1902307,
                        1902405, 1902406, 1902407,
                        1902505, 1902506, 1902507,
                        1902605, 1902606, 1902607,
                        1902705, 1902706, 1902707,

                        1902027, 1902040, 1902054,
                        1902060, 1902101, 1902102,
                        1902121, 1902554
                    ];
                    for (let i = 1902700; i <= 1902756; i++) {
                        if (!excludeIds.includes(i)) {
                            cm.gainItem(i, 1);
                        }
                    }
                    cm.dispose();
                    break;
                case 1004:
                    for (let i = 1102063; i <= 1902096; i++) {
                        if (cm.isItemExist(i)) cm.gainItem(i, 1);
                    }
                    cm.dispose();
                    break;
                default: break;
            }
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

function getWeekCN(week) {
    let weekCN = "";
    switch (week) {
        case 0:
            weekCN = "星期日";
            break;
        case 1:
            weekCN = "星期一";
            break;
        case 2:
            weekCN = "星期二";
            break;
        case 3:
            weekCN = "星期三";
            break;
        case 4:
            weekCN = "星期四";
            break;
        case 5:
            weekCN = "星期五";
            break;
        case 6:
            weekCN = "星期六";
            break;
        default:
            break;
    }
    return weekCN;
}