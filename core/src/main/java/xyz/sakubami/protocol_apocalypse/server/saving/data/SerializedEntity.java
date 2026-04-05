package xyz.sakubami.protocol_apocalypse.server.saving.data;

import xyz.sakubami.protocol_apocalypse.shared.types.EntityType;
import xyz.sakubami.protocol_apocalypse.server.logic.world.entities.Entity;

import java.util.UUID;

public class SerializedEntity implements Serialized<Entity>{
    public String pos;
    public EntityType type;
    public UUID uuid;

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
