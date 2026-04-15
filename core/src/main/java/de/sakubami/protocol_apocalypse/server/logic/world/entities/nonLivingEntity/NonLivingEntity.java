package de.sakubami.protocol_apocalypse.server.logic.world.entities.nonLivingEntity;

import de.sakubami.protocol_apocalypse.server.logic.world.entities.Entity;
import de.sakubami.protocol_apocalypse.server.saving.data.SerializedEntity;
import de.sakubami.protocol_apocalypse.shared.type.EntityType;

public abstract class NonLivingEntity extends Entity {
    private String displayName = "NULL";

    public NonLivingEntity(EntityType textureT) {
        super(textureT);
    }

    @Override
    public SerializedEntity toData() {
        SerializedEntity data = super.toData();
        data.displayName = displayName;
        return data;
    }

    public void setName(String displayName) { this.displayName = displayName; }
    public String getName() { return this.displayName; }
}
