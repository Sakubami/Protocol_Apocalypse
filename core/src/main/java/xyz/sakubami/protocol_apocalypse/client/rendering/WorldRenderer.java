package xyz.sakubami.protocol_apocalypse.client.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import xyz.sakubami.protocol_apocalypse.client.logic.ClientWorld;
import xyz.sakubami.protocol_apocalypse.client.rendering.textures.TextureManager;
import xyz.sakubami.protocol_apocalypse.shared.Configuration;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.ChunkState;
import xyz.sakubami.protocol_apocalypse.shared.types.EntityType;
import xyz.sakubami.protocol_apocalypse.shared.types.ObjectType;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.State;
import xyz.sakubami.protocol_apocalypse.shared.types.TileType;
import xyz.sakubami.protocol_apocalypse.shared.types.Type;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.util.*;

public record WorldRenderer() {

    public void render(SpriteBatch batch, ClientWorld world) {
        List<State> states = new ArrayList<>(world.getEntities().values());

        System.out.println("STATES SIZE AT START OF RENDERING -> " + states.size());

        for (Map.Entry<Vector2f, ChunkState> chunks : world.getChunks().entrySet()) {
            states.addAll(chunks.getValue().objects.values());

            int size = Configuration.getDefaultChunkSize();
            int tileSize = Configuration.getDefaultTileSize();

            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    TileType tile = chunks.getValue().getTiles()[x + y * size];

                    float worldX = (chunks.getKey().x() * size + x) * tileSize;
                    float worldY = (chunks.getKey().y() * size + y) * tileSize;

                    batch.draw(TextureManager.get().getTileTexture(tile), worldX, worldY);
                }
            }
        }

        System.out.println("SIZE 1 --------------> " + states.size());

        List<State> sorted = states.stream()
            .sorted(Comparator.comparing((State state) -> state.getPos().y()).reversed())
            .toList();

        System.out.println("SIZE 2 --------------> " + states.size());


        for (State state : sorted) {
            System.out.println(state.getPos());
            Vector2f pos = state.getPos();
            TextureRegion texture;
            Type type = state.getType();

            if (type instanceof ObjectType)
                texture = TextureManager.get().getObjectTexture((ObjectType) state.getType());
            else
                texture = TextureManager.get().getEntityTexture((EntityType) state.getType());
            batch.draw(texture, pos.x(), pos.y());
        }
    }
}
