package de.sakubami.protocol_apocalypse.server.logic.world;

import de.sakubami.protocol_apocalypse.server.logic.chunks.ChunkManager;
import de.sakubami.protocol_apocalypse.server.logic.chunks.WorldGenerator;
import de.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity.Player;
import de.sakubami.protocol_apocalypse.server.saving.data.Serializable;
import de.sakubami.protocol_apocalypse.server.saving.data.WorldData;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.GameStateBuilder;

import java.util.*;

public class World implements Serializable<WorldData> {
    private long seed;
    private UUID uuid;
    private String name;

    private final ChunkManager chunkManager;

    public World(String name) {
        this.name = name;
        this.seed = System.currentTimeMillis();
        this.uuid = UUID.randomUUID();

        chunkManager = new ChunkManager(new WorldGenerator(seed));
    }

    public GameStateBuilder tick(Collection<Player> players, GameStateBuilder builder) {
        return chunkManager.handleBatches(this, players, builder);
    }

    @Override
    public WorldData toData() {
        WorldData data = new WorldData();
        data.seed = seed;
        data.uuid = uuid;
        data.name = name;
        return data;
    }

    @Override
    public void readData(WorldData data) {
        this.seed = data.seed;
        this.uuid = data.uuid;
        this.name = data.name;
    }

    public void shutdown() {
        chunkManager.shutdown();
    }

    public UUID getUuid() { return this.uuid; }
    public String getName() { return this.name; }
    public String getDirectory() { return "worlds/" + name; }
    public void setName(String name) { this.name = name; }
    public ChunkManager getChunkManager() { return chunkManager; }
}
