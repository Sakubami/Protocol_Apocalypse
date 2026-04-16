package de.sakubami.tarnished_soil.client.rendering.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.sakubami.tarnished_soil.client.logic.ClientWorld;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.EntityState;
import de.sakubami.tarnished_soil.shared.utils.Vector2f;

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
