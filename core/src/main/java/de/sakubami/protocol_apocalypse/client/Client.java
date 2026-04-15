package de.sakubami.protocol_apocalypse.client;

import de.sakubami.protocol_apocalypse.client.logic.ClientWorld;
import de.sakubami.protocol_apocalypse.client.logic.Prediction;
import de.sakubami.protocol_apocalypse.shared.Configuration;
import de.sakubami.protocol_apocalypse.shared.network.client.gamestate.GameState;
import de.sakubami.protocol_apocalypse.server.Server;
import de.sakubami.protocol_apocalypse.shared.network.Connection;
import de.sakubami.protocol_apocalypse.shared.network.Packet;
import de.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver.C2SPlayerConnectPacket;
import de.sakubami.protocol_apocalypse.shared.network.packets.handlers.ClientPacketHandler;
import de.sakubami.protocol_apocalypse.shared.utils.Vector2f;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Connection connection;
    private Server localServer;
    private final ClientWorld state;
    private Prediction prediction;

    public Client() {
        this.state = new ClientWorld();
        this.prediction = new Prediction(this);
    }

    public void hostLocal(int port) throws IOException {
        localServer = new Server();
        localServer.start(port);
        connect("localhost", port);
    }

    public void connect(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        connection = new Connection(socket, false);
        System.out.println("Connected to server: " + host + ":" + port);

        connection.send(new C2SPlayerConnectPacket(Configuration.getClientPlayerUUID()));
    }

    public void update() {
        connection.tick(new ClientPacketHandler(this));
    }

    public void sendPacket(Packet packet) {
        connection.send(packet);
    }

    public void disconnect() {
        if (connection != null) {
            connection.disconnect();
        }
        if (localServer != null) {
            try {
                localServer.stop();
            } catch (IOException ignored) {}
        }
    }

    public boolean isHosting() { return localServer != null; }
    public void applyState(GameState state) { this.state.applyState(state);}
    public ClientWorld getCurrentWorldData() { return this.state; }
    public Vector2f getPlayerPos() {
        if (state.getPlayers().isEmpty())
            return new Vector2f(0, 0);
        return state.getPlayers().values().stream().filter(p -> p.uuid.equals(Configuration.getClientPlayerUUID())).findFirst().get().getPos();
    }
    public Prediction getPrediction() { return prediction; }
}
