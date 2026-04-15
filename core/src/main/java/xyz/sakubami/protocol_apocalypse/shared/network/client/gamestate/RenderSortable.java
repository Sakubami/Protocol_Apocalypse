package xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate;

import xyz.sakubami.protocol_apocalypse.shared.type.Type;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

public interface RenderSortable<T> extends State<T> {
    Vector2f getPos();
}
