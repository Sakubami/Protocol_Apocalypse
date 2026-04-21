package de.sakubami.tarnished_soil.client.rendering.UI.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.sakubami.tarnished_soil.client.Client;
import de.sakubami.tarnished_soil.server.logic.inventory.EntityInventory;
import de.sakubami.tarnished_soil.server.logic.items.ItemStack;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.EntityState;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.InventoryState;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.ItemState;
import de.sakubami.tarnished_soil.shared.type.ItemType;

public class HotbarView {

    private final Table root = new Table();
    private final SlotView[] slots = new SlotView[4];

    public HotbarView(TextureRegion slotBackground) {

        root.setFillParent(true);
        root.bottom().left().pad(20);

        build(slotBackground);
    }

    public void update(EntityState player) {
        for (int i = 0; i < player.inventory.getHotbarSize(); i++) {
            ItemState item = player.inventory.hotbar[i];
            slots[i].setItem(item);
        }
    }

    public void build(TextureRegion slotBackGround) {
        root.add(createSlot(0, slotBackGround)).colspan(3).center().padBottom(10);
        root.row();

        root.add(createSlot(1, slotBackGround)).padRight(10);
        root.add();
        root.add(createSlot(3, slotBackGround)).padLeft(10);
        root.row();

        root.add(createSlot(2, slotBackGround)).colspan(3).center().padTop(10);
    }

    public SlotView createSlot(int index, TextureRegion slotBackGround) {
        SlotView slot = new SlotView(slotBackGround);

        slot.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // clear previous selection
                for (SlotView s : slots) {
                    if (s != null) s.setSelected(false);
                }
                slot.setSelected(true);
            }
        });

        slots[index] = slot;
        return slot;
    }

    public Table getRoot() {
        return root;
    }
}
