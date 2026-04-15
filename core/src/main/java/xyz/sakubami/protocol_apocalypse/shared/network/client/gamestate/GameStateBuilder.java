package xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate;

import xyz.sakubami.protocol_apocalypse.shared.network.packets.servertoclient.S2CGameStatePacket;
import xyz.sakubami.protocol_apocalypse.shared.type.EntityType;
import xyz.sakubami.protocol_apocalypse.shared.utils.Coordinates;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameStateBuilder {
    private final Map<Vector2f, ChunkState> chunks = new HashMap<>();
    private final Map<UUID, EntityState> entities = new HashMap<>();
    private final Map<UUID, EntityState> players = new HashMap<>();

    public void updateObject(ObjectState object) {
        Vector2f pos = Coordinates.getChunkPos(object.pos);
        ChunkState state = chunks.computeIfAbsent(pos, k -> new ChunkState(false));
        object.pos = Coordinates.getTilePos(object.pos);
        chunks.get(pos).addObject(object);
    }

    public void addChunk(Vector2f pos, ChunkState state) {
        this.chunks.put(pos, state);
    }

    public void updateEntity(EntityState entity) {
        entities.put(entity.uuid, entity);

        if (entity.type.equals(EntityType.PLAYER)) {
            System.out.println("updated player pos at: " + entity.pos);
            players.put(entity.uuid, entity);
        }

    }

    public void removeChunk(Vector2f pos) {
        chunks.put(pos, new ChunkState(true));
    }

    public void removeEntity(UUID uuid) {
        EntityState state = new EntityState(true);
        state.uuid = uuid;
        entities.put(uuid, state);
    }

    public GameState buildState() {
        return new GameState(entities, chunks);
    }

    public S2CGameStatePacket compile() {
        return new S2CGameStatePacket(new GameState(entities, chunks));
    }

    public Map<UUID, EntityState> getPlayers() { return this.players; }
}
