package de.sakubami.tarnished_soil.shared.network.client.gamestate;

import de.sakubami.tarnished_soil.server.logic.items.ItemStack;
import de.sakubami.tarnished_soil.shared.type.ItemSubType;
import de.sakubami.tarnished_soil.shared.type.ItemType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ItemState implements State<ItemState>{

    public boolean remove = false;
    public ItemType type;
    public ItemSubType id;
    public int amount;

    public ItemState(boolean remove) {
        this.remove = remove;
        this.type = ItemType.PLACEHOLDER;
        this.id = ItemSubType.PLACEHOLDER;
    }

    public ItemState(ItemStack data) {
        this.type = data.getType();
    }

    public ItemState(ItemStack data, boolean remove) {
        this.remove = remove;
        this.type = data.getType();
        this.id = data.getId();
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeBoolean(remove);
        out.writeInt(type.getId());
        out.writeInt(id.getId());
    }

    public static ItemState read(DataInputStream in) throws IOException {
        ItemState e = new ItemState(in.readBoolean());
        e.type = ItemType.getByID(in.readInt());
        e.id = ItemSubType.getByID(in.readInt());
        return e;
    }

    @Override public ItemType getType() { return type; }

    @Override
    public ItemState copy(ItemState original) {
        ItemState state = new ItemState(original.remove);
        state.type = original.type;
        state.id = original.id;
        state.amount = original.amount;
        return state;
    }
}
