
const PacketCreator = Java.type('org.gms.util.PacketCreator')
let status;
let chr;
const skillId = 5001001;
const itemId = 2431106
const skillLv_min = 22;
const skillLv_max = 24;
const successRate = 70;
const needLevel = 80;

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
            const skillLv = chr.getSkillLevel(skillId);
            let canBreak = false;
            if (chr.getLevel() >= needLevel && chr.getJob().getId() >= 510 && chr.getJob().getId() <= 512 && skillLv >= skillLv_min && skillLv < skillLv_max && im.hasItem(itemId, 1)) canBreak = true;

            if (canBreak) {
                chr.breakSkillLevel(skillId, skillLv + 1, successRate);
                im.gainItem(itemId, -1);
            } else chr.getMap().broadcastMessage(PacketCreator.skillBookResult(chr, 0, 0, false, false));

            im.dispose();
        }
    }
}