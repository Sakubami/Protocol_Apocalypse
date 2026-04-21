package de.sakubami.tarnished_soil.client.rendering.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Effect {
    void update(float delta);
    void render(SpriteBatch batch);
    boolean isFinished();
}
