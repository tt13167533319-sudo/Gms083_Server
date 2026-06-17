let status;
let chr;
let say = "";

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
            say = "伤害测试中心\r\n\r\n";
            say += "#L0#重置伤害记录#l\r\n";
            cm.sendSimple(say, 2);
        } else if (status == 1) {
            chr.resetTestDamageRecord();
            chr.dropMessage(5, "[伤害测试] 伤害记录已重置")
            cm.dispose();
        } else {

            cm.dispose();
        }
    }
}