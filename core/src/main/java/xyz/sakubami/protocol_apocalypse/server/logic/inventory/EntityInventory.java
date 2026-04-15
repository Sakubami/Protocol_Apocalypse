package xyz.sakubami.protocol_apocalypse.server.logic.inventory;

import xyz.sakubami.protocol_apocalypse.server.logic.items.ItemStack;
import xyz.sakubami.protocol_apocalypse.shared.type.InventoryType;

public class EntityInventory extends Inventory{
    private final ItemStack[] hotbar = new ItemStack[4];

    public EntityInventory() {
        super(InventoryType.PLAYER, 54);
    }

    public void test() {
    }

    public ItemStack[] getHotbar() { return this.hotbar; }
    public ItemStack getHotbarItem(int pos) { return this.hotbar[pos]; }
    public void setHotbarItem(int pos, ItemStack stack) { this.hotbar[pos] = stack; }
}
