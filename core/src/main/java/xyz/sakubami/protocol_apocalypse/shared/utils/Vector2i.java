package xyz.sakubami.protocol_apocalypse.shared.utils;

/**
 *
 * Descibes 2D coordinates *shrug*
 * @param x coordinate
 * @param y coordinate
 */
public record Vector2i(int x, int y) implements PositionalVector<Vector2i> {

    public static Vector2i fromString(String v) {
        String[] split = v.split("%");
        return new Vector2i(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    @Override
    public String toString() {
        return x + "%" + y;
    }

    @Override
    public float length() {
        return 0;
    }

    @Override
    public Vector2i normalized() {
        return null;
    }

    @Override
    public Vector2i mul(float scalar) {
        return null;
    }

    public Vector2i subtract(Vector2i subtractor) {
        int x1 = x - subtractor.x();
        int y1 = y - subtractor.y();
        return new Vector2i(x1, y1);
    }

    public Vector2i add(Vector2i adder) {
        int x1 = x + adder.x();
        int y1 = y + adder.y();
        return new Vector2i(x1, y1);
    }

    public Vector2f transform() {
        return new Vector2f(x, y);
    }
}

