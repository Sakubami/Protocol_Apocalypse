package de.sakubami.tarnished_soil.shared.network.packets.clienttoserver;

import de.sakubami.tarnished_soil.server.logic.world.entities.livingentity.Player;
import de.sakubami.tarnished_soil.shared.network.Connection;
import de.sakubami.tarnished_soil.shared.network.Packet;
import de.sakubami.tarnished_soil.shared.network.packets.handlers.PacketHandler;
import de.sakubami.tarnished_soil.shared.network.packets.handlers.ServerPacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class C2SPlayerConnectPacket implements Packet {

    public UUID uuid;
    public Connection connection;

    public C2SPlayerConnectPacket(UUID uuid) {
        this.uuid = uuid;
    }

    public C2SPlayerConnectPacket() {}

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeUTF(uuid.toString());
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        this.uuid = UUID.fromString(in.readUTF());
    }

    @Override
    public void execute(PacketHandler handler) {
        if (!(handler instanceof ServerPacketHandler h))
            return;
        h.server().connectPlayer(connection, new Player(uuid));
        //TODO load player information from server OR client
    }

    public void addConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int getId() {
        return 4;
    }

    @Override
    public UUID getSignature() {
        return null;
    }
}
