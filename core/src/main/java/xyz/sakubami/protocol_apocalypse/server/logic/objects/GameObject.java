package xyz.sakubami.protocol_apocalypse.server.logic.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import xyz.sakubami.protocol_apocalypse.server.saving.data.Serializable;
import xyz.sakubami.protocol_apocalypse.server.saving.data.SerializedObject;
import xyz.sakubami.protocol_apocalypse.client.rendering.textures.TextureManager;
import xyz.sakubami.protocol_apocalypse.shared.types.ObjectType;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.util.HashMap;
import java.util.function.Supplier;

public abstract class GameObject implements Serializable<SerializedObject> {
    private Vector2f tilePos;
    private final int width;
    private final int height;
    private final ObjectType type;

    public GameObject(ObjectType textureT) {
        this.tilePos = new Vector2f(0,0);
        this.type = textureT;
        TextureRegion texture = TextureManager.get().getObjectTexture(textureT);
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
    }

    @Override
    public SerializedObject toData() {
        SerializedObject data = new SerializedObject();
        data.pos = tilePos.toString();
        data.type = type;
        return data;
    }

    @Override
    public void readData(SerializedObject data) {
        this.tilePos = Vector2f.fromString(data.pos);
    }

    private static final HashMap<ObjectType, Supplier<? extends GameObject>> registry = new HashMap<>();

    public static void registerType(ObjectType type, Supplier<? extends GameObject> constructor) {
        registry.put(type, constructor);
    }

    public static GameObject createFromData(SerializedObject data) {
        Supplier<? extends GameObject> supplier = registry.get(data.type);
        if (supplier == null) {
            throw new RuntimeException("Unknown GameObject type: " + data.type);
        }
        GameObject obj = supplier.get();   // concrete instance
        obj.readData(data);                // populate fields
        return obj;
    }

    public ObjectType getType() { return type; }
    public Vector2f getTilePos() { return tilePos; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Rectangle getBoundingBox() { return new Rectangle(tilePos.x(), tilePos.y(), width, height); }
    public void update(float deltaT) {}
}
