package xyz.sakubami.protocol_apocalypse.client.logic;

import xyz.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity.Player;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.ChunkState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.GameState;
import xyz.sakubami.protocol_apocalypse.shared.types.EntityType;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2i;

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
            chunks.put(pos, entry.getValue());
        }
    }

    public Map<UUID, EntityState> getEntities() { return  entities; }
    public Map<Vector2f, ChunkState> getChunks() { return chunks; }
    public Map<UUID, EntityState> getPlayers() { return players; }
}
