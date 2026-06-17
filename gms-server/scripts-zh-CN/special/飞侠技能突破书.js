let status;
let chr;
let say = "";
let sel;
let itemId = 2430037;

let skillBreakList = [
    //技能ID，最小等级，最大等级，需要数量，成功率，需要等级
    //一转通用
    [4001334, 20, 22, 1, 100, 10],//二连击
    [4001344, 20, 22, 1, 100, 10],//双飞斩

    //刺客
    [4100000, 20, 22, 2, 90, 30],//精准暗器
    [4100001, 30, 32, 2, 90, 30],//强力投掷
    //无影人
    [4111001, 20, 22, 3, 70, 70],//聚财术
    [4111002, 30, 32, 3, 70, 70],//影分身
    [4111005, 30, 32, 3, 70, 70],//多重飞镖
    //隐士
    [4120002, 30, 32, 4, 50, 120],//假动作
    [4121006, 30, 32, 4, 50, 120],//暗器伤人
    [4121007, 30, 32, 4, 50, 120],//三连环光击破
    [4120005, 30, 32, 4, 50, 120],//武器用毒液

    //侠客
    [4200000, 20, 22, 2, 90, 30],//精准短刀
    [4201005, 30, 32, 2, 90, 30],//回旋斩
    //独行侠
    [4201005, 32, 34, 3, 70, 80],//回旋斩
    [4211005, 20, 22, 3, 70, 70],//金钱护盾
    [4211004, 30, 32, 3, 70, 70],//分身术
    //侠盗
    [4221001, 30, 32, 4, 50, 120],//暗杀
    [4221007, 30, 32, 4, 50, 120],//一出双击
    [4221006, 30, 32, 4, 50, 120],//烟雾弹
    [4220005, 30, 32, 4, 50, 120],//武器用毒液

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
                    if (skillId == 4201005) {
                        if (skillBreakList[i][5] == 30) skillName += "（二转）";
                        else if (skillBreakList[i][5] == 70) skillName += "（三转）";
                    }
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