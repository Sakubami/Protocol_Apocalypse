package xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate;

import xyz.sakubami.protocol_apocalypse.server.logic.objects.GameObject;
import xyz.sakubami.protocol_apocalypse.shared.type.ObjectType;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ObjectState implements RenderSortable<ObjectState> {
    public boolean remove = false;
    public Vector2f pos;
    public ObjectType type;

    public ObjectState(boolean remove) {
        this.remove = remove;
        this.pos = new Vector2f(0, 0);
        this.type = ObjectType.CHEST;
    }

    public ObjectState(GameObject data) {
        this.pos = data.getTilePos();
        this.type = data.getType();
    }

    public ObjectState(GameObject data, boolean remove) {
        this.remove = remove;
        this.pos = data.getTilePos();
        this.type = data.getType();
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeBoolean(remove);
        out.writeFloat(pos.x());
        out.writeFloat(pos.y());
        out.writeInt(type.getId());
    }

    public static ObjectState read(DataInputStream in) throws IOException {
        ObjectState e = new ObjectState(false);
        e.remove = in.readBoolean();
        e.pos = new Vector2f(in.readFloat(), in.readFloat());
        e.type = ObjectType.getByID(in.readInt());
        return e;
    }

    @Override public Vector2f getPos() { return pos; }
    @Override public ObjectType getType() { return type; }

    @Override
    public ObjectState copy(ObjectState original) {
        ObjectState state = new ObjectState(original.remove);
        state.pos = original.pos;
        state.type = original.type;
        return state;
    }
}
