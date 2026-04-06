package xyz.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver;

import xyz.sakubami.protocol_apocalypse.ProtocolApocalypse;
import xyz.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity.Player;
import xyz.sakubami.protocol_apocalypse.shared.Configuration;
import xyz.sakubami.protocol_apocalypse.shared.network.Connection;
import xyz.sakubami.protocol_apocalypse.shared.network.Packet;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.PacketHandler;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.ServerPacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
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
    }

    public void addConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int getId() {
        return 4;
    }
}
