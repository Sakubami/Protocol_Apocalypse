package de.sakubami.protocol_apocalypse.server.saving.data;

import de.sakubami.protocol_apocalypse.shared.type.ObjectType;
import de.sakubami.protocol_apocalypse.client.logic.user_interfaces.InterfaceT;

import java.util.Map;

public class SerializedObject {
    public String pos;
    public ObjectType type;
    public String id;

    public Map<Integer, SerializedItemStack> items;
    public InterfaceT interfaceT;

    public SerializedObject() {}
}
