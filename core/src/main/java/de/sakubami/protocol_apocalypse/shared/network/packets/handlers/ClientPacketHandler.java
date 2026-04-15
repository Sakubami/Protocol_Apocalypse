package de.sakubami.protocol_apocalypse.shared.network.packets.handlers;

import de.sakubami.protocol_apocalypse.client.Client;
import de.sakubami.protocol_apocalypse.shared.network.Packet;

public record ClientPacketHandler(Client client) implements PacketHandler {

    @Override
    public void handle(Packet packet) {
        packet.execute(this);
    }
}
