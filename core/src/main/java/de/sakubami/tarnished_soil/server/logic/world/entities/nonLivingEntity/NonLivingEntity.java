package de.sakubami.tarnished_soil.server.logic.world.entities.nonLivingEntity;

import de.sakubami.tarnished_soil.server.logic.world.entities.Entity;
import de.sakubami.tarnished_soil.server.saving.data.SerializedEntity;
import de.sakubami.tarnished_soil.shared.type.EntityType;

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
