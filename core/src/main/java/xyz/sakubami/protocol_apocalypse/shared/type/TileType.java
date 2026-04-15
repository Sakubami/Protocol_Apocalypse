package xyz.sakubami.protocol_apocalypse.shared.type;

public enum TileType implements Type {
    GRASS(0),
    SNOW(1),
    WATER(2);

    private static final TileType[] BY_ID = new TileType[values().length];

    static {
        for (TileType type : values()) {
            BY_ID[type.id] = type;
        }
    }

    private final int id;

    TileType(int id) {
        this.id = id;
    }

    public int getId() { return this.id; }
    public static TileType getByID(int id) {
        return BY_ID[id];
    }
}
