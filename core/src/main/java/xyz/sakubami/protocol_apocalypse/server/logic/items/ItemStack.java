package xyz.sakubami.protocol_apocalypse.server.logic.items;

import xyz.sakubami.protocol_apocalypse.server.saving.data.Serializable;
import xyz.sakubami.protocol_apocalypse.server.saving.data.SerializedItemStack;
import xyz.sakubami.protocol_apocalypse.shared.type.ItemType;

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
        data.texture = type;
        data.id = id;
        data.amount = amount;
        return data;
    }

    @Override
    public void readData(SerializedItemStack data) {}
}
