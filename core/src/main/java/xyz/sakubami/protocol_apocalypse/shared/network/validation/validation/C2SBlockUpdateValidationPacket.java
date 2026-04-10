package xyz.sakubami.protocol_apocalypse.shared.network.validation.validation;

import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.ObjectState;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver.C2SBlockUpdatePacket;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.PacketHandler;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.ServerPacketHandler;
import xyz.sakubami.protocol_apocalypse.shared.network.validation.ValidationPacket;
import xyz.sakubami.protocol_apocalypse.shared.utils.Coordinates;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.Vector;

public class C2SBlockUpdateValidationPacket implements ValidationPacket {

    private UUID uuid;
    private Vector2f pos;
    private ObjectState state;

    public C2SBlockUpdateValidationPacket(UUID uuid, Vector2f pos, ObjectState state) {
        this.uuid = uuid;
        this.pos = pos;
        this.state = state;
        this.state.pos = Coordinates.getChunkObjectPos(pos);
    }

    public C2SBlockUpdateValidationPacket() {}

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeUTF(uuid.toString());
        out.writeFloat(pos.x());
        out.writeFloat(pos.y());
        state.write(out);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        this.uuid = UUID.fromString(in.readUTF());
        this.pos = new Vector2f(in.readFloat(), in.readFloat());
        this.state = ObjectState.read(in);
    }

    @Override
    public void execute(PacketHandler handler) {
        if (!(handler instanceof ServerPacketHandler h))
            return;
        h.server().getValidation().validateBlockUpdate(this);
    }

    @Override
    public int getId() {
        return 7;
    }

    @Override
    public UUID getSignature() {
        return null;
    }

    public UUID getUuid() { return this.uuid; }
    public Vector2f getPos() { return this.pos; }
    public ObjectState getState() { return this.state; }
}
