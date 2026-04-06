package xyz.sakubami.protocol_apocalypse.client.logic;

import xyz.sakubami.protocol_apocalypse.client.Client;
import xyz.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity.Player;
import xyz.sakubami.protocol_apocalypse.shared.Configuration;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver.C2SPlayerMovePacket;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

public record Prediction(Client client) {

    public void sendMovement(Vector2f velocity, float deltaTime) {
        Vector2f movement = velocity.mul(deltaTime);
        client.getCurrentWorldData().getPlayers().get(Configuration.getClientPlayerUUID()).pos = client.getCurrentWorldData().getPlayers().get(Configuration.getClientPlayerUUID()).getPos().add(movement);
        client.sendPacket(new C2SPlayerMovePacket(Configuration.getClientPlayerUUID(), movement));
    }
}
