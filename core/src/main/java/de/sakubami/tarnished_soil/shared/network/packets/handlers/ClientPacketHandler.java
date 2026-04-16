package de.sakubami.tarnished_soil.shared.network.packets.handlers;

import de.sakubami.tarnished_soil.client.Client;
import de.sakubami.tarnished_soil.shared.network.Packet;

public record ClientPacketHandler(Client client) implements PacketHandler {

    @Override
    public void handle(Packet packet) {
        packet.execute(this);
    }
}
