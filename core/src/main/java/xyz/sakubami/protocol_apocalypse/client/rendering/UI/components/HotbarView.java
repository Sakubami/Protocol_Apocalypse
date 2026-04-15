package xyz.sakubami.protocol_apocalypse.client.rendering.UI.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import xyz.sakubami.protocol_apocalypse.server.logic.inventory.EntityInventory;
import xyz.sakubami.protocol_apocalypse.server.logic.items.ItemStack;

public class HotbarView {

    private final Table root = new Table();
    private final SlotView[] slots = new SlotView[4];
    private final EntityInventory inventory;

    public HotbarView(EntityInventory inventory, TextureRegion slotBackground) {
        this.inventory = inventory;

        root.setFillParent(true);
        root.bottom().left().pad(20);

        build(slotBackground);
    }

    public void update() {
        for (int i = 0; i < 4; i++) {
            ItemStack item = inventory.getHotbarItem(i);

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

                System.out.println("CLICKED SLOT LOL");
                // select this one
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
