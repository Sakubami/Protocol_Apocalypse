package de.sakubami.protocol_apocalypse.server.logic.objects.container.interfaces;

import de.sakubami.protocol_apocalypse.shared.type.ObjectType;
import de.sakubami.protocol_apocalypse.client.logic.user_interfaces.InterfaceT;

public class Chest extends InterfaceHolder {
    public Chest() {
        super(ObjectType.CHEST, "CHEST", InterfaceT.INVENTORY);
    }

    public void openInterface() {
        super.openInterfaceMethod();
    }
}
