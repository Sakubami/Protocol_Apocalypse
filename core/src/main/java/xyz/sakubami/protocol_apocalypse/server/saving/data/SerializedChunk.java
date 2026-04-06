package xyz.sakubami.protocol_apocalypse.server.saving.data;

import xyz.sakubami.protocol_apocalypse.shared.types.TileType;
import xyz.sakubami.protocol_apocalypse.server.logic.chunks.Chunk;
import xyz.sakubami.protocol_apocalypse.shared.Configuration;

import java.util.HashMap;
import java.util.Map;

public class SerializedChunk implements Serialized<Chunk> {
    public TileType[] tiles;
    public int size;
    public Map<String, SerializedObject> objects = new HashMap<>();

    public SerializedChunk() {}

    @Override
    public Chunk createObject() {
        Chunk chunk = new Chunk(Configuration.getDefaultChunkSize());
        chunk.readData(this);
        return chunk;
    }

    @Override
    public String getPath() {
        return "";
    }
}
