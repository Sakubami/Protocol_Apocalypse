package xyz.sakubami.protocol_apocalypse.shared.network;

import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.PacketHandler;

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
