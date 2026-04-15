package de.sakubami.protocol_apocalypse.server.saving.data;

import de.sakubami.protocol_apocalypse.server.logic.items.ItemType;

public class SerializedItemStack {
    public ItemType type;
    public String id;
    public int amount;

    public SerializedItemStack() {}
}
