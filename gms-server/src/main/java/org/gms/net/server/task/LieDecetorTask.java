package org.gms.net.server.task;

import org.gms.client.Character;
import org.gms.config.GameConfig;
import org.gms.net.server.PlayerStorage;
import org.gms.net.server.world.World;

public class LieDecetorTask extends BaseTask implements Runnable{

    @Override
    public void run() {
        if (!GameConfig.getServerBoolean("use_auto_lie_decetor")) {
            return;
        }

        PlayerStorage ps = wserv.getPlayerStorage();
        for (Character chr : ps.getAllCharacters()) {
            if (chr != null) {
                if (chr.isFullScreenAttacking() || chr.getMap().isTown()) {
                    continue;
                }

                if ( (System.currentTimeMillis() - chr.getInMapTimeRecord()) >= (GameConfig.getServerShort("auto_lie_decetor_interval") * 60000L) ) {
                    if (chr.getCashShop() == null) chr.getAbstractPlayerInteraction().openNpc(0,"测谎仪");
                }
            }
        }
    }

    public LieDecetorTask(World world) { super(world); }
}
