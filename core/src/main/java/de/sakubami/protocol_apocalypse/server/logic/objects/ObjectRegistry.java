package de.sakubami.protocol_apocalypse.server.logic.objects;

import de.sakubami.protocol_apocalypse.server.logic.objects.container.interfaces.Chest;
import de.sakubami.protocol_apocalypse.server.logic.objects.normal.Tree;
import de.sakubami.protocol_apocalypse.shared.type.ObjectType;

public class ObjectRegistry {
    static {
        GameObject.registerType(ObjectType.CHEST, Chest::new);
        GameObject.registerType(ObjectType.TREE, Tree::new);
    }

    public static void init() {}
}
