package de.sakubami.tarnished_soil.server.logic.objects.container.interfaces;

import de.sakubami.tarnished_soil.server.logic.objects.container.ItemHolder;
import de.sakubami.tarnished_soil.server.saving.data.SerializedObject;
import de.sakubami.tarnished_soil.shared.type.ObjectType;
import de.sakubami.tarnished_soil.client.logic.user_interfaces.InterfaceT;

public abstract class InterfaceHolder extends ItemHolder {
    private final InterfaceT interfaceT;

    public InterfaceHolder(ObjectType texture, String id, InterfaceT interfaceT) {
        super(texture);
        this.interfaceT = interfaceT;
    }

    protected void openInterfaceMethod() {
        System.out.println("I OPENED THE INTERFACE");
    }

    @Override
    public SerializedObject toData() {
        SerializedObject data = super.toData();
        data.interfaceT = interfaceT;
        return data;
    }
}
