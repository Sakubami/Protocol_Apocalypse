package de.sakubami.protocol_apocalypse.shared.network.client.gamestate;

import de.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameState {
    public Map<UUID, EntityState> entities;
    public Map<Vector2f, ChunkState> chunks;

    public GameState(Map<UUID, EntityState> entities, Map<Vector2f, ChunkState> chunks) {
        this.entities = entities;
        this.chunks = chunks;
    }

    public void write(DataOutputStream out) throws IOException {
        // Entities
        out.writeInt(entities.size());
        for (EntityState entity : entities.values()) {
            entity.write(out);
        }

        // Chunks
        out.writeInt(chunks.size());
        for (Map.Entry<Vector2f, ChunkState> entry : chunks.entrySet()) {
            out.writeFloat(entry.getKey().x());
            out.writeFloat(entry.getKey().y());
            entry.getValue().write(out);
        }
    }

    public static GameState read(DataInputStream in) throws IOException {
        Map<UUID, EntityState> entities = new HashMap<>();
        Map<Vector2f, ChunkState> chunks = new HashMap<>();

        // Entities
        int entityCount = in.readInt();
        for (int i = 0; i < entityCount; i++) {
            EntityState entity = EntityState.read(in);
            entities.put(entity.uuid, entity);
        }

        // Chunks
        int chunkCount = in.readInt();
        for (int i = 0; i < chunkCount; i++) {
            Vector2f pos = new Vector2f(in.readFloat(), in.readFloat());
            chunks.put(pos, ChunkState.read(in));
        }

        return new GameState(entities, chunks);
    }
}
