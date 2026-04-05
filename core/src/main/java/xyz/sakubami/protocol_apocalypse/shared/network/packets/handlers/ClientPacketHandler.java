package xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers;

import xyz.sakubami.protocol_apocalypse.client.Client;
import xyz.sakubami.protocol_apocalypse.shared.network.Connection;
import xyz.sakubami.protocol_apocalypse.shared.network.Packet;

public record ClientPacketHandler(Client client) implements PacketHandler {

    @Override
    public void handle(Packet packet) {
        packet.execute(this);
    }
}
