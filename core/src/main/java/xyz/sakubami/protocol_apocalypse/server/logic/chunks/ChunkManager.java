package xyz.sakubami.protocol_apocalypse.server.logic.chunks;

import com.google.gson.Gson;
import xyz.sakubami.protocol_apocalypse.server.logic.objects.normal.Tree;
import xyz.sakubami.protocol_apocalypse.server.saving.Saviour;
import xyz.sakubami.protocol_apocalypse.server.logic.world.entities.Entity;
import xyz.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity.Player;
import xyz.sakubami.protocol_apocalypse.server.saving.data.ChunkBatch;
import xyz.sakubami.protocol_apocalypse.server.saving.data.SerializedChunk;
import xyz.sakubami.protocol_apocalypse.server.saving.data.SerializedEntity;
import xyz.sakubami.protocol_apocalypse.shared.Configuration;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.ChunkState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.GameStateBuilder;
import xyz.sakubami.protocol_apocalypse.shared.utils.Coordinates;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;
import xyz.sakubami.protocol_apocalypse.server.logic.world.World;

import java.util.*;

/**
 * handles chunk loading, updating and unloading
 */
public class ChunkManager {
    private final Gson gson = new Gson();
    private final WorldGenerator generator;
    private final int CHUNK_SIZE = Configuration.getDefaultChunkSize();
    private final int BATCH_SIZE = Configuration.getDefaultBatchSize();

    private final Map<Vector2f, Chunk> chunks;
    private final Map<Vector2f, ChunkBatch> batches;
    private final Map<UUID, Entity> entities;

    private GameStateBuilder builder;

    public ChunkManager(WorldGenerator generator) {
        this.generator = generator;
        this.chunks = new HashMap<>();
        this.batches = new HashMap<>();
        this.entities = new HashMap<>();
    }

    public void shutdown() {
        for(Vector2f v : batches.keySet()) {
            unloadBatch(v);
            System.out.println("SHUTDOWN");
        }
    }

    /**
     * should load the chunkbatches around every player connected to the world
     */
    public GameStateBuilder handleBatches(World world, Collection<Player> players, GameStateBuilder builder) {
        this.builder = builder;

        Set<Vector2f> loadQueue = new HashSet<>();
        Set<Vector2f> unloadQueue = new HashSet<>(batches.keySet());

        Set<Vector2f> occupiedBatches = new HashSet<>();
        for (Player player : players) {
            occupiedBatches.add(Coordinates.getChunkBatch(player));
        }

        for(Vector2f currentBatch : occupiedBatches) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    Vector2f neighbor = new Vector2f(currentBatch.x() + dx, currentBatch.y() + dy);
                    loadQueue.add(neighbor);
                    unloadQueue.remove(neighbor);
                }
            }
        }

        for (Vector2f pos : loadQueue) {
            if (!batches.containsKey(pos)) {
                loadBatch(world, pos);
            }
        }

        for (Vector2f pos : unloadQueue) {
            unloadBatch(pos);
        }

        return builder;
    }

    /**
     * just loads chunkBatches with the given location ---> name
     * puts empty chunks inside the batch on generation until saving for performace reasons
     * @param loc
     */
    private void loadBatch(World world, Vector2f loc) {
        String path = world.getDirectory() + "/batches/" + loc.toString();

        ChunkBatch batch = Saviour.loadData(path, ChunkBatch.class);
        if (batch == null)
            batch = new ChunkBatch();

        if (batch.chunks.isEmpty()) {
            float chunkX = loc.x() * 3;
            float chunkY = loc.y() * 3;
            for(float i = chunkX; i <= chunkX + 2; i++) {
                for(float i2 = chunkY; i2 <= chunkY + 2; i2++) {
                    Vector2f v = new Vector2f(i, i2);
                    Chunk chunk = new Chunk(CHUNK_SIZE);
                    chunk.generateTiles(generator, v);
                    batch.chunks.put(v.toString(), chunk.toData());
                    this.chunks.put(v, chunk);

                    builder.addChunk(v, new ChunkState(chunk));
                }
            }
        } else {
            for (Map.Entry<String, SerializedChunk> entry : batch.chunks.entrySet()) {
                Vector2f chunkPos = Vector2f.fromString(entry.getKey());
                Chunk chunk = new Chunk(CHUNK_SIZE);
                chunk.readData(entry.getValue());
                this.chunks.put(chunkPos, chunk);

                builder.addChunk(chunkPos, new ChunkState(chunk));
            }
        }

        if (!batch.entities.isEmpty())
            for (SerializedEntity data : batch.entities.values()) {
                Entity entity = Entity.createFromData(data);
                entities.put(entity.getUuid(), entity);

                builder.updateEntity(new EntityState(entity));
            }

        this.batches.put(loc, batch);
    }

    /**
     * should handle the unloading and saving of the chunks correctly
     * im keeping the loadedchunks in a separate list for uhm reasons
     * @param loc position of the batch
     */
    private void unloadBatch(Vector2f loc) {
        ChunkBatch newBatch = new ChunkBatch();
        newBatch.chunks = new HashMap<>();
        newBatch.entities = new HashMap<>();
        newBatch.name = loc.toString();

        for (String v : batches.get(loc).chunks.keySet()) {
            Vector2f chunkPos = Vector2f.fromString(v);
            Chunk chunk = chunks.get(chunkPos);
            newBatch.chunks.put(chunkPos.toString(), chunk.toData());
            chunks.remove(chunkPos);

            builder.removeChunk(chunkPos);
        }

        for (Map.Entry<UUID, Entity> entry : entities.entrySet()) {
            Vector2f pos = Coordinates.getChunkBatch(entry.getValue());
            Vector2f distance = pos.subtract(loc);
            if  (distance.x() > 0 || distance.y() > 0)
                continue;
            newBatch.entities.put(entry.getKey(), entry.getValue().toData());
            entities.remove(entry.getKey());

            builder.removeEntity(entry.getKey());
        }

        Saviour.saveWorldData(newBatch);
        batches.remove(loc);
    }

    public Map<Vector2f, Chunk> getChunks() { return chunks; }
    public Map<UUID, Entity> getEntities() { return this.entities; }

}


// handle player position
// load chunks from saved file
// generate chunk if non-existent on loading stage
