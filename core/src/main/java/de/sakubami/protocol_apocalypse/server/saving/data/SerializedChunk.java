package de.sakubami.protocol_apocalypse.server.saving.data;

import de.sakubami.protocol_apocalypse.client.rendering.textures.registry.TileTexture;
import de.sakubami.protocol_apocalypse.server.logic.chunks.Chunk;
import de.sakubami.protocol_apocalypse.shared.Configuration;

import java.util.HashMap;
import java.util.Map;

public class SerializedChunk implements Serialized<Chunk> {
    public TileTexture[] tiles;
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
