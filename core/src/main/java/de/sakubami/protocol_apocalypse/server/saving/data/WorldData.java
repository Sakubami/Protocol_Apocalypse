package de.sakubami.protocol_apocalypse.server.saving.data;

import de.sakubami.protocol_apocalypse.server.logic.world.World;

import java.util.UUID;

public class WorldData implements Serialized<World> {
    public UUID uuid;
    public long seed;
    public String name;

    public WorldData() {}

    public WorldData(UUID uuid, long seed, String name) {}

    @Override
    public World createObject() {
        World world = new World(name);
        world.readData(this);
        return world;
    }

    @Override
    public String getPath() {
        return "worlds/" + name;
    }
}
