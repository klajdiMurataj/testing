//Eden Pajo
package org.example.carrental.service;

import org.example.carrental.model.common.Credential;
import org.example.carrental.model.users.Account;
import org.example.carrental.model.users.EndUser;
import org.example.carrental.storage.repositories.UserRepository;
import org.example.carrental.util.HashUtil;
import org.example.carrental.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    private UserRepository userRepository;
    private AuthService authService;

    @BeforeEach
    void setUp() throws Exception {
        Files.deleteIfExists(Path.of("data", "users.dat"));

        userRepository = new UserRepository();
        userRepository.clear(); 

        authService = new AuthService(userRepository);
        authService.logout(); 
    }

    @Test
    void login_userNotFound_returnsEmptyOptional_andDoesNotSetSession() {
        Optional<Account> result = authService.login("john", "pass123");

        assertTrue(result.isEmpty());
        assertNull(authService.getCurrentUser());
    }

    @Test
    void login_validCredentials_returnsAccount_andSetsSession() {
        String username = "john";
        String password = "pass123";

        Credential cred = createCredential(username, password);

        EndUser user = new EndUser(
                IdGenerator.generateId("USER"),
                "John",
                "Doe",
                "john@example.com",
                "123456789",
                cred,
                "DL-12345",
                "Tirane"
        );

        userRepository.add(user);

        // Act
        Optional<Account> result = authService.login(username, password);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getCredential().getUsername());

        assertNotNull(authService.getCurrentUser());
        assertEquals(username, authService.getCurrentUser().getCredential().getUsername());
    }

    private Credential createCredential(String username, String password) {
        byte[] salt = HashUtil.generateSalt();
        byte[] hash = HashUtil.hashPassword(password, salt);
        return new Credential(username, hash, salt);
    }
}

