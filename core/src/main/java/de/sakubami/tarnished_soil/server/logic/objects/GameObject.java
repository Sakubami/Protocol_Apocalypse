package de.sakubami.tarnished_soil.server.logic.objects;

import de.sakubami.tarnished_soil.server.saving.data.Serializable;
import de.sakubami.tarnished_soil.server.saving.data.SerializedObject;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.ObjectState;
import de.sakubami.tarnished_soil.shared.type.ObjectType;
import de.sakubami.tarnished_soil.shared.utils.Vector2f;

import java.util.HashMap;
import java.util.function.Supplier;

public abstract class GameObject implements Serializable<SerializedObject> {
    private Vector2f pos;
    private final ObjectType type;
    private boolean removal = false;

    public GameObject(ObjectType type) {
        this.pos = new Vector2f(0,0);
        this.type = type;
    }

    @Override
    public SerializedObject toData() {
        SerializedObject data = new SerializedObject();
        data.pos = pos.toString();
        data.type = type;
        return data;
    }

    @Override
    public void readData(SerializedObject data) {
        this.pos = Vector2f.fromString(data.pos);
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

    public static GameObject createFromData(ObjectState data) {
        Supplier<? extends GameObject> supplier = registry.get(data.type);
        if (supplier == null) {
            throw new RuntimeException("Unknown GameObject type: " + data.type);
        }
        GameObject obj = supplier.get();
        obj.pos = data.pos;
        return obj;
    }

    public ObjectType getType() { return type; }
    public Vector2f getPos() { return pos; }
    public void setRemoval() { this.removal = true; }
    public boolean checkRemoval() { return this.removal; }
}
