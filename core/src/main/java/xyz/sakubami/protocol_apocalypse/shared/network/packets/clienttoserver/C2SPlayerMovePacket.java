package xyz.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver;

import xyz.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity.Player;
import xyz.sakubami.protocol_apocalypse.shared.network.Packet;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.PacketHandler;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.ServerPacketHandler;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class C2SPlayerMovePacket implements Packet {
    public float x;
    public float y;
    public float vx;
    public float vy;
    public UUID uuid;

    public C2SPlayerMovePacket(UUID uuid, Vector2f pos) {
        this.uuid = uuid;
        this.x = pos.x();
        this.y = pos.y();
    }

    public C2SPlayerMovePacket() {}

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeUTF(uuid.toString());
        out.writeFloat(x);
        out.writeFloat(y);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        this.uuid = UUID.fromString(in.readUTF());
        this.x = in.readFloat();
        this.y = in.readFloat();
    }

    @Override
    public void execute(PacketHandler handler) {
        if (!(handler instanceof ServerPacketHandler h))
            return;
        Player player = h.server().getOnlinePlayer(uuid);
        player.setPos(player.getPos().add(new Vector2f(x, y)));
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public UUID getSignature() {
        return null;
    }
}
