package xyz.sakubami.protocol_apocalypse.client.logic;

import com.badlogic.gdx.Input;
import xyz.sakubami.protocol_apocalypse.client.Client;
import xyz.sakubami.protocol_apocalypse.server.logic.objects.normal.Tree;
import xyz.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity.Player;
import xyz.sakubami.protocol_apocalypse.shared.Configuration;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.ObjectState;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver.C2SPlayerMovePacket;
import xyz.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SBlockUpdateValidationPacket;
import xyz.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SMovementValidationPacket;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

public record Prediction(Client client) {

    public void sendMovement(Vector2f velocity, float deltaTime) {
        Vector2f movement = velocity.mul(deltaTime);
        Vector2f v = client.getCurrentWorldData().getPlayers().get(Configuration.getClientPlayerUUID()).getPos();
        client.getCurrentWorldData().getPlayers().get(Configuration.getClientPlayerUUID()).pos = v.add(movement);
        client.sendPacket(new C2SMovementValidationPacket(Configuration.getClientPlayerUUID(), v, movement));
    }

    public void sendInteract(Vector2f pos) {
        EntityState state = client.getCurrentWorldData().getPlayers().get(Configuration.getClientPlayerUUID());
        System.out.println("CLICKED TILE: " + pos.x() / 32 + " - " +  pos.y() / 32);
        client.getCurrentWorldData().placeBlock(pos, new ObjectState(new Tree()));
        client.sendPacket(new C2SBlockUpdateValidationPacket(Configuration.getClientPlayerUUID(), pos, new ObjectState(new Tree())));
    }
}
