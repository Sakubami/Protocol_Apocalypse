package de.sakubami.protocol_apocalypse.server.logic.items;

import de.sakubami.protocol_apocalypse.server.saving.data.Serializable;
import de.sakubami.protocol_apocalypse.server.saving.data.SerializedItemStack;

import java.util.HashMap;
import java.util.function.Supplier;

public abstract class ItemStack implements Serializable<SerializedItemStack> {
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

    @Override
    public void readData(SerializedItemStack data) {}

    private static final HashMap<ItemType, Supplier<? extends ItemStack>> registry = new HashMap<>();

    public static void registerType(ItemType type, Supplier<? extends ItemStack> constructor) {
        registry.put(type, constructor);
    }

    public static ItemStack createFromData(SerializedItemStack data) {
        Supplier<? extends ItemStack> supplier = registry.get(data.type);
        if (supplier == null) {
            throw new RuntimeException("Unknown ItemStack type: " + data.type);
        }
        ItemStack obj = supplier.get();   // concrete instance
        obj.readData(data);                // populate fields
        return obj;
    }
}
