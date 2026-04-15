package de.sakubami.protocol_apocalypse.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import de.sakubami.protocol_apocalypse.ProtocolApocalypse;

public class TitleScreen implements Screen {
    private final ProtocolApocalypse game;
    private final SpriteBatch batch;
    private final BitmapFont font;

    public TitleScreen(ProtocolApocalypse game) {
        Gdx.graphics.setForegroundFPS(60);
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "this is a header lol",
            0, Gdx.graphics.getHeight() / 2f + 50,
            Gdx.graphics.getWidth(), Align.center, false);

        font.draw(batch, "Press ENTER",
            0, Gdx.graphics.getHeight() / 2f - 50,
            Gdx.graphics.getWidth(), Align.center, false);

        batch.end();
        // test lol

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            LoadingScreen loadingScreen = new LoadingScreen(game);
            game.setScreen(loadingScreen); // Change to your actual game screen
        }
    }

    @Override public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}
}
