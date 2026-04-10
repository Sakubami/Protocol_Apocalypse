package xyz.sakubami.protocol_apocalypse.client.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import xyz.sakubami.protocol_apocalypse.client.logic.ClientWorld;
import xyz.sakubami.protocol_apocalypse.client.rendering.textures.TextureManager;
import xyz.sakubami.protocol_apocalypse.shared.Configuration;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.ChunkState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.ObjectState;
import xyz.sakubami.protocol_apocalypse.shared.types.EntityType;
import xyz.sakubami.protocol_apocalypse.shared.types.ObjectType;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.State;
import xyz.sakubami.protocol_apocalypse.shared.types.TileType;
import xyz.sakubami.protocol_apocalypse.shared.types.Type;
import xyz.sakubami.protocol_apocalypse.shared.utils.Coordinates;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.util.*;

import static com.badlogic.gdx.utils.JsonValue.ValueType.object;

public record WorldRenderer() {

    public void render(SpriteBatch batch, ClientWorld world) {
        List<State<?>> states = new ArrayList<>(world.getEntities().values());

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
                    TileType tile = entry.getValue().getTiles()[x + y * size];

                    float worldX = (entry.getKey().x() * size + x) * tileSize;
                    float worldY = (entry.getKey().y() * size + y) * tileSize;

                    batch.draw(TextureManager.get().getTileTexture(tile), worldX, worldY);
                }
            }
        }

        List<State<?>> sorted = states.stream()
            .sorted(Comparator.comparing((State<?> state) -> state.getPos().y()).reversed())
            .toList();

        for (State<?> state : sorted) {
            Vector2f pos = state.getPos();
            TextureRegion texture;
            Type type = state.getType();

            if (type instanceof ObjectType) {
                texture = TextureManager.get().getObjectTexture((ObjectType) type);
            }
            else {
                texture = TextureManager.get().getEntityTexture((EntityType) type);
            }

            batch.draw(texture, pos.x(), pos.y());
        }
    }
}
