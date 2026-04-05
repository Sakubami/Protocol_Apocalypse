package xyz.sakubami.protocol_apocalypse.client;

import xyz.sakubami.protocol_apocalypse.client.logic.ClientWorld;
import xyz.sakubami.protocol_apocalypse.client.logic.input.InputHandler;
import xyz.sakubami.protocol_apocalypse.shared.Configuration;
import xyz.sakubami.protocol_apocalypse.shared.network.client.gamestate.GameState;
import xyz.sakubami.protocol_apocalypse.server.Server;
import xyz.sakubami.protocol_apocalypse.shared.network.Connection;
import xyz.sakubami.protocol_apocalypse.shared.network.Packet;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver.C2SPlayerConnectPacket;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.ClientPacketHandler;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Connection connection;
    private Server localServer;
    private final ClientWorld state;
    private final InputHandler inputHandler;

    public Client() {
        this.state = new ClientWorld();
        this.inputHandler = new InputHandler();
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
        System.out.println("CLIENT GAMETICK HAPPENED");
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
    public InputHandler getInputHandler() { return this.inputHandler; }
    public ClientWorld getCurrentWorldData() { return this.state; }
}
