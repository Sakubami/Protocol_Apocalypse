package xyz.sakubami.protocol_apocalypse.server.logic.world.entities;

import xyz.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity.Player;
import xyz.sakubami.protocol_apocalypse.shared.types.EntityType;

public class EntityRegistry {
    static {
        Entity.registerType(EntityType.PLAYER, Player::new);
        // Entity.registerType(EntityType.SHEEP, Sheep::new);
    }

    public static void init() {}
}
