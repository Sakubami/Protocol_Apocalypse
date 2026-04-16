package de.sakubami.tarnished_soil.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import de.sakubami.tarnished_soil.TarnishedSoil;

public class LoadingScreen implements Screen {
    private final TarnishedSoil game;
    private final SpriteBatch batch;
    private final BitmapFont font;

    public LoadingScreen(TarnishedSoil game) {
        Gdx.graphics.setForegroundFPS(60);
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "IM LOADING !!!!!!!!!!!!!!!!!!!!!!!!!!!",
            0, Gdx.graphics.getHeight() / 2f + 50,
            Gdx.graphics.getWidth(), Align.center, false);

        font.draw(batch, "Press ENTER",
            0, Gdx.graphics.getHeight() / 2f - 50,
            Gdx.graphics.getWidth(), Align.center, false);

        batch.end();
        // test lol

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            //
            //if (!gamescreen.loadsomething)
            // render bar progress 33%
            // return;
            // and so on (very crude lol)
            // just make sure not to load it over and over again with like a state inside the screen or something

            new GameScreen(game);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
