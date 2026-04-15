package de.sakubami.protocol_apocalypse.server.saving;

import com.google.gson.Gson;
import de.sakubami.protocol_apocalypse.server.saving.data.Serialized;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Saviour {
    private static final Gson gson = new Gson();
    private static final Queue<Serialized<?>> saveQueue = new LinkedList<>();
    private final String path;

    public Saviour(String path) {
        this.path = path;
    }

    public static <T, S extends Serialized<T>> T loadObject(String path, Class<S> dataClass) {
        try(Reader reader = new FileReader(path)) {
            S object = gson.fromJson(reader, dataClass);
            return object.createObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends Serialized<?>> T loadData(String path, Class<T> dataClass) {
        try(Reader reader = new FileReader(path)) {
            return gson.fromJson(reader, dataClass);
        } catch (IOException ignored) {}
        return null;
    }

    public static void saveWorldData(Serialized<?> data) {
        saveQueue.add(data);
    }

    public static void saveData(String path, Serialized<?> data) {
        try(Writer writer = new FileWriter(path)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        while (!saveQueue.isEmpty()) {
            Serialized<?> data = saveQueue.poll();
            try(Writer writer = new FileWriter(path + "/" + data.getPath())) {
                gson.toJson(data, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
