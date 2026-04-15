package de.sakubami.protocol_apocalypse.server;

import de.sakubami.protocol_apocalypse.server.logic.world.World;
import de.sakubami.protocol_apocalypse.server.logic.world.entities.livingentity.Player;
import de.sakubami.protocol_apocalypse.server.saving.Saviour;
import de.sakubami.protocol_apocalypse.server.logic.world.WorldManager;
import de.sakubami.protocol_apocalypse.shared.network.Connection;
import de.sakubami.protocol_apocalypse.shared.network.Packet;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.EntityState;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.GameStateBuilder;
import de.sakubami.protocol_apocalypse.shared.network.packets.handlers.ServerPacketHandler;
import de.sakubami.protocol_apocalypse.shared.network.validation.Validation;

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
    private final Map<Connection, Player> connectedPlayers = new HashMap<>();
    private final Map<UUID, Player> onlinePlayers = new HashMap<>();
    private final Map<UUID, Player> offlinePlayers = new HashMap<>();

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

        if (this.onlinePlayers.isEmpty() || this.connectedPlayers.isEmpty())
            return;

        GameStateBuilder stateBuilder = new GameStateBuilder();
        validation.tick(stateBuilder);

        if (!pendingPlayers.isEmpty())
            stateBuilder.updateEntity(new EntityState(pendingPlayers.poll()));

        for (Map.Entry<UUID, EntityState> states : stateBuilder.getPlayers().entrySet()) {
            this.onlinePlayers.get(states.getKey()).setPos(states.getValue().pos);
        }

        broadcast(world.tick(onlinePlayers.values(), stateBuilder).compile());
        saviour.tick();

    }

    private void disconnectClient(Connection c) {
        c.disconnect();
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
    public Map<UUID, Player> getOnlinePlayers() { return this.onlinePlayers; }
    public Map<UUID, Player> getOfflinePlayers() { return this.offlinePlayers; }
    public void updatePlayer(Player player) { this.onlinePlayers.replace(player.getUuid(), player); }
    public void connectPlayer(Connection adress, Player player) {
        this.onlinePlayers.put(player.getUuid(), player);
        this.connectedPlayers.put(this.clients.stream().filter(c -> c.getUUID().equals(adress.getUUID())).findFirst().get(), player);
        this.pendingPlayers.add(player);
    }
    public void disconnectPlayer(UUID uuid) { this.onlinePlayers.remove(uuid); }
    public Player getOnlinePlayer(UUID uuid) { return this.onlinePlayers.get(uuid); }
    public Player getOfflinePlayer(UUID uuid) { return this.offlinePlayers.get(uuid); }
    public Validation getValidation() { return this.validation; }
}
