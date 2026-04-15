package de.sakubami.protocol_apocalypse.shared.utils;

public interface PositionalVector<T> {
    T subtract(T subtractor);
    T add(T adder);
    String toString();
    float length();
    T normalized();
    T mul(float scalar);
}
