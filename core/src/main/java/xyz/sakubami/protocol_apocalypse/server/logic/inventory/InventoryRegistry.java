package xyz.sakubami.protocol_apocalypse.server.logic.inventory;

import xyz.sakubami.protocol_apocalypse.shared.type.InventoryType;

public class InventoryRegistry {
    static {
        Inventory.registerType(InventoryType.PLAYER, EntityInventory::new);
    }
}
