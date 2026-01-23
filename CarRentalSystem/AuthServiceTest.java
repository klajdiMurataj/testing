package org.example.carrental.service; 

import static org.junit.jupiter.api.Assertions.*;

import org.example.carrental.storage.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for AuthService - method registerEndUser
 * Focus: input validation and duplicate checks
 */
class AuthServiceTest {

    AuthService authService;
    UserRepository repo;

    @BeforeEach
    void setup() {
        // Fresh repository before each test
        repo = new UserRepository();
        authService = new AuthService(repo);
    }

    @Test
    void testValidUser() {
        // Reason: Valid input (happy path)
        boolean result = authService.registerEndUser(
                "john", "Strong123!",
                "John", "Doe",
                "john@mail.com",
                "049123456",
                "DL1", "Prishtine"
        );

        assertTrue(result);
    }

    @Test
    void testInvalidUsername() {
        // Reason: Username is empty
        assertThrows(IllegalArgumentException.class, () ->
                authService.registerEndUser(
                        "", "Strong123!",
                        "John", "Doe",
                        "john@mail.com",
                        "049123456",
                        "DL1", "Prishtine"
                )
        );
    }

    @Test
    void testWeakPassword() {
        // Reason: Password does not meet strength requirements
        assertThrows(IllegalArgumentException.class, () ->
                authService.registerEndUser(
                        "john2", "123",
                        "John", "Doe",
                        "john2@mail.com",
                        "049123457",
                        "DL2", "Prishtine"
                )
        );
    }

    @Test
    void testInvalidEmail() {
        // Reason: Email format is invalid
        assertThrows(IllegalArgumentException.class, () ->
                authService.registerEndUser(
                        "john3", "Strong123!",
                        "John", "Doe",
                        "johnmail.com",
                        "049123458",
                        "DL3", "Prishtine"
                )
        );
    }

    @Test
    void testInvalidPhone() {
        // Reason: Phone number format is invalid
        assertThrows(IllegalArgumentException.class, () ->
                authService.registerEndUser(
                        "john4", "Strong123!",
                        "John", "Doe",
                        "john4@mail.com",
                        "123",
                        "DL4", "Prishtine"
                )
        );
    }

    @Test
    void testUsernameExists() {
        // Reason: Username must be unique
        authService.registerEndUser(
                "ana", "Strong123!",
                "Ana", "Test",
                "ana@mail.com",
                "049999999",
                "DL5", "Prishtine"
        );

        assertThrows(IllegalArgumentException.class, () ->
                authService.registerEndUser(
                        "ana", "Strong123!",
                        "Ana", "Test",
                        "ana2@mail.com",
                        "048888888",
                        "DL6", "Prishtine"
                )
        );
    }

    @Test
    void testEmailExists() {
        // Reason: Email must be unique
        authService.registerEndUser(
                "mark", "Strong123!",
                "Mark", "Test",
                "mark@mail.com",
                "047777777",
                "DL7", "Prishtine"
        );

        assertThrows(IllegalArgumentException.class, () ->
                authService.registerEndUser(
                        "mark2", "Strong123!",
                        "Mark", "Test",
                        "mark@mail.com",
                        "046666666",
                        "DL8", "Prishtine"
                )
        );
    }
}
