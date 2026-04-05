package xyz.sakubami.protocol_apocalypse.server.logic.objects.container.interfaces;

import xyz.sakubami.protocol_apocalypse.server.logic.objects.container.ItemHolder;
import xyz.sakubami.protocol_apocalypse.server.saving.data.SerializedObject;
import xyz.sakubami.protocol_apocalypse.shared.types.ObjectType;
import xyz.sakubami.protocol_apocalypse.client.logic.user_interfaces.InterfaceT;

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
