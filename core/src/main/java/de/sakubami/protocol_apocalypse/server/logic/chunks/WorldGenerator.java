package de.sakubami.protocol_apocalypse.server.logic.chunks;

import com.raylabz.opensimplex.OpenSimplexNoise;

public class WorldGenerator {
    private final OpenSimplexNoise noise;
    private final long seed;

    public WorldGenerator(long seed) {
        this.seed = seed;
        this.noise = new OpenSimplexNoise(seed);
    }

    public long getSeed() {
        return seed;
    }

    public double getNoise(double x, double y) {
        return noise.getNoise2D(x, y).getValue();
    }

}
