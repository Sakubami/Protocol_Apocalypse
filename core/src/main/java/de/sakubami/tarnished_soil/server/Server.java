package de.sakubami.tarnished_soil.server;

import de.sakubami.tarnished_soil.server.logic.world.World;
import de.sakubami.tarnished_soil.server.logic.world.entities.livingentity.Player;
import de.sakubami.tarnished_soil.server.saving.Saviour;
import de.sakubami.tarnished_soil.server.logic.world.WorldManager;
import de.sakubami.tarnished_soil.shared.network.Connection;
import de.sakubami.tarnished_soil.shared.network.Packet;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.EntityState;
import de.sakubami.tarnished_soil.shared.network.client.gamestate.GameStateBuilder;
import de.sakubami.tarnished_soil.shared.network.packets.handlers.ServerPacketHandler;
import de.sakubami.tarnished_soil.shared.network.packets.servertoclient.S2CGameStatePacket;
import de.sakubami.tarnished_soil.shared.network.validation.Validation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private final List<Connection> clients = new CopyOnWriteArrayList<>();
    private ServerSocket serverSocket;
    private volatile boolean running = false;
    private final ServerPacketHandler handler = new ServerPacketHandler(this);
    private World world;
    private Saviour saviour;
    private final Validation validation = new Validation(this);

    private final Queue<Player> pendingPlayers = new LinkedList<>();
    private final Map<Connection, UUID> connectedPlayers = new HashMap<>();
    private final List<UUID> offlinePlayers = new ArrayList<>();

    public Server() {}

    public void start(int port) throws IOException {
        running = true;
        serverSocket = new ServerSocket(port);
        setupWorld("BITCH");
        System.out.println("Server started on port " + port);

        new Thread(() -> {
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    Connection connection = new Connection(clientSocket, true);
                    clients.add(connection);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    if(running) e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (running) {
                tick();
                try {
                    Thread.sleep(33); // ~30 ticks per second
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }

    private void tick() {
        for (Connection c : clients) {
            if (!c.isAlive()) {
                disconnectClient(c);
                continue;
            }
            c.tick(handler); // read & execute packets
        }

        if (this.connectedPlayers.isEmpty())
            return;

        GameStateBuilder stateBuilder = new GameStateBuilder();
        validation.tick(stateBuilder);

        //pretty bad but update players here before broadcasting lol
        for (Map.Entry<UUID, EntityState> state : stateBuilder.getPlayers().entrySet()) {
            //TODO WIP REPLACE THIS
        }

        S2CGameStatePacket packet = world.tick(stateBuilder).compile();
        if (!packet.empty) {
            broadcast(packet);
        }

        saviour.tick();
    }

    private void disconnectClient(Connection c) {
        c.disconnect();
        this.world.removePlayer(this.connectedPlayers.get(c));
        this.clients.remove(c);
        System.out.println("DISCONNECTED CLIENT!!!");
    }

    public void setupWorld(String name) {
        this.world = WorldManager.get().selectWorld(name);
        this.saviour = new Saviour(world.getDirectory());
    }

    public void broadcast(Packet packet) {
        for (Connection c : clients) {
            c.send(packet);
        }
    }

    public void stop() throws IOException {
        WorldManager.get().saveWorld(world);

        this.running = false;
        serverSocket.close();
        for (Connection c : clients) {
            c.disconnect();
        }
    }

    public World getWorld() { return world; }
    public List<UUID> getOfflinePlayers() { return this.offlinePlayers; }
    public void connectPlayer(Connection adress, Player player) {
        this.connectedPlayers.put(this.clients.stream().filter(c -> c.getUUID().equals(adress.getUUID())).findFirst().get(), player.getUuid());
        this.world.addPlayer(player);
    }
    public Validation getValidation() { return this.validation; }
}
