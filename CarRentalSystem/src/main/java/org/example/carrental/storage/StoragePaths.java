package org.example.carrental.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Centralized storage paths for all data files
 */
public class StoragePaths {

    // Base directory for all data files
    public static final Path DATA_DIR = Paths.get("data");

    // Binary data files (.dat)
    public static final Path USERS_FILE = DATA_DIR.resolve("users.dat");
    public static final Path CARS_FILE = DATA_DIR.resolve("cars.dat");
    public static final Path BOOKINGS_FILE = DATA_DIR.resolve("bookings.dat");

    // Invoice directory
    public static final Path INVOICES_DIR = DATA_DIR.resolve("invoices");

    // Car photos directory
    public static final Path PHOTOS_DIR = DATA_DIR.resolve("photos");

    /**
     * Ensures all required directories exist
     */
    public static void ensureDirectoriesExist() {
        try {
            java.nio.file.Files.createDirectories(DATA_DIR);
            java.nio.file.Files.createDirectories(INVOICES_DIR);
            java.nio.file.Files.createDirectories(PHOTOS_DIR);
        } catch (Exception e) {
            System.err.println("Error creating data directories: " + e.getMessage());
        }
    }
}