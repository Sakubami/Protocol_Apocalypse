package de.sakubami.protocol_apocalypse.shared.network.client.gamestate;

import de.sakubami.protocol_apocalypse.shared.type.Type;

public interface State<T>{
    Type getType();
    T copy(T original);
}
