



 package org.example.usermanagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.carrental.model.users.EndUser;
import org.example.carrental.model.users.Worker;
import org.example.carrental.service.AuthService;
import org.example.carrental.storage.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

    class AuthServiceTest {
        @Mock
        private UserRepository userRepository;

        private AuthService workerService;

        @BeforeEach
        void setup() {
            MockitoAnnotations.openMocks(this);
            workerService = new AuthService(userRepository);
        }

        @Test
        void createWorker_emptyUsername_throwsException() {
            Exception ex = assertThrows(IllegalArgumentException.class, () ->
                    workerService.createWorker("", "ValidPass1!", "John", "Doe",
                            "john@example.com", "1234567890", "E123", "IT")
            );
            assertEquals("Invalid username or password", ex.getMessage());
        }

        @Test
        void createWorker_invalidPassword_throwsException() {
            Exception ex = assertThrows(IllegalArgumentException.class, () ->
                    workerService.createWorker("john", "123", "John", "Doe",
                            "john@example.com", "1234567890", "E123", "IT")
            );
            assertEquals("Invalid username or password", ex.getMessage());
        }

        @Test
        void createWorker_invalidEmail_throwsException() {
            Exception ex = assertThrows(IllegalArgumentException.class, () ->
                    workerService.createWorker("john", "ValidPass1!", "John", "Doe",
                            "johnexample.com", "1234567890", "E123", "IT")
            );
            assertEquals("Invalid email format", ex.getMessage());
        }

        @Test
        void createWorker_existingUsername_throwsException() {
            when(userRepository.existsByUsername("john")).thenReturn(true);

            Exception ex = assertThrows(IllegalArgumentException.class, () ->
                    workerService.createWorker("john", "ValidPass1!", "John", "Doe",
                            "john@example.com", "1234567890", "E123", "IT")
            );
            assertEquals("Username already exists", ex.getMessage());
        }

        @Test
        void createWorker_existingEmail_throwsException() {
            when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

            Exception ex = assertThrows(IllegalArgumentException.class, () ->
                    workerService.createWorker("john", "ValidPass1!", "John", "Doe",
                            "john@example.com", "1234567890", "E123", "IT")
            );
            assertEquals("Email already registered", ex.getMessage());
        }

        @Test
        void createWorker_validWorker_returnsTrue() {
            // Arrange: username and email do not exist
            when(userRepository.existsByUsername("john")).thenReturn(false);
            when(userRepository.existsByEmail("john@example.com")).thenReturn(false);

            // Act
            boolean result = workerService.createWorker(
                    "john", "ValidPass1!", "John", "Doe",
                    "john@example.com", "1234567890", "E123", "IT"
            );

            // Assert
            assertTrue(result);

            // Verify userRepository.add() is called once
            verify(userRepository, times(1)).add(any(Worker.class));
        }
    }