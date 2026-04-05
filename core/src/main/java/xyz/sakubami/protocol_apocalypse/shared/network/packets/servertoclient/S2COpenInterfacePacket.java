package xyz.sakubami.protocol_apocalypse.shared.network.packets.servertoclient;

import xyz.sakubami.protocol_apocalypse.client.logic.user_interfaces.InterfaceHelper;
import xyz.sakubami.protocol_apocalypse.client.logic.user_interfaces.InterfaceT;
import xyz.sakubami.protocol_apocalypse.shared.network.Packet;
import xyz.sakubami.protocol_apocalypse.shared.network.packets.handlers.PacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class S2COpenInterfacePacket implements Packet {
    private InterfaceT type;

    public S2COpenInterfacePacket() {}

    public S2COpenInterfacePacket(InterfaceT interfaceT) {
        this.type = interfaceT;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeUTF(type.name());
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        this.type = InterfaceT.valueOf(in.readUTF());
    }

    @Override
    public void execute(PacketHandler handler) {
        InterfaceHelper.openInterface(type);
    }

    @Override
    public int getId() {
        return 2;
    }
}
