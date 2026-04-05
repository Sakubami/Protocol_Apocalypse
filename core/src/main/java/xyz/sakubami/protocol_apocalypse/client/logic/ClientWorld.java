package xyz.sakubami.protocol_apocalypse.client.logic;

import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.ChunkState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.GameState;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2i;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientWorld {
    private final Map<UUID, EntityState> entities = new HashMap<>();
    private final Map<Vector2f, ChunkState> chunks = new HashMap<>();

    public void applyState(GameState state) {
        for (Map.Entry<UUID, EntityState> entry : state.entities.entrySet()) {
            if (entry.getValue().remove) {
                entities.remove(entry.getKey());
                System.out.println("REMOVED ENTITY AT ---> " + entry.getKey());
                continue;
            }
            entities.put(entry.getKey(), entry.getValue());
            System.out.println("ADDED ENTITY AT ---> " + entry.getKey());
        }

        for (Map.Entry<Vector2f, ChunkState> entry : state.chunks.entrySet()) {
            Vector2f pos = entry.getKey();
            if (entry.getValue().remove) {
                chunks.remove(pos);
                System.out.println("REMOVED CHUNK AT ---> " + pos);
                continue;
            }
            chunks.put(pos, entry.getValue());
            System.out.println("ADDED CHUNK AT ---> " + pos);
        }
    }

    public Map<UUID, EntityState> getEntities() { return  entities; }
    public Map<Vector2f, ChunkState> getChunks() { return chunks; }
}
