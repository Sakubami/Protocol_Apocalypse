package de.sakubami.protocol_apocalypse.client.rendering.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.sakubami.protocol_apocalypse.client.logic.ClientWorld;
import de.sakubami.protocol_apocalypse.client.rendering.textures.TextureHelper;
import de.sakubami.protocol_apocalypse.client.rendering.textures.TextureManager;
import de.sakubami.protocol_apocalypse.shared.Configuration;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.ChunkState;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.ObjectState;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.RenderSortable;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.*;
import de.sakubami.protocol_apocalypse.client.rendering.textures.registry.TileTexture;
import de.sakubami.protocol_apocalypse.shared.type.ObjectType;
import de.sakubami.protocol_apocalypse.shared.type.Type;
import de.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.util.*;

public record WorldRenderer() {

    public void render(SpriteBatch batch, ClientWorld world) {
        List<RenderSortable<?>> states = new ArrayList<>(world.getEntities().values());

        states.addAll(world.getPlayers().values());

        for (Map.Entry<Vector2f, ChunkState> entry : world.getChunks().entrySet()) {
            List<ObjectState> objectStates = new ArrayList<>();
            for (ObjectState state : entry.getValue().objects.values()) {
                ObjectState s = state.copy(state);
                Vector2f p = s.pos;
                s.pos = new Vector2f(p.x() * 32, p.y() * 32);
                objectStates.add(s);
            }
            states.addAll(objectStates);

            int size = Configuration.getDefaultChunkSize();
            int tileSize = Configuration.getDefaultTileSize();

            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    TileTexture tile = entry.getValue().getTiles()[x + y * size];

                    float worldX = (entry.getKey().x() * size + x) * tileSize;
                    float worldY = (entry.getKey().y() * size + y) * tileSize;

                    batch.draw(TextureManager.get().getTileTexture(tile), worldX, worldY);
                }
            }
        }

        List<RenderSortable<?>> sorted = states.stream()
            .sorted(Comparator.comparing((RenderSortable<?> state) -> state.getPos().y()).reversed())
            .toList();

        for (RenderSortable<?> state : sorted) {
            Vector2f pos = state.getPos();
            TextureRegion texture;
            Type type = state.getType();

            if (type instanceof ObjectType) {
                texture = TextureHelper.getDefaultObjectTexture((ObjectType) type);
            }
            else {
                texture = TextureHelper.getEntityTextureByDirection((EntityState) state);
            }

            batch.draw(texture, Math.round(pos.x()), Math.round(pos.y()));
        }
    }
}
