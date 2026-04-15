package de.sakubami.protocol_apocalypse.server.logic.items.ability;

import de.sakubami.protocol_apocalypse.server.logic.items.ItemStack;
import de.sakubami.protocol_apocalypse.server.logic.items.ItemType;

public class AbilityItem extends ItemStack {
    public AbilityItem(ItemType type, String id, int amount) {
        super(type, id, amount);
    }
}
