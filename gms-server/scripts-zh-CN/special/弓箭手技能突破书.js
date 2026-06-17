let status;
let chr;
let say = "";
let sel;
let itemId = 2430036;

let skillBreakList = [
    //技能ID，最小等级，最大等级，需要数量，成功率，需要等级
    //一转通用
    [3000001, 20, 22, 1, 100, 10],//强力箭
    [3001005, 20, 22, 1, 100, 10],//二连射

    //猎手
    [3100001, 30, 32, 2, 90, 30],//终极弓
    [3101005, 30, 32, 2, 90, 30],//爆炸箭
    //射手
    [3111003, 30, 32, 3, 70, 70],//烈火箭
    [3111004, 30, 32, 3, 70, 70],//箭雨
    [3111006, 30, 32, 3, 70, 70],//箭扫射
    //神射手
    [3120005, 30, 32, 4, 50, 120],//稳如泰山
    [3121002, 30, 32, 4, 50, 120],//火眼晶晶
    [3121004, 30, 32, 4, 50, 120],//暴风箭雨
    [3121008, 30, 32, 4, 50, 120],//集中精力

    //弩弓手
    [3200001, 30, 32, 2, 90, 30],//终极弩
    [3201005, 30, 32, 2, 90, 30],//穿透箭
    //游侠
    [3211003, 30, 32, 3, 70, 70],//寒冰箭
    [3211004, 30, 32, 3, 70, 70],//升龙弩
    [3211006, 30, 32, 3, 70, 70],//箭扫射
    //箭神
    [3220004, 30, 32, 4, 50, 120],//神弩手
    [3221003, 30, 32, 4, 50, 120],//无尽神弩
    [3221002, 30, 32, 4, 50, 120],//火眼晶晶
    [3221006, 30, 32, 4, 50, 120],//刺眼箭

];

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
            say = getTitle() + "#d[道具信息]  #i" + itemId + "#    #b#z" + itemId + "# #rx #c" + itemId + "#\r\n\r\n";
            say += "#d\t选择你要突破的技能：\r\n\r\n";
            let canUse = "";
            let maxUse = "";
            let noUse = "";
            for (let i = 0; i < skillBreakList.length; i++) {
                let skillId = skillBreakList[i][0];
                if (chr.isMyJobSkill(skillId)) {
                    let skillLevel = chr.getSkillLevel(skillId);
                    let skillName = cm.getSkillName(skillId);
                    if (skillLevel >= skillBreakList[i][2]) maxUse += "#d#L" + i + "##s" + skillId + "# " + format(" ", 30, skillName) + "#rlv." + format(" ", 10, "max") + "#d需要 #i" + itemId + "# #rx " + skillBreakList[i][3] + " #b(" + skillBreakList[i][4] + "%)#l\r\n";
                    else if (skillLevel < skillBreakList[i][1]) noUse += "#d#L" + i + "##s" + skillId + "# " + format(" ", 30, skillName) + "#rlv." + format(" ", 10, skillLevel) + "#d需要 #i" + itemId + "# #rx " + skillBreakList[i][3] + " #b(" + skillBreakList[i][4] + "%)#l\r\n";
                    else canUse += "#d#L" + i + "##s" + skillId + "# " + format(" ", 30, skillName) + "#dlv." + format(" ", 10, skillLevel) + "#d需要 #i" + itemId + "# #rx " + skillBreakList[i][3] + " #b(" + skillBreakList[i][4] + "%)#l\r\n";
                }
            }
            say += canUse + noUse + maxUse;
            cm.sendSimple(say, 2);
        } else if (status == 1) {
            sel = selection;
            if (chr.getLevel() >= skillBreakList[sel][5]) {
                if (cm.hasItem(itemId, skillBreakList[sel][3])) {
                    let skillId = skillBreakList[sel][0];
                    let skillLevel = chr.getSkillLevel(skillId);
                    if (skillLevel >= skillBreakList[sel][1] && skillLevel < skillBreakList[sel][2]) {
                        if (chr.breakSkillLevel(skillId, skillLevel + 1, skillBreakList[sel][4])) cm.gainItem(itemId, -skillBreakList[sel][3]);
                    } else {
                        cm.sendOk("您的技能等级不满足要求，无法突破", 2);
                    }
                } else {
                    cm.sendOk("您的道具不足，无法突破", 2);
                }
            } else {
                cm.sendOk("#d您的等级不足 #r" + skillBreakList[sel][5] + " #d，无法突破", 2);
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

// 纯JavaScript实现UTF-8字节长度计算（不依赖TextEncoder）
function getUtf8ByteLength(str) {
    let byteLength = 0;
    for (let i = 0; i < str.length; i++) {
        const charCode = str.charCodeAt(i);
        // ASCII字符（0-127）：1字节
        if (charCode <= 0x7F) {
            byteLength += 1;
        }
        // 中文字符等（0x80-0x7FF）：2字节
        else if (charCode <= 0x7FF) {
            byteLength += 2;
        }
        // 大部分汉字（0x800-0xFFFF）：3字节
        else if (charCode <= 0xFFFF) {
            byteLength += 4;
        }
        // 极少数字符（0x10000及以上）：4字节（一般场景可忽略）
        else {
            byteLength += 4;
        }
    }
    return byteLength;
}

function format(c, length, content) {
    const contentByteLen = getUtf8ByteLength(content); // 内容的字节长度
    let fillStr = "";

    // 计算需要填充的字符数
    if (contentByteLen < length) {
        const needFill = length - contentByteLen;
        fillStr = c.repeat(needFill); // 生成填充字符串
    }

    // 拼接结果（超过长度则直接返回原内容）
    return contentByteLen > length ? content : content + fillStr;
}