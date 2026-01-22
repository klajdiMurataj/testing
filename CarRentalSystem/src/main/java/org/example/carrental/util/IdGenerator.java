package org.example.carrental.util;

import java.util.UUID;

/**
 * Utility class for generating unique IDs
 */
public class IdGenerator {

    /**
     * Generates a unique ID using UUID
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generates a unique ID with a prefix
     * @param prefix The prefix to add (e.g., "USER", "CAR", "BOOKING")
     */
    public static String generateId(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}