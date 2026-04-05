package xyz.sakubami.protocol_apocalypse.shared.utils;

public record Vector2f(float x, float y) implements PositionalVector<Vector2f>{

    public static Vector2f fromString(String v) {
        String[] split = v.split("%");
        return new Vector2f(Float.parseFloat(split[0]), Float.parseFloat(split[1]));
    }

    @Override
    public String toString() {
        return x + "%" + y;
    }

    @Override
    public Vector2f subtract(Vector2f subtractor) {
        float x1 = x - subtractor.x();
        float y1 = y - subtractor.y();
        return new Vector2f(x1, y1);
    }

    @Override
    public Vector2f add(Vector2f adder) {
        float x1 = x + adder.x();
        float y1 = y + adder.y();
        return new Vector2f(x1, y1);
    }

    public Vector2i transform() {
        return new Vector2i((int) Math.floor(x), (int) Math.floor(y));
    }
}
