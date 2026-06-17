package org.gms.net.server.channel.handlers;

import org.gms.client.Client;
import org.gms.net.AbstractPacketHandler;
import org.gms.net.packet.InPacket;

public final class UseLieDetectorHandler extends AbstractPacketHandler {
    @Override
    public final void handlePacket(InPacket p, Client c) {
        String name = p.readString();

        c.getPlayer().getMap().getAllPlayers().forEach(chr -> {
            if (chr.getName().equals(name)) {
                if (chr.isFullScreenAttacking()) c.getPlayer().dropMessage(1,"无法对正在进行挂机的玩家使用测谎仪");
                else chr.getAbstractPlayerInteraction().openNpc(0, "测谎仪");
            }
        });
    }
}