package de.sakubami.tarnished_soil.server.logic.world;

import de.sakubami.tarnished_soil.server.saving.Saviour;
import de.sakubami.tarnished_soil.server.saving.data.WorldData;
import de.sakubami.tarnished_soil.shared.utils.DirectoryHelper;

import java.util.List;

public class WorldManager {
    private static WorldManager instance;
    public static void init() { instance = new WorldManager(); }
    public static WorldManager get() { return instance; }

    private final List<String> savedWorlds;

    public WorldManager() {
        this.savedWorlds = DirectoryHelper.listFolders("worlds");
        for (String string : savedWorlds) {
            System.out.println(string);
        }
    }

    public World selectWorld(String name) {
        if (savedWorlds.contains(name)){
            System.out.println("FOUND WORLD -> " + name);
            System.out.println(loadWorld(name).getName());
            return loadWorld(name);
        }
        else {
            System.out.println("CREATE WORLD");
            return createNewWorld(name);
        }
    }

    public World loadWorld(String name) {
        // Player player = (Player) Saviour.loadObject("worlds/" + name + "/players/" + Configuration.getClientPlayerUUID().toString(), SerializedEntity.class);
        return Saviour.loadObject("worlds/" + name + "/" + name, WorldData.class);
    }

    public void saveWorld(World world) {
        Saviour.saveData(world.getDirectory() + "/" + world.getName(), world.toData());
    }

    public World createNewWorld(String name) {
        DirectoryHelper.createDirectory("worlds/" + name + "/batches");
        return new World(name);
    }
}
