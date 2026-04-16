package de.sakubami.tarnished_soil.server.saving.data;

import de.sakubami.tarnished_soil.shared.type.ItemSubType;
import de.sakubami.tarnished_soil.shared.type.ItemType;

public class SerializedItemStack {
    public ItemType type;
    public ItemSubType id;
    public int amount;

    public SerializedItemStack() {}
}
