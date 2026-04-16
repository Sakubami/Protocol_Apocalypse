package de.sakubami.tarnished_soil.server.saving.data;

import de.sakubami.tarnished_soil.client.rendering.textures.registry.TileTexture;
import de.sakubami.tarnished_soil.server.logic.chunks.Chunk;
import de.sakubami.tarnished_soil.shared.Configuration;

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
