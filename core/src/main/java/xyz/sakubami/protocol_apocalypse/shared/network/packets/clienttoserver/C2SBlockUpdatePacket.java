package xyz.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver;

import xyz.sakubami.protocol_apocalypse.shared.network.Packet;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.PacketHandler;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2i;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
}
