package org.example.carrental.storage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Binary data store implementation using Java serialization
 * @param <T> The type of entity to store (must be Serializable)
 */
public class BinaryDataStore<T> implements DataStore<T> {

    private final Path filePath;

    public BinaryDataStore(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(List<T> entities) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath.toFile()))) {
            oos.writeObject(entities);
        } catch (IOException e) {
            System.err.println("Error saving data to " + filePath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> load() {
        if (!exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath.toFile()))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data from " + filePath + ": " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean exists() {
        return Files.exists(filePath);
    }

    @Override
    public void clear() {
        try {
            if (exists()) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            System.err.println("Error clearing data from " + filePath + ": " + e.getMessage());
        }
    }
}