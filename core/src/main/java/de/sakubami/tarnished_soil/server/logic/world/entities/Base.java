package de.sakubami.tarnished_soil.server.logic.world.entities;

import de.sakubami.tarnished_soil.shared.network.client.gamestate.State;

public interface Base<T> {
    T getObject(State<?> state);
}
