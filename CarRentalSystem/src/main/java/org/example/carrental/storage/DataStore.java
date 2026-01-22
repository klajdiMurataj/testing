package org.example.carrental.storage;

import java.util.List;

/**
 * Interface for data persistence operations
 * @param <T> The type of entity to store
 */
public interface DataStore<T> {

    /**
     * Saves a list of entities to storage
     */
    void save(List<T> entities);

    /**
     * Loads all entities from storage
     */
    List<T> load();

    /**
     * Checks if storage file exists
     */
    boolean exists();

    /**
     * Clears all data from storage
     */
    void clear();
}