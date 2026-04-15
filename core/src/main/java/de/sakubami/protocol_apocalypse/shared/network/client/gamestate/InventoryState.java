package de.sakubami.protocol_apocalypse.shared.network.client.gamestate;

import de.sakubami.protocol_apocalypse.server.logic.inventory.Inventory;
import de.sakubami.protocol_apocalypse.shared.type.InventoryType;
import de.sakubami.protocol_apocalypse.shared.type.ObjectType;
import de.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class InventoryState implements State<InventoryState>{

    public boolean clear = false;
    public ItemState[] items;
    public ItemState[] hotbar;
    public ItemState[] equipment;
    public InventoryType type;

    public InventoryState(boolean clear) {
        this.clear = clear;
    }

    public InventoryState(Inventory data) {
        this.items = data.getItems();

        this.type = data.getType();
    }

    public InventoryState(Inventory data, boolean clear) {
        this.clear = clear;
        this.pos = data.getTilePos();
        this.type = data.getType();
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeBoolean(clear);
        out.writeFloat(pos.x());
        out.writeFloat(pos.y());
        out.writeInt(type.getId());
    }

    public static InventoryState read(DataInputStream in) throws IOException {
        InventoryState e = new InventoryState(false);
        e.remove = in.readBoolean();
        e.pos = new Vector2f(in.readFloat(), in.readFloat());
        e.type = ObjectType.getByID(in.readInt());
        return e;
    }

    @Override public InventoryType getType() { return type; }

    @Override
    public InventoryState copy(InventoryState original) {
        InventoryState state = new InventoryState();
        state.pos = original.pos;
        state.type = original.type;
        return state;
    }
}
