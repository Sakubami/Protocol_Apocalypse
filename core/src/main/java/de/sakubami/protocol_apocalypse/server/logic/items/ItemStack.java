package de.sakubami.protocol_apocalypse.server.logic.items;

import de.sakubami.protocol_apocalypse.server.saving.data.Serializable;
import de.sakubami.protocol_apocalypse.server.saving.data.SerializedItemStack;

import java.util.HashMap;
import java.util.function.Supplier;

public class ItemStack implements Serializable<SerializedItemStack> {
    public final ItemType type;
    public final String id;
    public int amount;

    public ItemStack(ItemType type, String id, int amount) {
        this.type = type;
        this.id = id;
        this.amount = amount;
    }

    @Override
    public SerializedItemStack toData() {
        SerializedItemStack data = new SerializedItemStack();
        data.type = type;
        data.id = id;
        data.amount = amount;
        return data;
    }

    test

    @Override
    public void readData(SerializedItemStack data) {}
}
