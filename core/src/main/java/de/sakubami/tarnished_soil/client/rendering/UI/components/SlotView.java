package de.sakubami.tarnished_soil.client.rendering.UI.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import de.sakubami.tarnished_soil.client.rendering.textures.TextureManager;
import de.sakubami.tarnished_soil.client.rendering.textures.registry.ItemTexture;
import de.sakubami.tarnished_soil.server.logic.items.ItemStack;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.ItemState;
import de.sakubami.tarnished_soil.shared.type.ItemType;

public class SlotView extends Stack {

    private final Image itemImage = new Image();

    public SlotView(TextureRegion background) {
        Image background1 = new Image(background);

        background1.setScaling(Scaling.stretch);
        itemImage.setScaling(Scaling.fit);

        add(background1);
        add(itemImage);

        setSize(background.getRegionWidth(), background.getRegionHeight());
    }

    public void setItem(ItemState item) {
        if (item.getType().equals(ItemType.PLACEHOLDER)) {
            itemImage.setDrawable(null);
            return;
        }

        TextureRegion texture = TextureManager.get().getItemTexture(ItemTexture.SWORD0);

        if (texture == null) {
            itemImage.setDrawable(null);
            return;
        }

        itemImage.setDrawable(new TextureRegionDrawable(texture));
    }

    public void setSelected(boolean v) {
        if (v) {
            setScale(1.2f);
            setColor(Color.ORANGE);
        } else {
            setScale(1.0f);
            setColor(Color.WHITE);
        }
    }
}
