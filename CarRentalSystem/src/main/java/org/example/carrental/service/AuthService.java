package org.example.carrental.service;

import org.example.carrental.model.common.Credential;
import org.example.carrental.model.enums.Role;
import org.example.carrental.model.users.Account;
import org.example.carrental.model.users.EndUser;
import org.example.carrental.model.users.Worker;
import org.example.carrental.storage.repositories.UserRepository;
import org.example.carrental.util.HashUtil;
import org.example.carrental.util.IdGenerator;
import org.example.carrental.util.Validators;

import java.util.Optional;

/**
 * Service for authentication and user management
 */
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authenticates a user with username and password
     * @return The authenticated user or empty if authentication fails
     */
    public Optional<Account> login(String username, String password) {
        Optional<Account> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        Account user = userOpt.get();
        Credential credential = user.getCredential();

        boolean isValid = HashUtil.verifyPassword(
                password,
                credential.getPasswordHash(),
                credential.getSalt()
        );

        if (isValid) {
            Session.getInstance().setCurrentUser(user);
            return Optional.of(user);
        }

        return Optional.empty();
    }

    /**
     * Registers a new end user (customer)
     */
    public boolean registerEndUser(String username, String password, String firstName,
                                   String lastName, String email, String phoneNumber,
                                   String driverLicenseNumber, String address) {
        // Validate inputs
        if (!Validators.isNotEmpty(username) || !Validators.isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        if (!Validators.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (!Validators.isValidPhone(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        // Check if username or email already exists
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create credential
        byte[] salt = HashUtil.generateSalt();
        byte[] hash = HashUtil.hashPassword(password, salt);
        Credential credential = new Credential(username, hash, salt);

        // Create end user
        EndUser endUser = new EndUser(
                IdGenerator.generateId("USER"),
                firstName,
                lastName,
                email,
                phoneNumber,
                credential,
                driverLicenseNumber,
                address
        );

        userRepository.add(endUser);
        return true;
    }

    /**
     * Creates a new worker account (admin only)
     */
    public boolean createWorker(String username, String password, String firstName,
                                String lastName, String email, String phoneNumber,
                                String employeeId, String department) {
        // Validate inputs
        if (!Validators.isNotEmpty(username) || !Validators.isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        if (!Validators.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Check if username or email already exists
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create credential
        byte[] salt = HashUtil.generateSalt();
        byte[] hash = HashUtil.hashPassword(password, salt);
        Credential credential = new Credential(username, hash, salt);

        // Create worker
        Worker worker = new Worker(
                IdGenerator.generateId("WORKER"),
                firstName,
                lastName,
                email,
                phoneNumber,
                credential,
                employeeId,
                department
        );

        userRepository.add(worker);
        return true;
    }

    /**
     * Logs out the current user
     */
    public void logout() {
        Session.getInstance().logout();
    }

    /**
     * Checks if the current user is logged in
     */
    public boolean isLoggedIn() {
        return Session.getInstance().isLoggedIn();
    }

    /**
     * Gets the current logged-in user
     */
    public Account getCurrentUser() {
        return Session.getInstance().getCurrentUser();
    }

    /**
     * Deletes a user by ID (admin only)
     */
    public boolean deleteUser(String userId) {
        // Prevent deleting the current user
        if (Session.getInstance().getCurrentUserId().equals(userId)) {
            throw new IllegalArgumentException("Cannot delete currently logged-in user");
        }

        // Prevent deleting admin users
        Optional<Account> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent() && userOpt.get().getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("Cannot delete admin accounts");
        }

        return userRepository.remove(userId);
    }
}