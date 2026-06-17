/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
		       Matthias Butz <matze@odinms.de>
		       Jan Christian Meyer <vimes@odinms.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.gms.net.server.channel.handlers;

import lombok.extern.slf4j.Slf4j;
import org.gms.client.Character;
import org.gms.client.Client;
import org.gms.client.autoban.AutobanFactory;
import org.gms.config.GameConfig;
import org.gms.net.packet.InPacket;
import org.gms.util.PacketCreator;
import org.gms.exception.EmptyMovementException;

import java.awt.geom.Point2D;

@Slf4j
public final class MovePlayerHandler extends AbstractMovementPacketHandler {
    @Override
    public final void handlePacket(InPacket p, Client c) {
        p.skip(9);
        try {   // thanks Sa for noticing empty movement sequences crashing players\
            Character player = c.getPlayer();
            Point2D oldPos = player.getPosition();

            int movementDataStart = p.getPosition();
            // 鼠标飞天：新增isCheck检测
            boolean isCheck = updatePosition(p, player, 0);
            long movementDataLength = p.getPosition() - movementDataStart; //how many bytes were read by updatePosition
            p.seek(movementDataStart);

            player.getMap().movePlayer(player, player.getPosition());
            if (player.isHidden()) {
                player.getMap().broadcastGMMessage(player, PacketCreator.movePlayer(player.getId(), p, movementDataLength), false);
            } else {
                player.getMap().broadcastMessage(player, PacketCreator.movePlayer(player.getId(), p, movementDataLength), false);
            }

            if (isCheck && GameConfig.getServerBoolean("open_mouse_fly_check")) {
                double xDifference = Math.abs(oldPos.getX() - player.getPosition().getX());
                double yDifference = Math.abs(oldPos.getY() - player.getPosition().getY());

                if (player.getPosition().distanceSq(oldPos) > GameConfig.getServerInt("mouse_fly_check_total") && (xDifference > GameConfig.getServerInt("mouse_fly_check_x") || yDifference > GameConfig.getServerInt("mouse_fly_check_y"))) {
                    AutobanFactory.PORTAL_DISTANCE.addPoint(player.getAutoBanManager(), "鼠标飞天");
                    c.sendPacket(PacketCreator.enableActions());
                }
            }
        } catch (EmptyMovementException e) {

        }
    }
}
