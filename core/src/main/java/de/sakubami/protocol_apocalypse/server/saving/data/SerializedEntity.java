package de.sakubami.protocol_apocalypse.server.saving.data;

import de.sakubami.protocol_apocalypse.shared.type.EntityType;
import de.sakubami.protocol_apocalypse.server.logic.world.entities.Entity;

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
