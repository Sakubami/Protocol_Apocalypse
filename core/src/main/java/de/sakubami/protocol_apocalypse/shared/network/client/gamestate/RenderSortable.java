package de.sakubami.protocol_apocalypse.shared.network.client.gamestate;

import de.sakubami.protocol_apocalypse.shared.utils.Vector2f;

public interface RenderSortable<T> extends State<T> {
    Vector2f getPos();
}
