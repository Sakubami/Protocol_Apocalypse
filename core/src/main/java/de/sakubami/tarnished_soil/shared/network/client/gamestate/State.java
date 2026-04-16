package de.sakubami.tarnished_soil.shared.network.client.gamestate;

import de.sakubami.tarnished_soil.shared.type.Type;

public interface State<T>{
    Type getType();
    T copy(T original);
}
