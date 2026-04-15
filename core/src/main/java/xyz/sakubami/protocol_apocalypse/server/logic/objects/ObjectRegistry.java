package xyz.sakubami.protocol_apocalypse.server.logic.objects;

import xyz.sakubami.protocol_apocalypse.server.logic.objects.container.interfaces.Chest;
import xyz.sakubami.protocol_apocalypse.server.logic.objects.normal.Tree;
import xyz.sakubami.protocol_apocalypse.shared.type.ObjectType;

public class ObjectRegistry {
    static {
        GameObject.registerType(ObjectType.CHEST, Chest::new);
        GameObject.registerType(ObjectType.TREE, Tree::new);
    }

    public static void init() {}
}
