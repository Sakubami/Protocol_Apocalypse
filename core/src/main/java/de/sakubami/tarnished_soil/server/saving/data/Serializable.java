package de.sakubami.tarnished_soil.server.saving.data;

public interface Serializable <T> {
    T toData();
    void readData(T data);
}
