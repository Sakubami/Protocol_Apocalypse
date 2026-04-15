package xyz.sakubami.protocol_apocalypse.server.logic.inventory;

import xyz.sakubami.protocol_apocalypse.server.logic.items.ItemStack;
import xyz.sakubami.protocol_apocalypse.server.saving.data.Serializable;
import xyz.sakubami.protocol_apocalypse.server.saving.data.SerializedInventory;
import xyz.sakubami.protocol_apocalypse.shared.type.InventoryType;

import java.util.HashMap;
import java.util.function.Supplier;

public abstract class Inventory implements Serializable<SerializedInventory> {
    private final InventoryType type;
    private final ItemStack[] items;

    public Inventory(InventoryType type, int size) {
        this.type = type;
        this.items = new ItemStack[size];
    }

    @Override
    public SerializedInventory toData() {
        SerializedInventory data = new SerializedInventory();
        data.type = this.type;
        return data;
    }

    @Override
    public void readData(SerializedInventory data) {}

    private static final HashMap<InventoryType, Supplier<? extends Inventory>> registry = new HashMap<>();

    public static void registerType(InventoryType type, Supplier<? extends Inventory> constructor) {
        registry.put(type, constructor);
    }

    public static Inventory createFromData(SerializedInventory data) {
        Supplier<? extends Inventory> supplier = registry.get(data.type);
        if (supplier == null) {
            throw new RuntimeException("Unknown Inventory type: " + data.type);
        }
        Inventory obj = supplier.get();   // concrete instance
        obj.readData(data);                // populate fields
        return obj;
    }
    public void addItem(int pos, ItemStack stack) { this.items[pos] = stack; }
    public ItemStack[] getItems() { return this.items; }
    public ItemStack getItemAt(int pos) { return this.items[pos]; }
    public int getInventorySize() { return items.length; }
}
