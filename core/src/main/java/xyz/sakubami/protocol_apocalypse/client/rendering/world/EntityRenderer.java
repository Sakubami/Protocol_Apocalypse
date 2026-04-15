package xyz.sakubami.protocol_apocalypse.client.rendering.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import xyz.sakubami.protocol_apocalypse.client.logic.ClientWorld;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.util.*;

@Deprecated
public record EntityRenderer(SpriteBatch batch, ClientWorld world) {

    public void render() {
        renderEntities();
    }

    private void renderEntities() {
        Map<UUID, EntityState> v = new HashMap<>(world.getEntities());

        List<EntityState> sorted = v.values().stream()
            .sorted(Comparator.comparing((EntityState e) -> e.pos.y()).reversed())
            .toList();

        for (EntityState entity : sorted) {
            Vector2f pos = entity.pos;
            // batch.draw(TextureManager.get().getEntityTexture(entity.type), pos.x(), pos.y());
        }
    }

    public void dispose() {
        batch.dispose();
    }
}
