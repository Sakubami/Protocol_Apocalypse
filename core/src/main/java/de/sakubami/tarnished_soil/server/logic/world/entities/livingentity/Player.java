package de.sakubami.tarnished_soil.server.logic.world.entities.livingentity;

import de.sakubami.tarnished_soil.shared.type.EntityType;

import java.util.UUID;

public class Player extends LivingEntity {
    private final String name;

    public Player() {
        super(EntityType.PLAYER);
        this.name = "sakubami";
    }

    public Player(UUID uuid) {
        super(EntityType.PLAYER, uuid);
        this.name = "sakubami";
    }

    public String getName() { return name; }
}
