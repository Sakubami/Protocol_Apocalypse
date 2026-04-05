package xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate;

import xyz.sakubami.protocol_apocalypse.shared.types.Type;
import xyz.sakubami.protocol_apocalypse.shared.utils.PositionalVector;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

public interface State {
    Vector2f getPos();
    Type getType();
}
