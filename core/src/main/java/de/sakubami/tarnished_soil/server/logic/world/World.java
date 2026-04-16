package de.sakubami.tarnished_soil.server.logic.world;

import de.sakubami.tarnished_soil.server.logic.chunks.ChunkManager;
import de.sakubami.tarnished_soil.server.logic.chunks.WorldGenerator;
import de.sakubami.tarnished_soil.server.logic.world.entities.livingentity.Player;
import de.sakubami.tarnished_soil.server.saving.data.Serializable;
import de.sakubami.tarnished_soil.server.saving.data.WorldData;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.GameStateBuilder;

import java.util.*;

public class World implements Serializable<WorldData> {
    private long seed;
    private UUID uuid;
    private String name;
    private final Map<UUID, Player> players = new HashMap<>();

    private final ChunkManager chunkManager;

    public World(String name) {
        this.name = name;
        this.seed = System.currentTimeMillis();
        this.uuid = UUID.randomUUID();

        chunkManager = new ChunkManager(new WorldGenerator(seed));
    }

    public GameStateBuilder tick(GameStateBuilder builder) {
        return chunkManager.handleBatches(this, players.values(), builder);
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
    public Map<UUID, Player> getPlayers() { return this.players; }
    public void addPlayer(Player player) { this.players.put(player.getUuid(), player); }
    public void removePlayer(UUID uuid) { this.players.remove(uuid); }
}
