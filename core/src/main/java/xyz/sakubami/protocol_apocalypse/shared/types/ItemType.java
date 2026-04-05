package xyz.sakubami.protocol_apocalypse.shared.types;

public enum ItemType implements Type{
    SWORD(0);

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
