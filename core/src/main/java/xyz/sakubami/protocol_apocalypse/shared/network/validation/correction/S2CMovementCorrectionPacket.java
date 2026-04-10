package xyz.sakubami.protocol_apocalypse.shared.network.validation.correction;

import xyz.sakubami.protocol_apocalypse.client.Client;
import xyz.sakubami.protocol_apocalypse.shared.network.Packet;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.ClientPacketHandler;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.PacketHandler;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.ServerPacketHandler;
import xyz.sakubami.protocol_apocalypse.shared.network.validation.CorrectionPacket;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.Vector;

public class S2CMovementCorrectionPacket implements CorrectionPacket {

    private UUID playerUUID;
    private Vector2f correction;

    public S2CMovementCorrectionPacket(UUID uuid, Vector2f correction) {
        this.playerUUID = uuid;
        this.correction = correction;
    }

    public S2CMovementCorrectionPacket() {}

    @Override
    public void write(DataOutputStream out) throws IOException {

    }

    @Override
    public void read(DataInputStream in) throws IOException {

    }

    @Override
    public void execute(PacketHandler handler) {
        if (!(handler instanceof ClientPacketHandler h))
            return;
    }

    @Override
    public int getId() {
        return 6;
    }

    @Override
    public UUID getSignature() {
        return null;
    }


}
