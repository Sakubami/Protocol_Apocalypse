package xyz.sakubami.protocol_apocalypse.server.saving.data;

import xyz.sakubami.protocol_apocalypse.shared.type.ObjectType;
import xyz.sakubami.protocol_apocalypse.client.logic.user_interfaces.InterfaceT;

import java.util.Map;

public class SerializedObject {
    public String pos;
    public ObjectType type;
    public String id;

    public Map<Integer, SerializedItemStack> items;
    public InterfaceT interfaceT;

    public SerializedObject() {}
}
