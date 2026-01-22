package org.example.carrental.service;

import org.example.carrental.model.users.Account;

/**
 * Manages the current user session
 */
public class Session {

    private static Session instance;
    private Account currentUser;

    private Session() {
    }

    /**
     * Gets the singleton instance
     */
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * Sets the current logged-in user
     */
    public void setCurrentUser(Account user) {
        this.currentUser = user;
    }

    /**
     * Gets the current logged-in user
     */
    public Account getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks if a user is logged in
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Logs out the current user
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * Gets the current user's ID
     */
    public String getCurrentUserId() {
        return currentUser != null ? currentUser.getId() : null;
    }

    /**
     * Gets the current user's full name
     */
    public String getCurrentUserFullName() {
        return currentUser != null ? currentUser.getFullName() : null;
    }

    /**
     * Checks if the current user is an admin
     */
    public boolean isAdmin() {
        return currentUser != null &&
                currentUser.getRole() == org.example.carrental.model.enums.Role.ADMIN;
    }

    /**
     * Checks if the current user is a worker
     */
    public boolean isWorker() {
        return currentUser != null &&
                currentUser.getRole() == org.example.carrental.model.enums.Role.WORKER;
    }

    /**
     * Checks if the current user is an end user
     */
    public boolean isEndUser() {
        return currentUser != null &&
                currentUser.getRole() == org.example.carrental.model.enums.Role.END_USER;
    }
}