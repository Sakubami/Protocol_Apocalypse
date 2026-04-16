package de.sakubami.tarnished_soil.server.saving.data;

import de.sakubami.tarnished_soil.shared.type.EntityType;
import de.sakubami.tarnished_soil.server.logic.world.entities.Entity;

import java.util.UUID;

public class SerializedEntity implements Serialized<Entity>{
    public String pos;
    public EntityType type;
    public UUID uuid;
    public SerializedInventory inventory;

    public String displayName;

    public SerializedEntity() {}

    @Override
    public Entity createObject() {
        return Entity.createFromData(this);
    }

    @Override
    public String getPath() {
        return "";
    }
}
