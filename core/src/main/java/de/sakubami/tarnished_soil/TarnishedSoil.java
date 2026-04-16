package de.sakubami.tarnished_soil;

import com.badlogic.gdx.Game;
import de.sakubami.tarnished_soil.client.Client;
import de.sakubami.tarnished_soil.client.logic.input.InputHandler;
import de.sakubami.tarnished_soil.client.rendering.textures.TextureManager;
import de.sakubami.tarnished_soil.server.logic.inventory.InventoryRegistry;
import de.sakubami.tarnished_soil.server.logic.objects.ObjectRegistry;
import de.sakubami.tarnished_soil.client.screens.TitleScreen;
import de.sakubami.tarnished_soil.server.logic.world.WorldManager;
import de.sakubami.tarnished_soil.server.logic.world.entities.EntityRegistry;
import de.sakubami.tarnished_soil.shared.utils.DirectoryHelper;

public class TarnishedSoil extends Game {
    private Client client;
    private InputHandler inputHandler;

    @Override
    public void create() {
        ObjectRegistry.init();
        EntityRegistry.init();
        InventoryRegistry.init();
        DirectoryHelper.init();
        WorldManager.init();
        TextureManager.init();

        client = new Client();
        inputHandler = new InputHandler();

        setScreen(new TitleScreen(this));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        client.disconnect();
    }

    public Client getClient() { return this.client; }
    public InputHandler getInputHandler() { return this.inputHandler; }
}
