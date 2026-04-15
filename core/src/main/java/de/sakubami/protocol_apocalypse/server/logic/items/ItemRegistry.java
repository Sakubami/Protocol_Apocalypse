package de.sakubami.protocol_apocalypse.server.logic.items;

import de.sakubami.protocol_apocalypse.server.logic.items.regular.RegularItem;

public class ItemRegistry {
    static {
        ItemStack.registerType(ItemType.REGULAR, RegularItem::new);

    }

    public static void init() {}
}
