package xyz.sakubami.protocol_apocalypse.shared.network;

import xyz.sakubami.protocol_apocalypse.shared.network.packets.PacketType;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver.C2SPlayerConnectPacket;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.PacketHandler;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Connection implements Runnable{

    private final boolean isServer;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final Queue<Packet> incoming = new ConcurrentLinkedQueue<>();

    private volatile boolean running = true;


    public Connection(Socket socket, boolean isServer) throws IOException {
        this.socket = socket;
        this.isServer = isServer;
        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        new Thread(this, "Connection-" + socket.getRemoteSocketAddress()).start();
    }

    public synchronized void send(Packet packet) {
        if(!running) return;
        try {
            out.writeInt(packet.getId());  // ID comes directly from the packet
            packet.write(out);             // Write packet data
            out.flush();
        } catch (IOException e) {
            if (running) {
                e.printStackTrace();
            }
            disconnect();
        }
    }

    @Override
    public void run() {
        try {
            while (running) {
                int id = in.readInt();
                Packet packet = PacketType.getById(id).getPacket(); // Simple factory method
                if (packet == null) continue;
                if (packet instanceof C2SPlayerConnectPacket) {
                    ((C2SPlayerConnectPacket) packet).addConnection(this);
                }
                packet.read(in);
                incoming.add(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    public void tick(PacketHandler handler) {
        Packet packet;
        while ((packet = incoming.poll()) != null) {
            handler.handle(packet);
        }
    }

    public void disconnect() {
        running = false;
        try { socket.close(); } catch (IOException ignored) {}
    }

    public boolean isAlive() { return running && !socket.isClosed(); }

    /** Getters for input/output streams if needed */
    public DataInputStream getInputStream() { return in; }
    public DataOutputStream getOutputStream() { return out; }
    public InetAddress getID() { return socket.getInetAddress(); }
    public boolean isServer() { return this.isServer; }
}
