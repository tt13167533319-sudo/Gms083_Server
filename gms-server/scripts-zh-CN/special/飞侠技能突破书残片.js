let status;
let chr;
let itemId = 2430042;
let needNum = 10;
let targetItemId = 2430037;
let targetItemNum = 1;

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
            if (cm.hasItem(itemId, needNum)) {
                if (cm.canHold(targetItemId, targetItemNum)) {
                    cm.playSound("Romio/discovery");
                    cm.gainItem(targetItemId, targetItemNum);
                    cm.gainItem(itemId, -needNum);
                } else cm.sendOk(getTitle() + "#d背包消耗栏空间不足，请整理后再试", 2);
            }
            cm.dispose();
        }
    }
}

function getTitle() {
    let icon = "#fUI/UIWindow.img/Customized/title#";
    let say = "\t\t\t" + icon + "\t\t\t\t#d#e童心大家庭\t\t\t\t" + icon + "\r\n\r\n#n";
    return say;
}