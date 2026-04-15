package xyz.sakubami.protocol_apocalypse.shared.type;

public enum ObjectType implements Type {
    CHEST(0),
    TREE(1),
    WALL(2);

    private static final ObjectType[] BY_ID = new ObjectType[values().length];

    static {
        for (ObjectType type : values()) {
            BY_ID[type.id] = type;
        }
    }

    private final int id;

    ObjectType(int id) {
        this.id = id;
    }

    public int getId() { return this.id; }
    public static ObjectType getByID(int id) {
        return BY_ID[id];
    }
}
