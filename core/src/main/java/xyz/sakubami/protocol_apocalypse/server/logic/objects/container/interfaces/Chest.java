package xyz.sakubami.protocol_apocalypse.server.logic.objects.container.interfaces;

import xyz.sakubami.protocol_apocalypse.shared.type.ObjectType;
import xyz.sakubami.protocol_apocalypse.client.logic.user_interfaces.InterfaceT;

public class Chest extends InterfaceHolder {
    public Chest() {
        super(ObjectType.CHEST, "CHEST", InterfaceT.INVENTORY);
    }

    public void openInterface() {
        super.openInterfaceMethod();
    }
}
