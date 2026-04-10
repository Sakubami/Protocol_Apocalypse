package xyz.sakubami.protocol_apocalypse.shared.network.validation;

import xyz.sakubami.protocol_apocalypse.server.Server;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.GameStateBuilder;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.ObjectState;
import xyz.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SBlockUpdateValidationPacket;
import xyz.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SMovementValidationPacket;
import xyz.sakubami.protocol_apocalypse.shared.utils.Coordinates;
import xyz.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.util.LinkedList;
import java.util.Queue;

public class Validation {

    private final Server server;
    private GameStateBuilder builder;
    private final Queue<EntityState> entities = new LinkedList<>();
    private final Queue<ObjectState> objects = new LinkedList<>();

    public Validation(Server server) {
        this.server = server;
        // loadConfig or something
    }

    public void tick(GameStateBuilder builder) {
        while (!entities.isEmpty()) {
            builder.updateEntity(entities.poll());
        }

        while (!objects.isEmpty()) {
            builder.updateObject(objects.poll());
        }
    }

    public void validateMovement(C2SMovementValidationPacket p) {
        EntityState state = new EntityState(server.getOnlinePlayer(p.getPlayerUUID()));

        System.out.println("validating... : " + p.getLocation() + " vs destination: " + p.getDestination());
        if (p.getDestination().x() > 10f || p.getDestination().y() > 10f || (p.getDestination().x() + p.getDestination().y()) > 6.66f) {
            state.pos = p.getLocation();
            entities.add(state);
            System.out.println("REJECTED PLAYER MOVEMENT " + state.pos);
        } else {
            state.pos = p.getLocation().add(p.getDestination());
            entities.add(state);
            System.out.println("ACCEPTED player MOVEMENT " + state.pos);
        }
    }

    public void validateBlockUpdate(C2SBlockUpdateValidationPacket p) {
        Vector2f pos = Coordinates.getTilePos(p.getPos().subtract(server.getOnlinePlayer(p.getUuid()).getPos()));
        if (pos.x() > 5 || pos.y() > 5 || pos.x() + pos.y() > 6) {
            System.out.println("REJECTED OBJECT");
            return;
        }
        objects.add(p.getState());
        System.out.println("ACCEPTED OBJECT AT: " + p.getPos() + "object pos: " + p.getState().pos);
    }
}
