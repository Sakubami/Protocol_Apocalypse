package de.sakubami.tarnished_soil.shared.network.client.gamestate;

import de.sakubami.tarnished_soil.shared.utils.Vector2f;

public interface RenderSortable<T> extends State<T> {
    Vector2f getPos();
}
