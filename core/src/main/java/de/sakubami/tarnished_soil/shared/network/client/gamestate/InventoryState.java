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
            if (data.getItemAt(i) == null)
                continue;
            items[i] = new ItemState(data.getItemAt(i));
        }

        if (data instanceof EntityInventory v) {
            this.hotbar = new ItemState[v.getHotbarSize()];
            this.equipment = new ItemState[v.getEquipmentSize()];

            for (int i = 0; i < hotbar.length; i++) {
                if (v.getHotbarItem(i) == null)
                    continue;
                this.hotbar[i] = new ItemState(v.getHotbarItem(i));
            }

            for (int i = 0; i < equipment.length; i++) {
                if (v.getEquipmentItem(i) == null)
                    continue;
                this.equipment[i] = new ItemState(v.getEquipmentItem(i));
            }
        }
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeBoolean(clear);
        out.writeInt(type.getId());

        out.writeInt(items.length);
        for (ItemState item : items) {
            if (item == null)
                continue;
            item.write(out);
        }

        out.writeInt(hotbar.length);
        for (ItemState itemState : hotbar) {
            if (itemState == null)
                continue;
            itemState.write(out);
        }

        out.writeInt(equipment.length);
        for (ItemState itemState : equipment) {
            if (itemState == null)
                continue;
            itemState.write(out);
        }
    }

    public static InventoryState read(DataInputStream in) throws IOException {
        InventoryState e = new InventoryState(false);
        e.clear = in.readBoolean();
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
}
