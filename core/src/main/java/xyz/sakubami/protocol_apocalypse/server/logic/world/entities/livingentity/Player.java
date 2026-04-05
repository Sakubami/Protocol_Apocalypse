package xyz.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity;

import xyz.sakubami.protocol_apocalypse.shared.types.EntityType;

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
