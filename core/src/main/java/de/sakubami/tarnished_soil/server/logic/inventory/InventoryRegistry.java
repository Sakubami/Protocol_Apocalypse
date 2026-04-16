package de.sakubami.tarnished_soil.server.logic.inventory;

import de.sakubami.tarnished_soil.shared.type.InventoryType;

public class InventoryRegistry {
    static {
        Inventory.registerType(InventoryType.ENTITY, EntityInventory::new);
    }

    public static void init() {}
}
