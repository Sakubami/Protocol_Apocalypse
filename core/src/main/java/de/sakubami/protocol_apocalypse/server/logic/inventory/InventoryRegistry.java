package de.sakubami.protocol_apocalypse.server.logic.inventory;

import de.sakubami.protocol_apocalypse.shared.type.InventoryType;

public class InventoryRegistry {
    static {
        Inventory.registerType(InventoryType.PLAYER, EntityInventory::new);
    }

    public static void init() {}
}
