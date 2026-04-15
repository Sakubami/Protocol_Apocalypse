package de.sakubami.protocol_apocalypse.client.rendering.textures;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import de.sakubami.protocol_apocalypse.client.rendering.textures.registry.EntityTexture;
import de.sakubami.protocol_apocalypse.client.rendering.textures.registry.ItemTexture;
import de.sakubami.protocol_apocalypse.client.rendering.textures.registry.ObjectTexture;
import de.sakubami.protocol_apocalypse.server.logic.items.ItemType;
import de.sakubami.protocol_apocalypse.shared.type.ObjectType;

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
