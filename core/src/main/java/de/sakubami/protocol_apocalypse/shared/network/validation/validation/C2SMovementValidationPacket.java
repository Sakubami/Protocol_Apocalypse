package de.sakubami.protocol_apocalypse.shared.network.validation.validation;

import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.Direction;
import de.sakubami.protocol_apocalypse.shared.network.validation.ValidationPacket;
import de.sakubami.protocol_apocalypse.shared.network.packets.handlers.PacketHandler;
import de.sakubami.protocol_apocalypse.shared.network.packets.handlers.ServerPacketHandler;
import de.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class C2SMovementValidationPacket implements ValidationPacket {

    private UUID playerUUID;
    private Vector2f location;
    private Vector2f movement;
    private Direction direction;

    public C2SMovementValidationPacket(UUID uuid, Vector2f pos, Vector2f movement, Direction direction) {
        this.playerUUID = uuid;
        this.location = pos;
        this.movement = movement;
        this.direction = direction;
    }

    public C2SMovementValidationPacket() {}

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeUTF(playerUUID.toString());
        out.writeFloat(location.x());
        out.writeFloat(location.y());
        out.writeFloat(movement.x());
        out.writeFloat(movement.y());
        out.writeInt(direction.ordinal());
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        this.playerUUID = UUID.fromString(in.readUTF());
        this.location = new Vector2f(in.readFloat(), in.readFloat());
        this.movement = new Vector2f(in.readFloat(), in.readFloat());
        Direction[] directions = Direction.values();
        this.direction = directions[in.readInt()];
    }

    @Override
    public void execute(PacketHandler handler) {
        if (!(handler instanceof ServerPacketHandler h))
            return;
        h.server().getValidation().validateMovement(this);
    }

    @Override
    public int getId() {
        return 5;
    }

    @Override
    public UUID getSignature() {
        return null;
    }

    public Vector2f getLocation() { return this.location; }
    public Vector2f getMovement() { return this.movement; }
    public UUID getPlayerUUID() { return this.playerUUID; }
    public Direction getDirection() { return this.direction; }
}
