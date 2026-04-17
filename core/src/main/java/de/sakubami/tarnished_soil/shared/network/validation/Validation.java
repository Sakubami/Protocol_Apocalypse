package de.sakubami.tarnished_soil.shared.network.validation;

import de.sakubami.tarnished_soil.server.Server;
import de.sakubami.tarnished_soil.server.logic.objects.GameObject;
import de.sakubami.tarnished_soil.server.logic.world.entities.Entity;
import de.sakubami.tarnished_soil.server.logic.world.entities.livingentity.Player;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.EntityState;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.GameStateBuilder;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.ObjectState;
import de.sakubami.tarnished_soil.shared.network.validation.validation.C2SBlockUpdateValidationPacket;
import de.sakubami.tarnished_soil.shared.network.validation.validation.C2SMovementValidationPacket;
import de.sakubami.tarnished_soil.shared.utils.Coordinates;
import de.sakubami.tarnished_soil.shared.utils.Vector2f;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Validation {

    private final Server server;
    private GameStateBuilder builder;
    private final Queue<EntityState> entities = new LinkedList<>();
    private final Queue<ObjectState> objects = new LinkedList<>();
    private List<GameObject> serverObjects = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

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

    public List<GameObject> pollObjects() {
        List<GameObject> a = serverObjects;
        this.serverObjects = new ArrayList<>();
        return a;
    }

    public List<Player> pollPlayers() {
        List<Player> a = players;
        this.players = new ArrayList<>();
        return a;
    }

    public void validateMovement(C2SMovementValidationPacket p) {
        EntityState state = new EntityState(server.getPlayer(p.getPlayerUUID()));
        Player player = server.getPlayer(p.getPlayerUUID());
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
            player.setPos(p.getLocation().add(p.getMovement()));
            entities.add(state);
            players.add(player);
            System.out.println("ACCEPTED PLAYER MOVEMENT " + p.getMovement());
        }
    }

    public void validateBlockUpdate(C2SBlockUpdateValidationPacket p) {
        Vector2f pos = Coordinates.getTilePos(p.getPos().subtract(server.getPlayer(p.getUuid()).getPos()));
        if (pos.x() > 5 || pos.y() > 5 || pos.x() + pos.y() > 6) {
            return;
        }
        objects.add(p.getState());
        serverObjects.add(GameObject.createFromData(p.getState()));
        System.out.println("ADDED BLOCK AT: " + p.getState().pos);
    }
}
