package org.example.carrental.controller;

import org.example.carrental.AppNavigator;
import org.example.carrental.service.AuthService;
import org.example.carrental.service.CarService;
import org.example.carrental.storage.repositories.CarRepository;
import org.example.carrental.view.MainView;
import org.example.carrental.view.SignupView;
import org.example.carrental.util.Dialogs;
import org.example.carrental.util.Validators;

/**
 * Controller for the signup view
 */
public class SignupController {

    private final SignupView view;
    private final AuthService authService;

    public SignupController(SignupView view, AuthService authService) {
        this.view = view;
        this.authService = authService;

        setupEventHandlers();
        updateNavigation();
    }

    private void setupEventHandlers() {
        // Signup button
        view.getSignupButton().setOnAction(e -> handleSignup());

        // Back button
        view.getBackButton().setOnAction(e -> goToHome());

        // Top bar navigation
        view.getTopBar().getHomeButton().setOnAction(e -> goToHome());
    }

    private void handleSignup() {
        // Get all field values
        String username = view.getUsernameField().getText().trim();
        String password = view.getPasswordField().getText();
        String confirmPassword = view.getConfirmPasswordField().getText();
        String firstName = view.getFirstNameField().getText().trim();
        String lastName = view.getLastNameField().getText().trim();
        String email = view.getEmailField().getText().trim();
        String phone = view.getPhoneField().getText().trim();
        String license = view.getLicenseField().getText().trim();
        String address = view.getAddressField().getText().trim();

        // Validate all fields
        if (!validateInputs(username, password, confirmPassword, firstName, lastName,
                email, phone, license, address)) {
            return;
        }

        try {
            authService.registerEndUser(username, password, firstName, lastName,
                    email, phone, license, address);

            Dialogs.showSuccess("Account created successfully! You can now log in.");
            goToHome();

        } catch (IllegalArgumentException e) {
            Dialogs.showValidationError(e.getMessage());
        } catch (Exception e) {
            Dialogs.showError("Signup Error", "An error occurred: " + e.getMessage());
        }
    }

    private boolean validateInputs(String username, String password, String confirmPassword,
                                   String firstName, String lastName, String email,
                                   String phone, String license, String address) {
        // Check for empty fields
        if (!Validators.isNotEmpty(username)) {
            Dialogs.showValidationError("Username is required.");
            return false;
        }

        if (!Validators.isValidPassword(password)) {
            Dialogs.showValidationError("Password must be at least 6 characters long.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Dialogs.showValidationError("Passwords do not match.");
            return false;
        }

        if (!Validators.isNotEmpty(firstName) || !Validators.isNotEmpty(lastName)) {
            Dialogs.showValidationError("First name and last name are required.");
            return false;
        }

        if (!Validators.isValidEmail(email)) {
            Dialogs.showValidationError("Please enter a valid email address.");
            return false;
        }

        if (!Validators.isValidPhone(phone)) {
            Dialogs.showValidationError("Please enter a valid phone number (10-15 digits).");
            return false;
        }

        if (!Validators.isNotEmpty(license)) {
            Dialogs.showValidationError("Driver license number is required.");
            return false;
        }

        if (!Validators.isNotEmpty(address)) {
            Dialogs.showValidationError("Address is required.");
            return false;
        }

        return true;
    }

    private void goToHome() {
        CarRepository carRepository = new CarRepository();
        CarService carService = new CarService(carRepository);
        MainView mainView = new MainView();
        new MainController(mainView, carService, authService);
        AppNavigator.getInstance().navigateTo(mainView);
    }

    private void updateNavigation() {
        view.getTopBar().updateButtonVisibility(false, false, false, false);
    }
}