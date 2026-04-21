package de.sakubami.tarnished_soil.client.logic;

import de.sakubami.tarnished_soil.client.Client;
import de.sakubami.tarnished_soil.server.logic.items.ItemStack;
import de.sakubami.tarnished_soil.server.logic.objects.normal.Wall;
import de.sakubami.tarnished_soil.shared.Configuration;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.Direction;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.EntityState;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.ItemState;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.ObjectState;
import de.sakubami.tarnished_soil.shared.network.validation.validation.C2SBlockUpdateValidationPacket;
import de.sakubami.tarnished_soil.shared.network.validation.validation.C2SMovementValidationPacket;
import de.sakubami.tarnished_soil.shared.type.ItemSubType;
import de.sakubami.tarnished_soil.shared.type.ItemType;
import de.sakubami.tarnished_soil.shared.utils.Vector2f;

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
        float x = (float) Math.floor(Math.abs(state.pos.x() / 32 - pos.x() / 32));
        float y = (float) Math.floor(Math.abs(state.pos.y() / 32- pos.y() / 32));
        if (x > 5 || y > 5 || x + y > 6.66f) {
            System.out.println("REJECTED PLAYER INTERACTION AT CLIENT LEVEL");
            return;
        }
        client.getCurrentWorldData().getPlayers().get(Configuration.getClientPlayerUUID()).inventory.hotbar[0] = new ItemState(new ItemStack(ItemType.CONSUMABLE, ItemSubType.DEATH_BRINGER, 1));
        System.out.println(client.getCurrentWorldData().getPlayers().get(Configuration.getClientPlayerUUID()).inventory.hotbar[0].type);
        client.getCurrentWorldData().placeBlock(pos, new ObjectState(new Wall()));
        client.sendPacket(new C2SBlockUpdateValidationPacket(Configuration.getClientPlayerUUID(), pos, new ObjectState(new Wall())));
    }

    public void sendAttack(Vector2f pos) {

    }
}
