package de.sakubami.protocol_apocalypse.client.rendering.textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.sakubami.protocol_apocalypse.client.rendering.textures.registry.*;
import xyz.sakubami.protocol_apocalypse.client.rendering.textures.registry.*;

import java.util.EnumMap;

public class TextureManager {
    public static void init() { instance = new TextureManager(); }
    private static TextureManager instance;
    public static TextureManager get() { return instance; }

    private final TextureAtlas atlas;

    private final EnumMap<ItemTexture, TextureRegion> items = new EnumMap<>(ItemTexture.class);
    private final EnumMap<TileTexture, TextureRegion> tiles = new EnumMap<>(TileTexture.class);
    private final EnumMap<ObjectTexture, TextureRegion> objects = new EnumMap<>(ObjectTexture.class);
    private final EnumMap<EntityTexture, TextureRegion> entities = new EnumMap<>(EntityTexture.class);
    private final EnumMap<UITexture, TextureRegion> uiElements = new EnumMap<>(UITexture.class);

    public TextureManager() {
        atlas = new TextureAtlas(Gdx.files.internal("game.atlas"));

        loadEnum(ItemTexture.values(), items);
        loadEnum(ObjectTexture.values(), objects);
        loadEnum(TileTexture.values(), tiles);
        loadEnum(EntityTexture.values(), entities);
        loadEnum(UITexture.values(), uiElements);
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

    public TextureRegion getItemTexture(ItemTexture texture) { return this.items.get(texture); }
    public TextureRegion getTileTexture(TileTexture texture) { return this.tiles.get(texture); }
    public TextureRegion getObjectTexture(ObjectTexture texture) { return this.objects.get(texture); }
    public TextureRegion getEntityTexture(EntityTexture texture) { return this.entities.get(texture); }
    public TextureRegion getUITexture(UITexture texture) { return this.uiElements.get(texture); }
}
