package xyz.sakubami.protocol_apocalypse.client.rendering.textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import xyz.sakubami.protocol_apocalypse.shared.types.EntityType;
import xyz.sakubami.protocol_apocalypse.shared.types.ItemType;
import xyz.sakubami.protocol_apocalypse.shared.types.ObjectType;
import xyz.sakubami.protocol_apocalypse.shared.types.TileType;

import java.util.EnumMap;

public class TextureManager {
    public static void init() { instance = new TextureManager(); }
    private static TextureManager instance;
    public static TextureManager get() { return instance; }

    private final TextureAtlas atlas;

    private final EnumMap<ItemType, TextureRegion> items = new EnumMap<>(ItemType.class);
    private final EnumMap<TileType, TextureRegion> tiles = new EnumMap<>(TileType.class);
    private final EnumMap<ObjectType, TextureRegion> objects = new EnumMap<>(ObjectType.class);
    private final EnumMap<EntityType, TextureRegion> entities = new EnumMap<>(EntityType.class);

    public TextureManager() {
        atlas = new TextureAtlas(Gdx.files.internal("game.atlas"));

        loadEnum(ItemType.values(), items);
        loadEnum(ObjectType.values(), objects);
        loadEnum(TileType.values(), tiles);
        loadEnum(EntityType.values(), entities);
    }

    private <E extends Enum<E>> void loadEnum(E[] values, EnumMap<E, TextureRegion> map) {
        for (E type : values) {
            String path = type.name().toLowerCase();
            TextureRegion region = atlas.findRegion(path);

            if (region == null) {
                Gdx.app.error("TextureManager", "Texture not found: " + path);
            }

            map.put(type, region);
        }
    }

    public TextureRegion getItemTexture(ItemType texture) { return this.items.get(texture); }
    public TextureRegion getTileTexture(TileType texture) { return this.tiles.get(texture); }
    public TextureRegion getObjectTexture(ObjectType texture) { return this.objects.get(texture); }
    public TextureRegion getEntityTexture(EntityType texture) { return this.entities.get(texture); }
}
