package xyz.sakubami.protocol_apocalypse.shared.network.validation.validation;

import xyz.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity.Player;
import xyz.sakubami.protocol_apocalypse.shared.network.validation.ValidationPacket;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.PacketHandler;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.ServerPacketHandler;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class C2SMovementValidationPacket implements ValidationPacket {

    private UUID playerUUID;
    private Vector2f location;
    private Vector2f destination;

    public C2SMovementValidationPacket(Player player, Vector2f destination) {
        this.playerUUID = player.getUuid();
        this.location = player.getPos();
        this.destination = destination;
    }

    public C2SMovementValidationPacket() {}

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeUTF(playerUUID.toString());
        out.writeFloat(location.x());
        out.writeFloat(location.y());
        out.writeFloat(destination.x());
        out.writeFloat(destination.y());
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        this.playerUUID = UUID.fromString(in.readUTF());
        this.location = new Vector2f(in.readFloat(), in.readFloat());
        this.destination = new Vector2f(in.readFloat(), in.readFloat());
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

    public Vector2f getLocation() { return this.location; }
    public Vector2f getDestination() { return this.destination; }
    public UUID getPlayerUUID() { return this.playerUUID; }
}
