package de.sakubami.tarnished_soil.server.logic.items;

import de.sakubami.tarnished_soil.server.saving.data.Serializable;
import de.sakubami.tarnished_soil.server.saving.data.SerializedItemStack;
import de.sakubami.tarnished_soil.shared.type.ItemSubType;
import de.sakubami.tarnished_soil.shared.type.ItemType;

public class ItemStack implements Serializable<SerializedItemStack> {
    private ItemType type;
    private ItemSubType id;
    private int amount;

    public ItemStack(ItemType type, ItemSubType id, int amount) {
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

    @Override
    public void readData(SerializedItemStack data) {
        this.type = data.type;
        this.id = data.id;
        this.amount = data.amount;
    }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public ItemSubType getId() { return id; }
    public ItemType getType() { return type; }
}
