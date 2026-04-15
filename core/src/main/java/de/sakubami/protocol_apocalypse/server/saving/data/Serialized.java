package de.sakubami.protocol_apocalypse.server.saving.data;

public interface Serialized<T> {
    T createObject();
    String getPath();
}
