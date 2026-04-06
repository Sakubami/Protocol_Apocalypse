package xyz.sakubami.protocol_apocalypse.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import xyz.sakubami.protocol_apocalypse.client.Client;
import xyz.sakubami.protocol_apocalypse.client.logic.input.InputHandler;
import xyz.sakubami.protocol_apocalypse.client.logic.interactions.InteractionManager;
import xyz.sakubami.protocol_apocalypse.client.rendering.ChunkRenderer;
import xyz.sakubami.protocol_apocalypse.client.rendering.EntityRenderer;
import xyz.sakubami.protocol_apocalypse.ProtocolApocalypse;
import xyz.sakubami.protocol_apocalypse.client.rendering.WorldRenderer;
import xyz.sakubami.protocol_apocalypse.server.Server;
import xyz.sakubami.protocol_apocalypse.shared.Configuration;

import java.io.IOException;

public class GameScreen implements Screen {
    private final ProtocolApocalypse game;

    private final OrthographicCamera camera;
    private final ScreenViewport viewport;
    private final WorldRenderer renderer;
    private final SpriteBatch batch;
    private final InteractionManager interactionManager;
    private final InputHandler inputHandler;
    private final Client client;

    private final int TILE_SIZE = 32;
    private final int MAP_HEIGHT = 20;
    private final int MAP_WIDTH = 20;

    private final float CAMERA_SPEED = 200f;

    public GameScreen(ProtocolApocalypse game) {
        Gdx.graphics.setForegroundFPS(60);

        this.game = game;

        camera = new OrthographicCamera();
        batch = new SpriteBatch();

        client = game.getClient();
        interactionManager = new InteractionManager(client);
        inputHandler = game.getInputHandler();

        renderer = new WorldRenderer();

        viewport = new ScreenViewport(camera); // 1 world unit = 1 pixel
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        game.setScreen(this);

        try {
            client.hostLocal(25556);
        } catch (IOException exception) {
            exception.printStackTrace();
        };
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float v) {
        client.update();
        inputHandler.handle(Gdx.input, client);

        float speed = 1000 * Gdx.graphics.getDeltaTime(); // pixels per second

        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.y += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.y -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.x -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.x += speed;

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) camera.zoom -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.E)) camera.zoom += 1;

        camera.zoom = MathUtils.clamp(camera.zoom, 0.5f, 20f);

        camera.position.x = Math.round(client.getPlayerPos().x());
        camera.position.y = Math.round(client.getPlayerPos().y());

        camera.update();

        ScreenUtils.clear(0, 0, 0, 1); // clear to black

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        renderer.render(batch, client.getCurrentWorldData());
        batch.end();
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {

    }

    @Override public void show() {}
}
