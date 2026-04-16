package de.sakubami.tarnished_soil.shared.network;

import de.sakubami.tarnished_soil.shared.network.packets.handlers.PacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public interface Packet {
    void write(DataOutputStream out) throws IOException;
    void read(DataInputStream in) throws IOException;
    void execute(PacketHandler handler);
    int getId();
    UUID getSignature();
}
