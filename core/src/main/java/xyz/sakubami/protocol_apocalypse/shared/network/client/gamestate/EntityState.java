package xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate;

import xyz.sakubami.protocol_apocalypse.server.logic.world.entities.Entity;
import xyz.sakubami.protocol_apocalypse.server.saving.data.SerializedEntity;
import xyz.sakubami.protocol_apocalypse.shared.types.EntityType;
import xyz.sakubami.protocol_apocalypse.shared.types.Type;
import xyz.sakubami.protocol_apocalypse.shared.utils.PositionalVector;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2i;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class EntityState implements State {
    public UUID uuid;
    public boolean remove = false;
    public EntityType type;
    public Vector2f pos;

    public EntityState(boolean remove) {
        this.remove = remove;
    }

    public EntityState(Entity data) {
        this.uuid = data.getUuid();
        this.pos = data.getPos();
        this.type = data.getType();
    }

    public EntityState(Entity data, boolean remove) {
        this.uuid = data.getUuid();
        this.pos = data.getPos();
        this.type = data.getType();
        this.remove = remove;
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeBoolean(remove);
        out.writeLong(uuid.getMostSignificantBits());
        out.writeLong(uuid.getLeastSignificantBits());
        out.writeFloat(pos.x());
        out.writeFloat(pos.y());
        out.writeInt(type.getId());
    }

    public static EntityState read(DataInputStream in) throws IOException {
        EntityState e = new EntityState(false);
        e.remove = in.readBoolean();
        e.uuid = new UUID(in.readLong(), in.readLong());
        e.pos = new Vector2f(in.readFloat(), in.readFloat());
        e.type = EntityType.getByID(in.readInt());
        return e;
    }

    @Override public Vector2f getPos() { return pos; }
    @Override public Type getType() { return type; }
}
