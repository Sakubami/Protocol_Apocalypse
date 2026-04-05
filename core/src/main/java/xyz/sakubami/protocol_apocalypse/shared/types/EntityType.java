package xyz.sakubami.protocol_apocalypse.shared.types;

import java.util.Arrays;

public enum EntityType implements Type{
    PLAYER(0);
    // SHEEP(1);

    private static final EntityType[] BY_ID = new EntityType[values().length];

    static {
        for (EntityType type : values()) {
            BY_ID[type.id] = type;
        }
    }

    private final int id;

    EntityType(int id) {
        this.id = id;
    }

    public int getId() { return this.id; }
    public static EntityType getByID(int id) {
        return BY_ID[id];
    }
}
