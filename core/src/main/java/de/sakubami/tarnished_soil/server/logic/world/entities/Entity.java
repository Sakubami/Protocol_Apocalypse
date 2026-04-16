package de.sakubami.tarnished_soil.server.logic.world.entities;

import de.sakubami.tarnished_soil.server.logic.inventory.EntityInventory;
import de.sakubami.tarnished_soil.server.saving.data.Serializable;
import de.sakubami.tarnished_soil.server.saving.data.SerializedEntity;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.EntityState;
import de.sakubami.tarnished_soil.shared.type.EntityType;
import de.sakubami.tarnished_soil.shared.utils.Vector2f;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class Entity implements Serializable<SerializedEntity> {
    private Vector2f pos = new Vector2f(0, 0);
    private final EntityType type;
    private UUID uuid;
    private final EntityInventory inventory = new EntityInventory();

    public Entity(EntityType type) {
        this.type = type;
        this.uuid = UUID.randomUUID();
    }

    public Entity(EntityType type, UUID uuid) {
        this.type = type;
        this.uuid = uuid;
    }

    @Override
    public SerializedEntity toData() {
        SerializedEntity data = new SerializedEntity();
        data.pos = pos.toString();
        data.type = type;
        data.uuid = uuid;
        data.inventory = inventory.toData();
        return data;
    }

    @Override
    public void readData(SerializedEntity data) {
        this.pos = Vector2f.fromString(data.pos);
        this.uuid = data.uuid;
    }

    private static final HashMap<EntityType, Supplier<? extends Entity>> registry = new HashMap<>();

    public static void registerType(EntityType type, Supplier<? extends Entity> constructor) {
        registry.put(type, constructor);
    }

    public static Entity createFromData(SerializedEntity data) {
        Supplier<? extends Entity> supplier = registry.get(data.type);
        if (supplier == null) {
            throw new RuntimeException("Unknown GameObject type: " + data.type);
        }
        Entity obj = supplier.get();   // concrete instance
        obj.readData(data);                // populate fields
        return obj;
    }

    public UUID getUuid() { return this.uuid; }
    public Vector2f getPos() { return pos; }
    public void setPos(Vector2f pos) { this.pos = pos; }
    public EntityType getType() { return this.type; }
    public EntityInventory getInventory() { return this.inventory; }
}
