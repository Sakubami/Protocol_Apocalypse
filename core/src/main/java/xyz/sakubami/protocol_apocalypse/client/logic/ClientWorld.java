package xyz.sakubami.protocol_apocalypse.client.logic;

import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.ChunkState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.GameState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.ObjectState;
import xyz.sakubami.protocol_apocalypse.shared.type.EntityType;
import xyz.sakubami.protocol_apocalypse.shared.utils.Coordinates;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientWorld {
    private final Map<UUID, EntityState> entities = new HashMap<>();
    private final Map<Vector2f, ChunkState> chunks = new HashMap<>();
    private final Map<UUID, EntityState> players = new HashMap<>();

    public void applyState(GameState state) {
        for (Map.Entry<UUID, EntityState> entry : state.entities.entrySet()) {
            if (entry.getValue().getType().equals(EntityType.PLAYER)) {
                players.put(entry.getKey(), entry.getValue());
                continue;
            }
            if (entry.getValue().remove) {
                entities.remove(entry.getKey());
                players.remove(entry.getKey());
                continue;
            }
            entities.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Vector2f, ChunkState> entry : state.chunks.entrySet()) {
            Vector2f pos = entry.getKey();
            if (entry.getValue().remove) {
                chunks.remove(pos);
                continue;
            }
            if (!chunks.containsKey(entry.getKey()))
                chunks.put(pos, entry.getValue());
            else
                chunks.get(pos).objects.putAll(entry.getValue().objects);
        }
    }

    public void placeBlock(Vector2f pos, ObjectState state) {
        state.pos = Coordinates.getTilePos(pos);
        this.chunks.get(Coordinates.getChunkPos(pos)).addObject(state);
    }

    public Map<UUID, EntityState> getEntities() { return  entities; }
    public Map<Vector2f, ChunkState> getChunks() { return chunks; }
    public Map<UUID, EntityState> getPlayers() { return players; }
}
