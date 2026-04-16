package de.sakubami.tarnished_soil.server.logic.inventory;

import de.sakubami.tarnished_soil.server.logic.items.ItemStack;
import de.sakubami.tarnished_soil.server.saving.data.Serializable;
import de.sakubami.tarnished_soil.server.saving.data.SerializedInventory;
import de.sakubami.tarnished_soil.shared.type.InventoryType;

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
        //TODO do this later lol
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
    public InventoryType getType() { return this.type; }
}
