let status;
let chr;
let say = "";
let sel;
let itemId = 2430034;

let skillBreakList = [
    //技能ID，最小等级，最大等级，需要数量，成功率，需要等级
    //一转通用
    [1001004, 20, 22, 1, 100, 10],//强力攻击
    [1001005, 20, 22, 1, 100, 10],//群体攻击

    //剑客
    [1100002, 30, 32, 2, 90, 30],//终极剑
    [1100003, 30, 32, 2, 90, 30],//终极斧
    [1101006, 20, 22, 2, 90, 30],//愤怒之火
    //勇士
    [1111002, 28, 30, 3, 70, 70],//斗气集中
    [1111007, 20, 22, 3, 70, 70],//防御崩坏
    [1111008, 30, 32, 3, 70, 70],//虎咆哮
    //英雄
    [1121002, 30, 32, 4, 50, 120],//稳如泰山
    [1120003, 30, 32, 4, 50, 120],//进阶斗气
    [1121008, 30, 32, 4, 50, 120],//轻舞飞扬
    [1121010, 30, 32, 4, 50, 120],//葵花宝典

    //准骑士
    [1200002, 30, 32, 2, 90, 30],//终极剑
    [1200003, 30, 32, 2, 90, 30],//终极钝器
    [1201006, 20, 22, 2, 90, 30],//吸收之盾
    //骑士
    [1211002, 30, 32, 3, 70, 70],//属性攻击
    [1211003, 30, 32, 3, 70, 70],//烈焰之剑
    [1211004, 30, 32, 3, 70, 70],//烈焰钝器
    [1211005, 30, 32, 3, 70, 70],//寒冰之剑
    [1211006, 30, 32, 3, 70, 70],//寒冰钝器
    [1211007, 30, 32, 3, 70, 70],//雷电之剑
    [1211008, 30, 32, 3, 70, 70],//雷电钝器
    [1211009, 20, 22, 3, 70, 70],//防御崩坏
    //圣骑士
    [1220006, 30, 32, 4, 50, 120],//寒冰掌
    [1221002, 30, 32, 4, 50, 120],//稳如泰山
    [1221003, 20, 22, 4, 50, 120],//圣灵之剑
    [1221004, 20, 22, 4, 50, 120],//圣灵之锤
    [1221009, 30, 32, 4, 50, 120],//连环环破

    //枪战士
    [1300002, 30, 32, 2, 90, 30],//终极枪
    [1300003, 30, 32, 2, 90, 30],//终极矛
    [1301006, 20, 22, 2, 90, 30],//极限防御
    //龙骑士
    [1311001, 30, 32, 3, 70, 70],//枪连击
    [1311002, 30, 32, 3, 70, 70],//矛连击
    [1311006, 30, 32, 3, 70, 70],//龙咆哮
    [1311007, 20, 22, 3, 70, 70],//力量崩坏
    //黑骑士
    [1320005, 30, 32, 4, 50, 120],//阿基里斯
    [1321002, 30, 32, 4, 50, 120],//稳如泰山
    [1320006, 30, 32, 4, 50, 120],//恶龙附身
    [1320009, 25, 27, 4, 50, 120],//灵魂祝福

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