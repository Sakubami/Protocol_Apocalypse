package de.sakubami.tarnished_soil.server.saving.data;

import de.sakubami.tarnished_soil.shared.type.ObjectType;
import de.sakubami.tarnished_soil.client.logic.user_interfaces.InterfaceT;

import java.util.Map;

public class SerializedObject {
    public String pos;
    public ObjectType type;
    public String id;

    public Map<Integer, SerializedItemStack> items;
    public InterfaceT interfaceT;

    public SerializedObject() {}
}
