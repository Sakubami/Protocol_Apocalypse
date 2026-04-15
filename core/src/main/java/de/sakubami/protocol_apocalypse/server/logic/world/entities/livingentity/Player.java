package de.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity;

import de.sakubami.protocol_apocalypse.shared.type.EntityType;

import java.util.UUID;

public class Player extends LivingEntity{
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
