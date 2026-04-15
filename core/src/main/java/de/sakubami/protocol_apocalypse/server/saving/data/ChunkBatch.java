package de.sakubami.protocol_apocalypse.server.saving.data;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChunkBatch implements Serialized<ChunkBatch> {
    public Map<String, SerializedChunk> chunks = new HashMap<>();
    public Map<UUID, SerializedEntity> entities = new HashMap<>();
    public String name;

    public ChunkBatch() {}

    @Override
    public ChunkBatch createObject() {
        return this;
    }

    @Override
    public String getPath() {
        return "batches/" + name;
    }
}
