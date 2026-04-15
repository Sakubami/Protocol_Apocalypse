package de.sakubami.protocol_apocalypse.shared.network.packets;

import de.sakubami.protocol_apocalypse.shared.network.Packet;
import de.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver.C2SBlockUpdatePacket;
import de.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver.C2SPlayerConnectPacket;
import de.sakubami.protocol_apocalypse.shared.network.packets.clienttoserver.C2SPlayerMovePacket;
import de.sakubami.protocol_apocalypse.shared.network.validation.correction.S2CMovementCorrectionPacket;
import de.sakubami.protocol_apocalypse.shared.network.packets.servertoclient.S2CGameStatePacket;
import de.sakubami.protocol_apocalypse.shared.network.packets.servertoclient.S2COpenInterfacePacket;
import de.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SBlockUpdateValidationPacket;
import de.sakubami.protocol_apocalypse.shared.network.validation.validation.C2SMovementValidationPacket;

import java.util.Arrays;

public enum PacketType {
    C2S_PLAYER_MOVE(0, new C2SPlayerMovePacket()),
    C2S_BLOCK_UPDATE(1, new C2SBlockUpdatePacket()),
    S2C_OPEN_INTERFACE(2, new S2COpenInterfacePacket()),
    S2C_GAME_STATE(3, new S2CGameStatePacket()),
    C2S_PLAYER_CONNECT(4, new C2SPlayerConnectPacket()),
    C2S_MOVEMENT_VALIDATION(5, new C2SMovementValidationPacket()),
    S2C_MOVEMENT_CORRECTION(6, new S2CMovementCorrectionPacket()),
    C2S_BLOCK_UPDATE_VALIDATION(7, new C2SBlockUpdateValidationPacket());

    private final int id;
    private final Packet packet;

    PacketType(int id, Packet packet) {
        this.id = id;
        this.packet = packet;
    }

    public Packet getPacket() { return packet; }
    public int getId() { return id; }
    public static PacketType getById(int id) {
        return Arrays.stream(PacketType.values()).filter(packet -> packet.getId() == id).findFirst().get();
    }
}
