package de.sakubami.tarnished_soil.client.rendering.UI;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.sakubami.tarnished_soil.client.rendering.UI.components.HotbarView;
import de.sakubami.tarnished_soil.client.rendering.textures.TextureManager;
import de.sakubami.tarnished_soil.client.rendering.textures.registry.UITexture;
import de.sakubami.tarnished_soil.server.logic.inventory.EntityInventory;

public class UIManager {
    private final Stage stage;
    private final HotbarView hotbar;

    public UIManager() {
        stage = new Stage(new ScreenViewport());

        hotbar = new HotbarView(new EntityInventory(), TextureManager.get().getUITexture(UITexture.SLOT0));
        stage.addActor(hotbar.getRoot());
    }

    public void update(float delta) {
        hotbar.update();
        stage.act(delta);
    }

    public void render() {
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }
}
