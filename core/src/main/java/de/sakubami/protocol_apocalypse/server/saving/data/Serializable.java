package de.sakubami.protocol_apocalypse.server.saving.data;

public interface Serializable <T> {
    T toData();
    void readData(T data);
}
