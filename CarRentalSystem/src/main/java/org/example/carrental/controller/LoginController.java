package org.example.carrental.controller;

import org.example.carrental.AppNavigator;
import org.example.carrental.model.users.Account;
import org.example.carrental.service.AuthService;
import org.example.carrental.service.CarService;
import org.example.carrental.view.LoginView;
import org.example.carrental.view.MainView;
import org.example.carrental.util.Dialogs;

import java.util.Optional;

/**
 * Controller for the login view
 */
public class LoginController {

    private final LoginView view;
    private final AuthService authService;
    private final CarService carService;

    public LoginController(LoginView view, AuthService authService, CarService carService) {
        this.view = view;
        this.authService = authService;
        this.carService = carService;

        setupEventHandlers();
        updateNavigation();
    }

    private void setupEventHandlers() {
        // Login button
        view.getLoginButton().setOnAction(e -> handleLogin());

        // Enter key on password field
        view.getPasswordField().setOnAction(e -> handleLogin());

        // Back button
        view.getBackButton().setOnAction(e -> goToHome());

        // Top bar navigation
        view.getTopBar().getHomeButton().setOnAction(e -> goToHome());
    }

    private void handleLogin() {
        String username = view.getUsernameField().getText().trim();
        String password = view.getPasswordField().getText();

        if (username.isEmpty() || password.isEmpty()) {
            Dialogs.showValidationError("Please enter both username and password.");
            return;
        }

        try {
            Optional<Account> userOpt = authService.login(username, password);

            if (userOpt.isPresent()) {
                Account user = userOpt.get();
                Dialogs.showSuccess("Welcome back, " + user.getFirstName() + "!");

                // Clear password field
                view.getPasswordField().clear();

                // Navigate to home
                goToHome();
            } else {
                Dialogs.showError("Login Failed", "Invalid username or password.");
                view.getPasswordField().clear();
            }
        } catch (Exception e) {
            Dialogs.showError("Login Error", "An error occurred during login: " + e.getMessage());
        }
    }

    private void goToHome() {
        MainView mainView = new MainView();
        new MainController(mainView, carService, authService);
        AppNavigator.getInstance().navigateTo(mainView);
    }

    private void updateNavigation() {
        view.getTopBar().updateButtonVisibility(false, false, false, false);
    }
}