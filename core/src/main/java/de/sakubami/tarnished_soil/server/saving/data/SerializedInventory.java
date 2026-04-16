package de.sakubami.tarnished_soil.server.saving.data;

import de.sakubami.tarnished_soil.server.logic.inventory.Inventory;
import de.sakubami.tarnished_soil.shared.type.InventoryType;

public class SerializedInventory implements Serialized<Inventory> {
    public InventoryType type;
    public SerializedItemStack[] items = new SerializedItemStack[0];
    public SerializedItemStack[] hotbar = new SerializedItemStack[0];
    public SerializedItemStack[] equipment = new SerializedItemStack[0];

    public SerializedInventory() {}

    @Override
    public Inventory createObject() {
        return Inventory.createFromData(this);
    }

    @Override
    public String getPath() {
        return "";
    }
}
