package de.sakubami.protocol_apocalypse.server.logic.items.regular;

import de.sakubami.protocol_apocalypse.server.logic.items.ItemStack;
import de.sakubami.protocol_apocalypse.server.logic.items.ItemType;

public class RegularItem extends ItemStack {
    public RegularItem(ItemType type, String id, int amount) {
        super(type, id, amount);
    }
}
