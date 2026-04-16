package de.sakubami.tarnished_soil.server.logic.objects.container.interfaces;

import de.sakubami.tarnished_soil.shared.type.ObjectType;
import de.sakubami.tarnished_soil.client.logic.user_interfaces.InterfaceT;

public class Chest extends InterfaceHolder {
    public Chest() {
        super(ObjectType.CHEST, "CHEST", InterfaceT.INVENTORY);
    }

    public void openInterface() {
        super.openInterfaceMethod();
    }
}
