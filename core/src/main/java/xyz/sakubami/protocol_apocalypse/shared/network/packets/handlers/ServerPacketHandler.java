package xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers;

import xyz.sakubami.protocol_apocalypse.server.Server;
import xyz.sakubami.protocol_apocalypse.server.logic.world.World;
import xyz.sakubami.protocol_apocalypse.shared.network.Connection;
import xyz.sakubami.protocol_apocalypse.shared.network.Packet;

public record ServerPacketHandler(Server server) implements PacketHandler {

    @Override
    public void handle(Packet packet) {
        packet.execute(this);
    }
}
