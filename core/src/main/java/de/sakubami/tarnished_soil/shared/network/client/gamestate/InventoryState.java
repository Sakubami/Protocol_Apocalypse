package de.sakubami.tarnished_soil.shared.network.client.gamestate;

import de.sakubami.tarnished_soil.server.logic.inventory.EntityInventory;
import de.sakubami.tarnished_soil.server.logic.inventory.Inventory;
import de.sakubami.tarnished_soil.shared.type.InventoryType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class InventoryState implements State<InventoryState>{

    public boolean clear = false;
    public ItemState[] items = new ItemState[0];
    public ItemState[] hotbar = new ItemState[0];
    public ItemState[] equipment = new ItemState[0];
    public InventoryType type;

    public InventoryState(boolean clear) {
        this.clear = clear;
    }

    public InventoryState(Inventory data) {
        this.type = data.getType();
        this.items = new ItemState[data.getInventorySize()];

        for (int i = 0; i < items.length; i++) {
            ItemState itemState = new ItemState(false);
            if (data.getItemAt(i) != null)
                itemState = new ItemState(data.getItemAt(i));
            items[i] = itemState;
        }

        if (data instanceof EntityInventory v) {
            this.hotbar = new ItemState[v.getHotbarSize()];
            this.equipment = new ItemState[v.getEquipmentSize()];

            for (int i = 0; i < hotbar.length; i++) {
                ItemState itemState = new ItemState(false);
                if (v.getHotbarItem(i) != null)
                    itemState = new ItemState(v.getHotbarItem(i));
                this.hotbar[i] = itemState;
            }

            for (int i = 0; i < equipment.length; i++) {
                ItemState itemState = new ItemState(false);
                if (v.getEquipmentItem(i) != null)
                    itemState = new ItemState(v.getEquipmentItem(i));
                this.equipment[i] = itemState;
            }
        }
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeBoolean(clear);
        out.writeInt(type.getId());

        out.writeInt(items.length);
        for (ItemState itemState : items) {
            if (itemState == null)
                itemState = new ItemState(false);
            itemState.write(out);
        }

        out.writeInt(hotbar.length);
        for (ItemState itemState : hotbar) {
            if (itemState == null)
                itemState = new ItemState(false);
            itemState.write(out);
        }

        out.writeInt(equipment.length);
        for (ItemState itemState : equipment) {
            if (itemState == null)
                itemState = new ItemState(false);
            itemState.write(out);
        }
    }

    public static InventoryState read(DataInputStream in) throws IOException {
        InventoryState e = new InventoryState(in.readBoolean());
        e.type = InventoryType.getByID(in.readInt());

        int itemSize = in.readInt();
        if (itemSize != 0) {
            e.items = new ItemState[itemSize];
            for (int i = 0; i < itemSize; i++) {
                e.items[i] = ItemState.read(in);
            }
        }

        int hotbarSize = in.readInt();
        if (hotbarSize != 0) {
            e.hotbar = new ItemState[hotbarSize];
            for (int i = 0; i < hotbarSize; i++) {
                e.hotbar[i] = ItemState.read(in);
            }
        }

        int equipmentSize = in.readInt();
        if (equipmentSize != 0) {
            e.equipment = new ItemState[equipmentSize];
            for (int i = 0; i < equipmentSize; i++) {
                e.equipment[i] = ItemState.read(in);
            }
        }

        return e;
    }

    @Override public InventoryType getType() { return type; }

    @Override
    public InventoryState copy(InventoryState original) {
        InventoryState state = new InventoryState(false);
        state.type = original.type;
        return state;
    }

    public void clear(boolean clear) { this.clear = clear; }
    public int getHotbarSize() { return this.hotbar.length; }
}
