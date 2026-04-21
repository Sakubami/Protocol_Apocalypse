package de.sakubami.tarnished_soil.client.rendering.UI;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.sakubami.tarnished_soil.client.Client;
import de.sakubami.tarnished_soil.client.rendering.UI.components.HotbarView;
import de.sakubami.tarnished_soil.client.rendering.textures.TextureManager;
import de.sakubami.tarnished_soil.client.rendering.textures.registry.UITexture;
import de.sakubami.tarnished_soil.server.logic.inventory.EntityInventory;
import de.sakubami.tarnished_soil.shared.Configuration;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.EntityState;

public class UIManager {
    private final Stage stage;
    private final HotbarView hotbar;

    public UIManager() {
        stage = new Stage(new ScreenViewport());

        hotbar = new HotbarView(TextureManager.get().getUITexture(UITexture.SLOT0));
        stage.addActor(hotbar.getRoot());
    }

    public void update(float delta, EntityState player) {
        hotbar.update(player);
        stage.act(delta);
    }

    public void render() {
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }
}
