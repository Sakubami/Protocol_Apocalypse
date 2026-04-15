package de.sakubami.protocol_apocalypse.shared.network.packets.handlers;

import de.sakubami.protocol_apocalypse.shared.network.Packet;

public interface PacketHandler {
    void handle(Packet packet);
}
