package de.sakubami.tarnished_soil.shared.network.client.gamestate;

import de.sakubami.tarnished_soil.server.logic.world.entities.Entity;
import de.sakubami.tarnished_soil.shared.type.EntityType;
import de.sakubami.tarnished_soil.shared.type.Type;
import de.sakubami.tarnished_soil.shared.utils.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class EntityState implements RenderSortable<EntityState> {
    public UUID uuid;
    public boolean remove = false;
    public EntityType type;
    public Vector2f pos;
    public Direction direction = Direction.SOUTH;
    public InventoryState inventory;

    public EntityState(boolean remove) {
        this.remove = remove;
    }

    public EntityState(Entity data) {
        this.uuid = data.getUuid();
        this.pos = data.getPos();
        this.type = data.getType();
        this.inventory = new InventoryState(data.getInventory());
    }

    public EntityState(Entity data, boolean remove) {
        this.uuid = data.getUuid();
        this.pos = data.getPos();
        this.type = data.getType();
        this.inventory = new InventoryState(data.getInventory());
        this.remove = remove;
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeBoolean(remove);
        out.writeLong(uuid.getMostSignificantBits());
        out.writeLong(uuid.getLeastSignificantBits());
        out.writeFloat(pos.x());
        out.writeFloat(pos.y());
        out.writeInt(type.getId());
        inventory.write(out);
    }

    public static EntityState read(DataInputStream in) throws IOException {
        EntityState e = new EntityState(false);
        e.remove = in.readBoolean();
        e.uuid = new UUID(in.readLong(), in.readLong());
        e.pos = new Vector2f(in.readFloat(), in.readFloat());
        e.type = EntityType.getByID(in.readInt());
        e.inventory = InventoryState.read(in);
        return e;
    }

    public Vector2f getPos() { return pos; }
    @Override public Type getType() { return type; }

    @Override
    public EntityState copy(EntityState original) {
        EntityState e = new EntityState(original.remove);
        e.type = original.type;
        e.uuid = original.uuid;
        e.pos = original.pos;
        e.direction = original.direction;
        e.inventory = original.inventory;
        return e;
    }

    public void setDirection(Direction direction) { this.direction = direction; }
}
