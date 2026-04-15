package de.sakubami.protocol_apocalypse.server.logic.items.consumable;

import de.sakubami.protocol_apocalypse.server.logic.items.ItemStack;
import de.sakubami.protocol_apocalypse.server.logic.items.ItemType;

public class Consumable extends ItemStack {
    public Consumable(String id, int amount) {
        super(ItemType.CONSUMABLE, id, amount);
    }
}
