package de.sakubami.tarnished_soil.shared.type;

public enum InventoryType implements Type {
    ENTITY(0);

    private static final InventoryType[] BY_ID = new InventoryType[values().length];

    static {
        for (InventoryType type : values()) {
            BY_ID[type.id] = type;
        }
    }

    private final int id;

    InventoryType(int id) {
        this.id = id;
    }

    public int getId() { return this.id; }
    public static InventoryType getByID(int id) { return BY_ID[id]; }
}
