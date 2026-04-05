package xyz.sakubami.protocol_apocalypse.server.logic.chunks;

import xyz.sakubami.protocol_apocalypse.server.logic.objects.GameObject;
import xyz.sakubami.protocol_apocalypse.server.saving.data.Serializable;
import xyz.sakubami.protocol_apocalypse.server.saving.data.SerializedChunk;
import xyz.sakubami.protocol_apocalypse.shared.types.TileType;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2i;

import java.util.HashMap;
import java.util.Map;

public class Chunk implements Serializable<SerializedChunk> {
    private TileType[] tiles;
    private final int size;
    private final Map<Vector2f, GameObject> objects;

    public Chunk(int size) {
        this.size = size;
        this.objects = new HashMap<>();
    }

    @Override
    public SerializedChunk toData() {
        SerializedChunk data = new SerializedChunk();
        data.size = size;
        data.tiles = tiles;
        for(Vector2f loc : objects.keySet()) {
            data.objects.put(loc.toString(), objects.get(loc).toData());
        }
        return data;
    }

    @Override
    public void readData(SerializedChunk data) {
        this.tiles = data.tiles;
        if (data.objects == null)
            return;
        for(String key : data.objects.keySet()) {
            String[] split = key.split("%");
            objects.put(Vector2f.fromString(key), GameObject.createFromData(data.objects.get(key)));
        }
    }

    public boolean generateTiles(WorldGenerator generator, Vector2f pos) {
        TileType[] tiles1D = new TileType[size * size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                float worldX = pos.x() * size + x;
                float worldY = pos.y() * size + y;
                double value = generator.getNoise(worldX * 0.8, worldY * 0.8);
                TileType tile;
                if (value < 0) tile = TileType.WATER;
                else if (value < 0.2) tile = TileType.GRASS;
                else tile = TileType.SNOW;
                tiles1D[x + y * size] = tile;
            }
        }
        this.tiles = tiles1D;
        return tiles.length != 0;
    }

    public void addObject(Vector2f pos, GameObject object) {
        this.objects.put(pos, object);
    }
    public void removeObject(Vector2f pos) {
        this.objects.remove(pos);
    }
    public GameObject getObjectAt(Vector2f pos) {
        return this.objects.get(pos);
    }
    public Map<Vector2f, GameObject> getObjects() {
        return this.objects;
    }
    public TileType[] getTiles() { return this.tiles; }
    public int getSize() { return this.size; }
}
