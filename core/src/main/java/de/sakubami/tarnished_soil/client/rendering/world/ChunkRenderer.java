package de.sakubami.tarnished_soil.client.rendering.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.sakubami.tarnished_soil.client.logic.ClientWorld;
import de.sakubami.tarnished_soil.client.rendering.textures.TextureManager;
import de.sakubami.tarnished_soil.shared.Configuration;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.ChunkState;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.ObjectState;
import de.sakubami.tarnished_soil.client.rendering.textures.registry.TileTexture;
import de.sakubami.tarnished_soil.shared.utils.Vector2f;

@Deprecated
public record ChunkRenderer(SpriteBatch batch, ClientWorld clientWorld, int tileSize) {

    public void render() {
        for (Vector2f pos : clientWorld.getChunks().keySet()) {
            renderChunk(pos);
        }
    }

    private void renderChunk(Vector2f pos) {
        ChunkState chunk = clientWorld.getChunks().get(pos);
        int size = Configuration.getDefaultChunkSize();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                TileTexture tile = chunk.getTiles()[x + y * size];

                float worldX = (pos.x() * size + x) * tileSize;
                float worldY = (pos.y() * size + y) * tileSize;

                batch.draw(TextureManager.get().getTileTexture(tile), worldX, worldY);
                ObjectState object = chunk.getObjectAt(new Vector2f(x, y));
                if (object != null)
                    return;
                    // batch.draw(TextureManager.get().getObjectTexture(object.getType()), worldX, worldY);
            }
        }
    }

    public void dispose() {
        batch.dispose();
    }
}
