package xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate;

import xyz.sakubami.protocol_apocalypse.server.logic.chunks.Chunk;
import xyz.sakubami.protocol_apocalypse.server.logic.objects.GameObject;
import xyz.sakubami.protocol_apocalypse.server.saving.data.ChunkBatch;
import xyz.sakubami.protocol_apocalypse.server.saving.data.SerializedChunk;
import xyz.sakubami.protocol_apocalypse.shared.types.TileType;
import xyz.sakubami.protocol_apocalypse.shared.utils.Coordinates;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2i;

import javax.sound.midi.SysexMessage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChunkState {
    public boolean remove = false;
    public TileType[] tiles;
    public final Map<Vector2f, ObjectState> objects = new HashMap<>();

    public ChunkState(Chunk chunk) {
        this.tiles = chunk.getTiles();
        for (Map.Entry<Vector2f, GameObject> entry : chunk.getObjects().entrySet()) {
            this.objects.put(entry.getKey(), new ObjectState(entry.getValue()));
        }
    }

    public ChunkState(Chunk chunk, boolean remove) {
        this.tiles = chunk.getTiles();
        this.remove = remove;
        for (Map.Entry<Vector2f, GameObject> entry : chunk.getObjects().entrySet()) {
            this.objects.put(entry.getKey(), new ObjectState(entry.getValue(), remove));
        }
    }

    public ChunkState(boolean remove) {
        this.remove = remove;
        this.tiles = new TileType[0];
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeBoolean(remove);
        // Entities
        out.writeInt(tiles.length);
        for (TileType tile : tiles) {
            out.writeInt(tile.getId());
        }

        // Chunks
        if (objects.isEmpty()) {
            out.writeInt(-99);
            System.out.println("OBJECT ARE EMPTY");
            return;
        }

        out.writeInt(objects.size());
        System.out.println("OBJECTS SIZE: " + objects.size());
        for (Map.Entry<Vector2f, ObjectState> entry : objects.entrySet()) {
            System.out.println("DOING SOMETHING WITH OBJECTS OR SOMETHING");
            out.writeFloat(entry.getKey().x());
            out.writeFloat(entry.getKey().y());
            entry.getValue().write(out);
            System.out.println("WRITTEN OBJECT TO DATA" + entry.getValue().pos);
        }
    }

    public static ChunkState read(DataInputStream in) throws IOException {
        ChunkState state = new ChunkState(false);
        state.remove = in.readBoolean();
        // Entities
        int tileCount = in.readInt();
        state.tiles = new TileType[tileCount];
        for (int i = 0; i < tileCount; i++) {
            state.tiles[i] = TileType.getByID(in.readInt());
        }

        // Chunks
        int objectCount = in.readInt();
        if (objectCount == -99)
            return state;
        for (int i = 0; i < objectCount; i++) {
            float x = in.readFloat();
            float y = in.readFloat();
            ObjectState object = ObjectState.read(in);
            state.objects.put(new Vector2f(x, y), object);
            System.out.println("GOTTEN OBJECT FROM DATA" + object.pos);
        }

        return state;

    }

    public void addObject(ObjectState object) {
        System.out.println("POSITION BEFORE GETTING CHUNK: " + object.pos);
        Vector2f absolute = new Vector2f(object.pos.x() * 32, object.pos.y() * 32);
        Vector2f p = Coordinates.getChunkObjectPos(absolute);
        this.objects.put(p, object);
        System.out.println("PLACED OBJECT IN CHUNK AT: " + p);
    }
    public TileType[] getTiles() { return tiles; }
    public ObjectState getObjectAt(Vector2f pos) { return this.objects.get(pos); }
}
