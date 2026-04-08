package xyz.sakubami.protocol_apocalypse.shared.network.validation;

import xyz.sakubami.protocol_apocalypse.server.Server;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.GameStateBuilder;
import xyz.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SBlockUpdateValidationPacket;
import xyz.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SMovementValidationPacket;
import xyz.sakubami.protocol_apocalypse.shared.utils.Coordinates;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.util.*;

public class Validation {

    private final Server server;
    private GameStateBuilder builder;

    public Validation(Server server) {
        this.server = server;
        // loadConfig or something
    }

    public void refresh(GameStateBuilder builder) {
        this.builder = builder;
    }

    public void validateMovement(C2SMovementValidationPacket p) {
        Vector2f v = p.getDestination().subtract(p.getLocation());
        float difference = v.x() + v.y();
        EntityState state = new EntityState(server.getOnlinePlayer(p.getPlayerUUID()));

        if (difference > 20f) {
            state.pos = p.getLocation();
            builder.updateEntity(state);
            System.out.println("REJECTED PLAYER MOVEMENT");
        } else {
            state.pos = p.getDestination();
            builder.updateEntity(state);
            System.out.println("ACCEPTED player MOVEMENT");
        }
    }

    public void validateBlockUpdate(C2SBlockUpdateValidationPacket p) {
        Vector2f pos = Coordinates.getTilePos(p.getPos().subtract(server.getOnlinePlayer(p.getUuid()).getPos()));
        System.out.println("TREE POS ON VALIDATION: " + pos);
        if ((pos.x() + pos.y()) > 10) {
            System.out.println("REJECTED OBJECT");
            return;
        }
        builder.updateObject(p.getState());
    }
}
