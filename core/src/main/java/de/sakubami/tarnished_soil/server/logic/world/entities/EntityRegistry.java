package de.sakubami.tarnished_soil.server.logic.world.entities;

import de.sakubami.tarnished_soil.server.logic.world.entities.livingentity.Player;
import de.sakubami.tarnished_soil.shared.type.EntityType;

public class EntityRegistry {
    static {
        Entity.registerType(EntityType.PLAYER, Player::new);
        // Entity.registerType(EntityType.SHEEP, Sheep::new);
    }

    public static void init() {}
}
