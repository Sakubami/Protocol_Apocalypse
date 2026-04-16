package de.sakubami.tarnished_soil.shared.utils;

import de.sakubami.tarnished_soil.server.logic.world.entities.Entity;
import de.sakubami.tarnished_soil.shared.Configuration;

public class Coordinates {
    private static final int TILE_SIZE = 32;
    private static final int CHUNK_SIZE = 16;
    private static final int BATCH_SIZE = 3;

    public static Vector2f getChunkBatch(Entity entity) {
        int batchX = (int) Math.floor(((entity.getPos().x() / TILE_SIZE) / CHUNK_SIZE) / BATCH_SIZE);
        int batchY = (int) Math.floor(((entity.getPos().y() / TILE_SIZE) / CHUNK_SIZE) / BATCH_SIZE);
        return new Vector2f(batchX, batchY);
    }

    public static Vector2f getChunk(Vector2i pos) {
        int chunkX = pos.x() / Configuration.getDefaultChunkSize();
        int chunkY = pos.y() / Configuration.getDefaultChunkSize();
        return new Vector2f(chunkX, chunkY);
    }

    public static Vector2f getChunkObjectPos(Vector2f absolute) {
        return new Vector2f((float) Math.floor((absolute.x() % 512) / 32), (float) Math.floor((absolute.y() % 512) / 32));
    }

    public static Vector2f getChunkPos(Vector2f absolute) {
        return new Vector2f((float) Math.floor(absolute.x() / 512), (float) Math.floor(absolute.y() / 512));
    }

    public static Vector2f getTilePos(Vector2f absolute) {
        return new Vector2f((float) Math.floor(absolute.x() / 32), (float) Math.floor(absolute.y() / 32));
    }
}
