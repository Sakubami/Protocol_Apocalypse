package de.sakubami.protocol_apocalypse.shared.network.packets.servertoclient;

import de.sakubami.protocol_apocalypse.client.logic.user_interfaces.InterfaceHelper;
import de.sakubami.protocol_apocalypse.client.logic.user_interfaces.InterfaceT;
import de.sakubami.protocol_apocalypse.shared.network.Packet;
import de.sakubami.protocol_apocalypse.shared.network.packets.handlers.PacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

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

    @Override
    public UUID getSignature() {
        return null;
    }
}
