
const PacketCreator = Java.type('org.gms.util.PacketCreator')
let status;
let chr;
let sel;
const itemId = 2431016;
const skillLv_min = 30;
const skillLv_max = 32;
const successRate = 70;

function start() {
    chr = im.getPlayer();
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        im.dispose();
    } else {
        if (mode == 0 && type > 0) {
            im.dispose();
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
                    let skillName = im.getSkillName(skillId);
                    if (skillLevel >= skillBreakList[i][2]) maxUse += "#d#L" + i + "##s" + skillId + "# " + format(" ", 30, skillName) + "#rlv." + format(" ", 10, "max") + "#d需要 #i" + itemId + "# #rx " + skillBreakList[i][3] + " #b(" + skillBreakList[i][4] + "%)#l\r\n";
                    else if (skillLevel < skillBreakList[i][1]) noUse += "#d#L" + i + "##s" + skillId + "# " + format(" ", 30, skillName) + "#rlv." + format(" ", 10, skillLevel) + "#d需要 #i" + itemId + "# #rx " + skillBreakList[i][3] + " #b(" + skillBreakList[i][4] + "%)#l\r\n";
                    else canUse += "#d#L" + i + "##s" + skillId + "# " + format(" ", 30, skillName) + "#dlv." + format(" ", 10, skillLevel) + "#d需要 #i" + itemId + "# #rx " + skillBreakList[i][3] + " #b(" + skillBreakList[i][4] + "%)#l\r\n";
                }
            }
            say += canUse + noUse + maxUse;
            im.sendSimple(say, 2);
        } else if (status == 1) {
            sel = selection;
            const skillId = skillBreakList[sel][0];
            const skillLv = chr.getSkillLevel(skillId);
            let canBreak = false;
            if (skillLv >= skillLv_min && skillLv < skillLv_max && im.hasItem(itemId, 1)) canBreak = true;

            if (canBreak) {
                if (chr.breakSkillLevel(skillId, skillLv + 1, skillBreakList[sel][4])) im.gainItem(itemId, -1);
            } else chr.getMap().broadcastMessage(PacketCreator.skillBookResult(chr, 0, 0, false, false));
            im.dispose();
        }
    }
}

let skillBreakList = [
    //技能ID，最小等级，最大等级，需要数量，成功率，需要等级
    [1211003, 30, 32, 3, 70, 70],//烈焰之剑
    [1211005, 30, 32, 3, 70, 70],//寒冰之剑
    [1211007, 30, 32, 3, 70, 70],//雷电之剑
];

function getTitle() {
    let icon = "#fUI/UIWindow.img/Customized/title#";
    let say = "\t\t\t" + icon + "\t\t\t\t#d#e童心大家庭\t\t\t\t" + icon + "\r\n\r\n#n";
    return say;
}

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