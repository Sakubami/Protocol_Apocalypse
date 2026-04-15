package xyz.sakubami.protocol_apocalypse.client.rendering.UI.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import xyz.sakubami.protocol_apocalypse.client.rendering.textures.TextureHelper;
import xyz.sakubami.protocol_apocalypse.server.logic.items.ItemStack;

public class SlotView extends Stack {

    private final Image background;
    private final Image itemImage = new Image();

    public SlotView(TextureRegion background) {
        this.background = new Image(background);

        this.background.setScaling(Scaling.stretch);
        itemImage.setScaling(Scaling.fit);

        add(this.background);
        add(itemImage);

        setSize(background.getRegionWidth(), background.getRegionHeight());
    }

    public void setItem(ItemStack item) {
        if (item == null) {
            itemImage.setDrawable(null);
            return;
        }

        TextureRegion texture = TextureHelper.getDefaultItemTexture(item.type);

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
