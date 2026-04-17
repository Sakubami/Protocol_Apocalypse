package de.sakubami.tarnished_soil.server.logic.objects;

import de.sakubami.tarnished_soil.server.logic.objects.container.interfaces.Chest;
import de.sakubami.tarnished_soil.server.logic.objects.normal.Tree;
import de.sakubami.tarnished_soil.server.logic.objects.normal.Wall;
import de.sakubami.tarnished_soil.shared.type.ObjectType;

public class ObjectRegistry {
    static {
        GameObject.registerType(ObjectType.CHEST, Chest::new);
        GameObject.registerType(ObjectType.TREE, Tree::new);
        GameObject.registerType(ObjectType.WALL, Wall::new);
    }

    public static void init() {}
}
