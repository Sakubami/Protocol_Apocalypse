package de.sakubami.protocol_apocalypse.shared.network.packets.servertoclient;

import de.sakubami.protocol_apocalypse.shared.network.Packet;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.GameState;
import de.sakubami.protocol_apocalypse.shared.network.packets.handlers.ClientPacketHandler;
import de.sakubami.protocol_apocalypse.shared.network.packets.handlers.PacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class S2CGameStatePacket implements Packet {
    public GameState state;

    public S2CGameStatePacket() {}

    public S2CGameStatePacket(GameState state) {
        this.state = state;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        state.write(out);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        this.state = GameState.read(in);
    }

    @Override
    public void execute(PacketHandler handler) {
        if (!(handler instanceof ClientPacketHandler h))
            return;
        h.client().applyState(state);
    }

    @Override
    public int getId() {
        return 3;
    }

    @Override
    public UUID getSignature() {
        return null;
    }
}
