package org.example.carrental.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Utility class for password hashing and verification
 */
public class HashUtil {

    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    /**
     * Generates a random salt
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Hashes a password with the given salt
     */
    public static byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Clear password from memory
            Arrays.fill(password.toCharArray(), '\0');

            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Verifies if a password matches the stored hash
     */
    public static boolean verifyPassword(String password, byte[] storedHash, byte[] salt) {
        byte[] hashedPassword = hashPassword(password, salt);
        boolean matches = Arrays.equals(hashedPassword, storedHash);

        // Clear sensitive data from memory
        Arrays.fill(hashedPassword, (byte) 0);

        return matches;
    }
}