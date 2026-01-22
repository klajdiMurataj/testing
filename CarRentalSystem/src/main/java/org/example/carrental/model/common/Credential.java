package org.example.carrental.model.common;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Stores credentials securely with salt and hash
 */
public class Credential implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private byte[] passwordHash;
    private byte[] salt;

    public Credential(String username, byte[] passwordHash, byte[] salt) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    /**
     * Clears sensitive data from memory
     */
    public void clear() {
        if (passwordHash != null) {
            Arrays.fill(passwordHash, (byte) 0);
        }
        if (salt != null) {
            Arrays.fill(salt, (byte) 0);
        }
    }
}