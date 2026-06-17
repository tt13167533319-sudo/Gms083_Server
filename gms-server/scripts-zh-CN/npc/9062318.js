let status;
let chr;

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
            cm.dispose();
            cm.openNpc(0, "伤害测试");
        } else if (status == 1) {

            cm.dispose();
        } else {

            cm.dispose();
        }
    }
}