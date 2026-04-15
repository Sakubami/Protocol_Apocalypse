package de.sakubami.protocol_apocalypse.shared.network.client.gamestate;

import de.sakubami.protocol_apocalypse.server.logic.chunks.Chunk;
import de.sakubami.protocol_apocalypse.server.logic.objects.GameObject;
import de.sakubami.protocol_apocalypse.client.rendering.textures.registry.TileTexture;
import de.sakubami.protocol_apocalypse.shared.utils.Coordinates;
import de.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChunkState {
    public boolean remove = false;
    public TileTexture[] tiles;
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
        this.tiles = new TileTexture[0];
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeBoolean(remove);
        // Entities
        out.writeInt(tiles.length);
        for (TileTexture tile : tiles) {
            out.writeUTF(tile.name());
        }

        // Chunks
        if (objects.isEmpty()) {
            out.writeInt(-99);
            return;
        }

        out.writeInt(objects.size());
        for (Map.Entry<Vector2f, ObjectState> entry : objects.entrySet()) {
            out.writeFloat(entry.getKey().x());
            out.writeFloat(entry.getKey().y());
            entry.getValue().write(out);
        }
    }

    public static ChunkState read(DataInputStream in) throws IOException {
        ChunkState state = new ChunkState(false);
        state.remove = in.readBoolean();
        // Entities
        int tileCount = in.readInt();
        state.tiles = new TileTexture[tileCount];
        for (int i = 0; i < tileCount; i++) {
            state.tiles[i] = TileTexture.valueOf(in.readUTF());
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
        }

        return state;
    }

    public void addObject(ObjectState object) {
        Vector2f absolute = new Vector2f(object.pos.x() * 32, object.pos.y() * 32);
        Vector2f p = Coordinates.getChunkObjectPos(absolute);
        this.objects.put(p, object);
    }
    public TileTexture[] getTiles() { return tiles; }
    public ObjectState getObjectAt(Vector2f pos) { return this.objects.get(pos); }
}
