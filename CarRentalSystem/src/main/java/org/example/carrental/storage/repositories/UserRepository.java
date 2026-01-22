package org.example.carrental.storage.repositories;

import org.example.carrental.model.users.Account;
import org.example.carrental.storage.BinaryDataStore;
import org.example.carrental.storage.DataStore;
import org.example.carrental.storage.StoragePaths;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing user accounts
 */
public class UserRepository {

    private final DataStore<Account> dataStore;
    private List<Account> users;

    public UserRepository() {
        this.dataStore = new BinaryDataStore<>(StoragePaths.USERS_FILE);
        this.users = new ArrayList<>();
        load();
    }

    /**
     * Loads all users from storage
     */
    public void load() {
        this.users = dataStore.load();
    }

    /**
     * Saves all users to storage
     */
    public void save() {
        dataStore.save(users);
    }

    /**
     * Adds a new user
     */
    public void add(Account user) {
        users.add(user);
        save();
    }

    /**
     * Removes a user by ID
     */
    public boolean remove(String userId) {
        boolean removed = users.removeIf(user -> user.getId().equals(userId));
        if (removed) {
            save();
        }
        return removed;
    }

    /**
     * Finds a user by ID
     */
    public Optional<Account> findById(String userId) {
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst();
    }

    /**
     * Finds a user by username
     */
    public Optional<Account> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getCredential().getUsername().equals(username))
                .findFirst();
    }

    /**
     * Finds a user by email
     */
    public Optional<Account> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    /**
     * Gets all users
     */
    public List<Account> findAll() {
        return new ArrayList<>(users);
    }

    /**
     * Checks if a username already exists
     */
    public boolean existsByUsername(String username) {
        return users.stream()
                .anyMatch(user -> user.getCredential().getUsername().equals(username));
    }

    /**
     * Checks if an email already exists
     */
    public boolean existsByEmail(String email) {
        return users.stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    /**
     * Gets the total number of users
     */
    public int count() {
        return users.size();
    }

    /**
     * Clears all users
     */
    public void clear() {
        users.clear();
        dataStore.clear();
    }
}