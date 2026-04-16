package de.sakubami.tarnished_soil.shared.network.packets.handlers;

import de.sakubami.tarnished_soil.server.Server;
import de.sakubami.tarnished_soil.shared.network.Packet;

public record ServerPacketHandler(Server server) implements PacketHandler {

    @Override
    public void handle(Packet packet) {
        packet.execute(this);
    }
}
