package de.sakubami.tarnished_soil.shared.utils;

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

    @Override
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    @Override
    public Vector2f normalized() {
        float len = length();
        if (len == 0) return new Vector2f(0, 0); // avoid division by zero
        return new Vector2f(x / len, y / len);
    }

    @Override
    public Vector2f mul(float scalar) {
        return new Vector2f(x * scalar, y * scalar);
    }

    public Vector2i transform() {
        return new Vector2i((int) Math.floor(x), (int) Math.floor(y));
    }
}
