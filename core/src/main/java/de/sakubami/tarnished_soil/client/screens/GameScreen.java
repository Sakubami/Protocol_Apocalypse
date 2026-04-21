package de.sakubami.tarnished_soil.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.sakubami.tarnished_soil.client.Client;
import de.sakubami.tarnished_soil.client.logic.input.InputHandler;
import de.sakubami.tarnished_soil.TarnishedSoil;
import de.sakubami.tarnished_soil.client.rendering.UI.UIManager;
import de.sakubami.tarnished_soil.client.rendering.effects.EffectManager;
import de.sakubami.tarnished_soil.client.rendering.world.WorldRenderer;

import java.io.IOException;

public class GameScreen implements Screen {
    private final TarnishedSoil game;

    private final OrthographicCamera camera;
    private final ScreenViewport viewport;
    private final WorldRenderer renderer;
    private final SpriteBatch batch;
    private final InputHandler inputHandler;
    private final Client client;
    private final UIManager uiManager;

    private final int TILE_SIZE = 32;
    private final int MAP_HEIGHT = 20;
    private final int MAP_WIDTH = 20;

    private final float CAMERA_SPEED = 200f;

    public GameScreen(TarnishedSoil game) {
        Gdx.graphics.setForegroundFPS(60);

        this.game = game;

        camera = new OrthographicCamera();
        batch = new SpriteBatch();

        client = game.getClient();

        try {
            client.hostLocal(25556);
        } catch (IOException exception) {
            exception.printStackTrace();
        };

        inputHandler = game.getInputHandler();
        renderer = new WorldRenderer();
        uiManager = new UIManager();

        viewport = new ScreenViewport(camera); // 1 world unit = 1 pixel
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        game.setScreen(this);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        uiManager.getStage().getViewport().update(width, height, true);
    }

    @Override
    public void render(float v) {
        client.update();
        inputHandler.handle(Gdx.input, client.getPrediction(), Gdx.graphics.getDeltaTime(), camera);// game second

        Gdx.input.setInputProcessor(uiManager.getStage());

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) camera.zoom -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.E)) camera.zoom += 1;

        camera.zoom = 1f;

        camera.position.x = Math.round(client.getPlayerPos().x());
        camera.position.y = Math.round(client.getPlayerPos().y());

        camera.update();

        ScreenUtils.clear(0, 0, 0, 1); // clear to black

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        renderer.render(batch, client.getCurrentWorldData());
        EffectManager.get().render(Gdx.graphics.getDeltaTime());
        batch.end();
        uiManager.update(Gdx.graphics.getDeltaTime(), client.getPlayer());
        uiManager.render();
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {

    }

    @Override public void show() {}
}
