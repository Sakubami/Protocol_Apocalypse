package de.sakubami.tarnished_soil.server.logic.inventory;

import de.sakubami.tarnished_soil.server.logic.items.ItemStack;
import de.sakubami.tarnished_soil.shared.type.InventoryType;

public class EntityInventory extends Inventory{
    private final ItemStack[] hotbar = new ItemStack[4];
    private final ItemStack[] equipment = new ItemStack[10];

    public EntityInventory() {
        super(InventoryType.ENTITY, 54);
    }

    public ItemStack[] getHotbar() { return this.hotbar; }
    public int getHotbarSize() { return this.hotbar.length; }
    public ItemStack getHotbarItem(int pos) { return this.hotbar[pos]; }
    public void setHotbarItem(int pos, ItemStack stack) { this.hotbar[pos] = stack; }
    public ItemStack[] getEquipment() { return this.equipment; }
    public int getEquipmentSize() { return this.equipment.length; }
    public ItemStack getEquipmentItem(int pos) { return this.equipment[pos]; }
    public void setEquipmentItem(int pos, ItemStack stack) { this.equipment[pos] = stack; }
}
