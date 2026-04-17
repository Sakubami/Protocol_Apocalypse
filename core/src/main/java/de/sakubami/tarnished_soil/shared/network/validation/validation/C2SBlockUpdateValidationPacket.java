package de.sakubami.tarnished_soil.shared.network.validation.validation;

import de.sakubami.tarnished_soil.shared.network.client.gamestate.ObjectState;
import de.sakubami.tarnished_soil.shared.network.packets.handlers.PacketHandler;
import de.sakubami.tarnished_soil.shared.network.packets.handlers.ServerPacketHandler;
import de.sakubami.tarnished_soil.shared.network.validation.ValidationPacket;
import de.sakubami.tarnished_soil.shared.utils.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class C2SBlockUpdateValidationPacket implements ValidationPacket {

    private UUID uuid;
    private Vector2f pos;
    private ObjectState state;

    public C2SBlockUpdateValidationPacket(UUID uuid, Vector2f pos, ObjectState state) {
        this.uuid = uuid;
        this.pos = pos;
        this.state = state;
        this.state.pos = pos;
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
        return 0;
    }

    @Override
    public UUID getSignature() {
        return null;
    }

    public UUID getUuid() { return this.uuid; }
    public Vector2f getPos() { return this.pos; }
    public ObjectState getState() { return this.state; }
}
