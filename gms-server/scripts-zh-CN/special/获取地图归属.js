var status;
var chr;

function start() {
    status = -1;
    action(1, 0, 0);
    chr = cm.getPlayer();
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
            cm.sendGetNumber("输入你想获取地图归属的时间，单位：小时", 1, 1, 8);
        } else if (status == 1) {
            var times = selection;
            if (times > 0) {
                if (chr.getMap().claimOwnership(chr, times)) {
                    chr.dropMessage(5, "您已获得地图归属权，您的队伍将有权独自在此狩猎，除非离开地图或挂机" + times + "小时以上");
                } else {
                    chr.dropMessage(5, "该地图已归属于其他玩家");
                }
            }
            cm.dispose();
        }
    }
}