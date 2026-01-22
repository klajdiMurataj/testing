package org.example.carrental.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.carrental.AppNavigator;
import org.example.carrental.model.enums.Role;
import org.example.carrental.model.users.Account;
import org.example.carrental.service.AuthService;
import org.example.carrental.service.CarService;
import org.example.carrental.service.Session;
import org.example.carrental.storage.repositories.CarRepository;
import org.example.carrental.storage.repositories.UserRepository;
import org.example.carrental.util.Dialogs;
import org.example.carrental.util.Validators;
import org.example.carrental.view.AdminView;
import org.example.carrental.view.MainView;

import java.util.List;

/**
 * Controller for the admin view
 */
public class AdminController {

    private final AdminView view;
    private final AuthService authService;
    private final UserRepository userRepository;

    public AdminController(AdminView view, AuthService authService) {
        this.view = view;
        this.authService = authService;
        this.userRepository = new UserRepository();

        setupEventHandlers();
        loadUsers();
        updateNavigation();
    }

    private void setupEventHandlers() {
        // Create worker button
        view.getCreateWorkerButton().setOnAction(e -> handleCreateWorker());

        // Top bar navigation
        view.getTopBar().getHomeButton().setOnAction(e -> goToHome());
        view.getTopBar().getLogoutButton().setOnAction(e -> logout());
    }

    private void handleCreateWorker() {
        // Get all field values
        String username = view.getWorkerUsernameField().getText().trim();
        String password = view.getWorkerPasswordField().getText();
        String firstName = view.getWorkerFirstNameField().getText().trim();
        String lastName = view.getWorkerLastNameField().getText().trim();
        String email = view.getWorkerEmailField().getText().trim();
        String phone = view.getWorkerPhoneField().getText().trim();
        String employeeId = view.getEmployeeIdField().getText().trim();
        String department = view.getDepartmentField().getText().trim();

        // Validate inputs
        if (!validateWorkerInputs(username, password, firstName, lastName, email, phone,
                employeeId, department)) {
            return;
        }

        try {
            authService.createWorker(username, password, firstName, lastName,
                    email, phone, employeeId, department);

            Dialogs.showSuccess("Worker account created successfully!");
            clearCreateWorkerForm();
            loadUsers();

        } catch (IllegalArgumentException e) {
            Dialogs.showValidationError(e.getMessage());
        } catch (Exception e) {
            Dialogs.showError("Error", "An error occurred: " + e.getMessage());
        }
    }

    private boolean validateWorkerInputs(String username, String password, String firstName,
                                         String lastName, String email, String phone,
                                         String employeeId, String department) {
        if (!Validators.isNotEmpty(username)) {
            Dialogs.showValidationError("Username is required.");
            return false;
        }

        if (!Validators.isValidPassword(password)) {
            Dialogs.showValidationError("Password must be at least 6 characters long.");
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

        if (!Validators.isNotEmpty(employeeId)) {
            Dialogs.showValidationError("Employee ID is required.");
            return false;
        }

        if (!Validators.isNotEmpty(department)) {
            Dialogs.showValidationError("Department is required.");
            return false;
        }

        return true;
    }

    private void clearCreateWorkerForm() {
        view.getWorkerUsernameField().clear();
        view.getWorkerPasswordField().clear();
        view.getWorkerFirstNameField().clear();
        view.getWorkerLastNameField().clear();
        view.getWorkerEmailField().clear();
        view.getWorkerPhoneField().clear();
        view.getEmployeeIdField().clear();
        view.getDepartmentField().clear();
    }

    private void loadUsers() {
        view.getUserListContainer().getChildren().clear();
        List<Account> users = userRepository.findAll();

        for (Account user : users) {
            HBox userRow = createUserRow(user);
            view.getUserListContainer().getChildren().add(userRow);
        }
    }

    private HBox createUserRow(Account user) {
        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(15));
        row.setStyle("-fx-background-color: #3a3a3a; -fx-background-radius: 8; " +
                "-fx-border-color: #555555; -fx-border-width: 1; -fx-border-radius: 8;");

        // User info
        VBox infoBox = new VBox(5);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        Label nameLabel = new Label(user.getFullName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nameLabel.setStyle("-fx-text-fill: #f2ed57;");

        Label emailLabel = new Label(user.getEmail());
        emailLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        emailLabel.setStyle("-fx-text-fill: white;");

        Label usernameLabel = new Label("Username: " + user.getCredential().getUsername());
        usernameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        usernameLabel.setStyle("-fx-text-fill: #cccccc;");

        infoBox.getChildren().addAll(nameLabel, emailLabel, usernameLabel);

        // Role label
        VBox roleBox = new VBox(5);
        roleBox.setAlignment(Pos.CENTER);
        roleBox.setPrefWidth(120);

        Label roleLabel = new Label(user.getRole().toString());
        roleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        String roleColor;
        switch (user.getRole()) {
            case ADMIN:
                roleColor = "#f44336";
                break;
            case WORKER:
                roleColor = "#2196F3";
                break;
            default:
                roleColor = "#4CAF50";
                break;
        }
        roleLabel.setStyle("-fx-text-fill: " + roleColor + ";");

        roleBox.getChildren().add(roleLabel);

        // Delete button (only for workers)
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPrefWidth(100);

        if (user.getRole() == Role.WORKER) {
            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; " +
                    "-fx-font-weight: bold; -fx-background-radius: 5; " +
                    "-fx-padding: 8 16 8 16; -fx-cursor: hand;");

            deleteButton.setOnMouseEntered(e ->
                    deleteButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-background-radius: 5; " +
                            "-fx-padding: 8 16 8 16; -fx-cursor: hand;"));

            deleteButton.setOnMouseExited(e ->
                    deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-background-radius: 5; " +
                            "-fx-padding: 8 16 8 16; -fx-cursor: hand;"));

            deleteButton.setOnAction(e -> handleDeleteUser(user));

            buttonBox.getChildren().add(deleteButton);
        }

        row.getChildren().addAll(infoBox, roleBox, buttonBox);

        return row;
    }

    private void handleDeleteUser(Account user) {
        boolean confirmed = Dialogs.showConfirmation(
                "Delete User",
                "Are you sure you want to delete user " + user.getFullName() + "?"
        );

        if (confirmed) {
            try {
                authService.deleteUser(user.getId());
                Dialogs.showSuccess("User deleted successfully!");
                loadUsers();
            } catch (IllegalArgumentException e) {
                Dialogs.showError("Error", e.getMessage());
            } catch (Exception e) {
                Dialogs.showError("Error", "Failed to delete user: " + e.getMessage());
            }
        }
    }

    private void logout() {
        authService.logout();
        Dialogs.showInfo("Logged Out", "You have been logged out successfully.");
        goToHome();
    }

    private void goToHome() {
        CarRepository carRepository = new CarRepository();
        CarService carService = new CarService(carRepository);
        MainView mainView = new MainView();
        new MainController(mainView, carService, authService);
        AppNavigator.getInstance().navigateTo(mainView);
    }

    private void updateNavigation() {
        Session session = Session.getInstance();
        view.getTopBar().updateButtonVisibility(
                session.isLoggedIn(),
                session.isEndUser(),
                session.isWorker(),
                session.isAdmin()
        );
    }
}