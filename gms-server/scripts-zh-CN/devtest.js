var Icon = Array(
    Array("分割線1", "#fUI/Basic.img/HScr/disabled/prev#"),
    Array("分割線2", "#fUI/Basic.img/HScr/disabled/base#"),
    Array("分割線3", "#fUI/Basic.img/HScr/disabled/next#"),
    Array("icon1", "#fMap/MapHelper.img/weather/witch/0#"),
    Array("icon2", "#fMap/MapHelper.img/weather/witch/3#"),
    Array("icon3", "#fMap/MapHelper.img/weather/witch/4#"),
    Array("icon3", "#fMap/MapHelper.img/weather/witch/5#"),
)

var status = -1

function start() {
    status = -1;
    action(1, 0, 0);
}
var Group = [

    /* 對應選單位置  顯示細項文字 傳送地圖 */

    /*--------------------------*/
    [99, " 弓箭手村 ", 100000000],
    [99, " 魔法森林 ", 101000000],
    [99, " 勇士之村 ", 102000000],
    [99, "維多利亞港", 104000000],
    [99, "  奇幻村  ", 105040300],
    /*--------------------------*/
    [98, "Lv.10 -  100 : #m742000103#", 742000103],
    [98, "Lv.10 -  100 : #m541010010#", 541010010],
    [98, "Lv.10 -  100 : #m702070400#", 702070400],
    /*--------------------------*/
    [97, "第一次同行 : ", 103000000],
    [97, "時空裂縫   : ", 221024500],
    [97, "毒霧森林   : ", 300030100],

]
function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (status == -1) {
        cm.dispose();
        return;
    }
    if (status == 0) {
        x = 0;
        trigger = 0
        cm.sendSimple(getString());
    } else if (status == 1) {
        trigger = 1
        GDP = selection;
        if (GDP >= 90) {
            cm.sendSimple(getString());
            status = 0;
        } else {
            cm.dispose()
            cm.warp(Group[GDP][2])
        }
    }
}
function getString() {
    txt = "\r\n";
    if (trigger >= 1) {
        Open = []
        switch (GDP) {                         /* 如果有更多選單push必須更動 */
            case 99:
                Open.push(1, 0, 0)
                break;
            case 98:
                Open.push(0, 1, 0)
                break;
            case 97:
                Open.push(0, 0, 1)
                break;
        }
        Tag()
        txt += "\r\n\r\n"
        txt += "#L99#[村莊傳送] " + getIcon(Open[0]) + "#l#L98#[練功地圖] " + getIcon(Open[1]) + "#l#L97#[組隊任務] " + getIcon(Open[2]) + "#l\r\n\r\n"
        Tag()
        txt += "\r\n\r\n"
        txt += "" + Icon[3][1] + "" + Icon[4][1] + " " + Icon[5][1] + " " + Icon[6][1] + " " + Icon[6][1] + " " + Icon[5][1] + " " + Icon[4][1] + "" + Icon[3][1] + "\r\n\r\n"
        for (var i in Group) {
            if (Group[i][0] != GDP) continue;
            txt += "#k#L" + i + "#" + Group[i][1] + "\r\n";
        }
    } else {
        Tag()
        txt += "\r\n\r\n"
        txt += "#L99#村莊傳送 " + getIcon(0) + "#l #L98#練功地圖 " + getIcon(0) + "#l #L97#組隊任務 " + getIcon(0) + "#l\r\n\r\n"
        Tag()
    }

    return txt;
}

function Tag() {
    txt += "" + Icon[0][1] + ""
    for (var i = 0; i <= 22; i++) {
        txt += "" + Icon[1][1] + ""
    }
    txt += "" + Icon[2][1] + ""
    return txt;
}

function getIcon(x) {
    if (x == 0) {
        Button = "#fUI/Basic.img/BtClaim/disabled/0#"
    } else {

        Button = "#fUI/Basic.img/BtClaim/normal/0#";
    }
    return Button;
}
