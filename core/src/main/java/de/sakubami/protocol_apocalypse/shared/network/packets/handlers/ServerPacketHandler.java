package de.sakubami.protocol_apocalypse.shared.network.packets.handlers;

import de.sakubami.protocol_apocalypse.server.Server;
import de.sakubami.protocol_apocalypse.shared.network.Packet;

public record ServerPacketHandler(Server server) implements PacketHandler {

    @Override
    public void handle(Packet packet) {
        packet.execute(this);
    }
}
