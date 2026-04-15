package de.sakubami.protocol_apocalypse;

import com.badlogic.gdx.Game;
import de.sakubami.protocol_apocalypse.client.Client;
import de.sakubami.protocol_apocalypse.client.logic.input.InputHandler;
import de.sakubami.protocol_apocalypse.client.rendering.textures.TextureManager;
import de.sakubami.protocol_apocalypse.server.logic.inventory.InventoryRegistry;
import de.sakubami.protocol_apocalypse.server.logic.items.ItemRegistry;
import de.sakubami.protocol_apocalypse.server.logic.objects.ObjectRegistry;
import de.sakubami.protocol_apocalypse.client.screens.TitleScreen;
import de.sakubami.protocol_apocalypse.server.logic.world.WorldManager;
import de.sakubami.protocol_apocalypse.server.logic.world.entities.EntityRegistry;
import de.sakubami.protocol_apocalypse.shared.utils.DirectoryHelper;

public class ProtocolApocalypse extends Game {
    private Client client;
    private InputHandler inputHandler;

    @Override
    public void create() {
        ObjectRegistry.init();
        EntityRegistry.init();
        InventoryRegistry.init();
        ItemRegistry.init();
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
