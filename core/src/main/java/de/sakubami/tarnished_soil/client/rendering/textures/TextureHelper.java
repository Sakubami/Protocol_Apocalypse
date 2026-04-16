package de.sakubami.tarnished_soil.client.rendering.textures;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.EntityState;
import de.sakubami.tarnished_soil.client.rendering.textures.registry.EntityTexture;
import de.sakubami.tarnished_soil.client.rendering.textures.registry.ItemTexture;
import de.sakubami.tarnished_soil.client.rendering.textures.registry.ObjectTexture;
import de.sakubami.tarnished_soil.shared.type.ItemType;
import de.sakubami.tarnished_soil.shared.type.ObjectType;

public class TextureHelper {

    public static TextureRegion getEntityTextureByDirection(EntityState state) {
        String name = state.type.name() + state.direction.ordinal();
        return TextureManager.get().getEntityTexture(EntityTexture.valueOf(name));
    }

    public static TextureRegion getDefaultObjectTexture(ObjectType type) {
        return TextureManager.get().getObjectTexture(ObjectTexture.valueOf(type.name() + 0));
    }

    public static TextureRegion getDefaultItemTexture(ItemType type) {
        return TextureManager.get().getItemTexture(ItemTexture.valueOf(type.name() + 0));
    }
}
