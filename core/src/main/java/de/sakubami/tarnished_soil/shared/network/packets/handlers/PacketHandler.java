package de.sakubami.tarnished_soil.shared.network.packets.handlers;

import de.sakubami.tarnished_soil.shared.network.Packet;

public interface PacketHandler {
    void handle(Packet packet);
}
