package de.sakubami.protocol_apocalypse.shared.network.validation;

import de.sakubami.protocol_apocalypse.server.Server;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.GameStateBuilder;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.ObjectState;
import de.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SBlockUpdateValidationPacket;
import de.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SMovementValidationPacket;
import de.sakubami.protocol_apocalypse.shared.utils.Coordinates;
import de.sakubami.protocol_apocalypse.shared.utils.Vector2f;

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
        state.direction = p.getDirection();
        Vector2f a = p.getLocation();
        Vector2f b = p.getLocation().add(p.getMovement());
        float c = p.getMovement().x() + p.getMovement().y();
        float d = p.getLocation().x() + p.getLocation().y();
        float e = c + d;

        if (Math.abs(b.x() - a.x()) > 10f || Math.abs(b.y() - a.y()) > 10f || (Math.abs(e - d) > 6.66f)) {
            state.pos = p.getLocation().subtract(p.getMovement());
            entities.add(state);
            System.out.println("REJECTED PLAYER MOVEMENT " + p.getMovement());
        } else {
            state.pos = p.getLocation().add(p.getMovement());
            entities.add(state);
            System.out.println("ACCEPTED PLAYER MOVEMENT " + p.getMovement());
        }
    }

    public void validateBlockUpdate(C2SBlockUpdateValidationPacket p) {
        Vector2f pos = Coordinates.getTilePos(p.getPos().subtract(server.getOnlinePlayer(p.getUuid()).getPos()));
        if (pos.x() > 5 || pos.y() > 5 || pos.x() + pos.y() > 6) {
            return;
        }
        objects.add(p.getState());
        System.out.println("ADDED BLOCK AT: " + p.getState().pos);
    }
}
