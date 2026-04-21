package de.sakubami.tarnished_soil.client.rendering.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.LinkedList;
import java.util.Queue;

public class EffectManager {
    private static final EffectManager INSTANCE = new EffectManager();
    private final Array<Effect> effects = new Array<>();

    private EffectManager() {}

    public void update(float delta) {
        for (int i = effects.size - 1; i >= 0; i--) {
            Effect effect = effects.get(i);
            effect.update(delta);

            if (effect.isFinished())
                effects.removeIndex(i);
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = effects.size - 1; i >= 0; i--) {
            effects.get(i).render(batch);
        }
    }

    public static EffectManager get() { return INSTANCE; }
}
