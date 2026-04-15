package de.sakubami.protocol_apocalypse.server.logic.objects.container;

import de.sakubami.protocol_apocalypse.server.logic.items.ItemStack;
import de.sakubami.protocol_apocalypse.server.logic.objects.GameObject;
import de.sakubami.protocol_apocalypse.server.saving.data.SerializedItemStack;
import de.sakubami.protocol_apocalypse.server.saving.data.SerializedObject;
import de.sakubami.protocol_apocalypse.shared.type.ObjectType;

import java.util.HashMap;

public abstract class ItemHolder extends GameObject {
    private final HashMap<Integer, ItemStack> items;

    public ItemHolder(ObjectType texture) {
        super(texture);
        this.items = new HashMap<>();
    }

    public HashMap<Integer, ItemStack> getItems() { return items; }

    @Override
    public SerializedObject toData() {
        SerializedObject data = super.toData();
        HashMap<Integer, SerializedItemStack> serializedItems = new HashMap<>();
        for (Integer key : this.items.keySet()) {
            serializedItems.put(key, this.items.get(key).toData());
        }
        data.items = serializedItems;
        return data;
    }

    @Override
    public void readData(SerializedObject data) {
        this.items.clear();

        for(Integer key : data.items.keySet()) {
            SerializedItemStack itemData = data.items.get(key);
            ItemStack stack = null; // new ItemStack(itemData.type, itemData.id, itemData.amount);
            stack.readData(data.items.get(key));
            this.items.put(key, stack);
        }
    }
}
