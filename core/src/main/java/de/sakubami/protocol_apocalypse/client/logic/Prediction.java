package de.sakubami.protocol_apocalypse.client.logic;

import de.sakubami.protocol_apocalypse.client.Client;
import de.sakubami.protocol_apocalypse.server.logic.objects.normal.Wall;
import de.sakubami.protocol_apocalypse.shared.Configuration;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.Direction;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.ObjectState;
import de.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SBlockUpdateValidationPacket;
import de.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SMovementValidationPacket;
import de.sakubami.protocol_apocalypse.shared.utils.Vector2f;

public record Prediction(Client client) {

    public void sendMovement(Vector2f velocity, float deltaTime, Direction direction) {
        Vector2f movement = velocity.mul(deltaTime);
        Vector2f v = client.getCurrentWorldData().getPlayers().get(Configuration.getClientPlayerUUID()).getPos();
        client.getCurrentWorldData().getPlayers().get(Configuration.getClientPlayerUUID()).pos = v.add(movement);
        client.getCurrentWorldData().getPlayers().get(Configuration.getClientPlayerUUID()).setDirection(direction);
        client.sendPacket(new C2SMovementValidationPacket(Configuration.getClientPlayerUUID(), v, movement, direction));
    }

    public void sendInteract(Vector2f pos) {
        EntityState state = client.getCurrentWorldData().getPlayers().get(Configuration.getClientPlayerUUID());
        System.out.println("CLICKED: " + state.pos);
        System.out.println("CLICKED AT: " + pos);
        float x = (float) Math.floor(Math.abs(state.pos.x() / 32 - pos.x() / 32));
        float y = (float) Math.floor(Math.abs(state.pos.y() / 32- pos.y() / 32));
        System.out.println("X: " + x + " Y: " + y + "   and click pos " + pos);
        System.out.println("coordinates: " + state.pos.x() / 32 + "  -  " + state.pos.y() / 32);
        if (x > 5 || y > 5 || x + y > 6.66f) {
            System.out.println("REJECTED PLAYER INTERACTION AT CLIENT LEVEL");
            return;
        }
        client.getCurrentWorldData().placeBlock(pos, new ObjectState(new Wall()));
        client.sendPacket(new C2SBlockUpdateValidationPacket(Configuration.getClientPlayerUUID(), pos, new ObjectState(new Wall())));
    }

    public void sendAttack(Vector2f pos) {

    }
}
