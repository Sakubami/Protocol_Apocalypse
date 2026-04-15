package de.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity;

import de.sakubami.protocol_apocalypse.server.logic.world.entities.Entity;
import de.sakubami.protocol_apocalypse.server.saving.data.SerializedEntity;
import de.sakubami.protocol_apocalypse.shared.type.EntityType;
import de.sakubami.protocol_apocalypse.shared.utils.Vector2i;

import java.util.UUID;

public abstract class LivingEntity extends Entity {
    private String displayName = "NULL";

    public LivingEntity(EntityType type) {
        super(type);
    }

    public LivingEntity(EntityType type, UUID uuid) {
        super(type, uuid);
    }

    @Override
    public SerializedEntity toData() {
        SerializedEntity data = super.toData();
        data.displayName = displayName;
        return data;
    }

    public void setName(String displayName) { this.displayName = displayName; }
    public String getName() { return this.displayName; }
    public Vector2i getTilePos() {
        int x = (int) Math.floor(super.getPos().x());
        int y = (int) Math.floor(super.getPos().y());
        return new Vector2i(x / 32, y / 32);
    }
}
