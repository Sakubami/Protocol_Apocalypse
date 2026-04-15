package de.sakubami.protocol_apocalypse.server.logic.world.entities;

import de.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity.Player;
import de.sakubami.protocol_apocalypse.shared.type.EntityType;

public class EntityRegistry {
    static {
        Entity.registerType(EntityType.PLAYER, Player::new);
        // Entity.registerType(EntityType.SHEEP, Sheep::new);
    }

    public static void init() {}
}
