package de.sakubami.tarnished_soil.shared.type;

public enum ItemSubType {
    DEATH_BRINGER(0),
    PLACEHOLDER(1);

    private static final ItemSubType[] BY_ID = new ItemSubType[values().length];

    static {
        for (ItemSubType type : values()) {
            BY_ID[type.id] = type;
        }
    }

    private final int id;

    ItemSubType(int id) {
        this.id = id;
    }

    public int getId() { return this.id; }
    public static ItemSubType getByID(int id) {
        return BY_ID[id];
    }
}
