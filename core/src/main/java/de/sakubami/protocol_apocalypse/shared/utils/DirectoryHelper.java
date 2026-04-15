package de.sakubami.protocol_apocalypse.shared.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DirectoryHelper {
    public static void init() {
        //create base directories

        List<Path> paths = new ArrayList<>();
        paths.add(Paths.get("worlds"));
        paths.add(Paths.get("config"));

        for (Path path : paths) {
            try {
                if (!Files.exists(path))
                    Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * creates folderstructure inside root directory IF ROOT DIRECTORY IS PRESENT
     * @param v folder structure: "ROOT/DIR1/DIR2/TARGET"
     */
    public static void createDirectory(String v) {
        Path path = Paths.get(v);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> listFolders(String path) {
        File directory = new File(path);
        List<String> folders = new ArrayList<>();

        // Ensure the directory exists and is actually a directory
        if (!directory.exists() || !directory.isDirectory()) {
            return folders;
        }

        File[] files = directory.listFiles();
        if (files == null) return folders;

        for (File file : files) {
            if (file.isDirectory()) {
                folders.add(file.getName());
            }
        }

        return folders;
    }
}
