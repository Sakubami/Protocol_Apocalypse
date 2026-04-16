package de.sakubami.tarnished_soil.server.saving.data;

public interface Serialized<T> {
    T createObject();
    String getPath();
}
