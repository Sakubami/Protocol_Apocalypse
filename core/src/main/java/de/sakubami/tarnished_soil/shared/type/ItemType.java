package de.sakubami.tarnished_soil.shared.type;

public enum ItemType implements Type {
    REGULAR(0),
    ABILITY_ITEM(1),
    CONSUMABLE(2),
    PLACEHOLDER(3);

    private static final ItemType[] BY_ID = new ItemType[values().length];

    static {
        for (ItemType type : values()) {
            BY_ID[type.id] = type;
        }
    }

    private final int id;

    ItemType(int id) {
        this.id = id;
    }

    public int getId() { return this.id; }
    public static ItemType getByID(int id) {
        return BY_ID[id];
    }
}
