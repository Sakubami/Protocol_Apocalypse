package de.sakubami.tarnished_soil.shared.network.packets.clienttoserver;

import de.sakubami.tarnished_soil.shared.network.Packet;
import de.sakubami.tarnished_soil.shared.network.packets.handlers.PacketHandler;
import de.sakubami.tarnished_soil.shared.utils.Vector2i;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class C2SBlockUpdatePacket implements Packet {
    public Vector2i pos;

    public C2SBlockUpdatePacket(Vector2i pos) {
        this.pos = pos;
    }

    public C2SBlockUpdatePacket() {}

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeUTF(pos.toString());
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        this.pos = Vector2i.fromString(in.readUTF());
    }

    @Override
    public void execute(PacketHandler nandler) {

    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public UUID getSignature() {
        return null;
    }
}
