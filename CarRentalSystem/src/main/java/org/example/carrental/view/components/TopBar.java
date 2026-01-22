package org.example.carrental.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Top navigation bar component
 */
public class TopBar extends HBox {

    private TextField searchField;
    private Button homeButton;
    private Button loginButton;
    private Button signupButton;
    private Button myAccountButton;
    private Button workerButton;
    private Button adminButton;
    private Button logoutButton;

    public TopBar() {
        setupUI();
    }

    private void setupUI() {
        setStyle("-fx-background-color: #2e2e2e; -fx-padding: 15;");
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(15);

        // Title
        Label titleLabel = new Label("Car Rental System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        Label creditLabel = new Label("developed by Thomas Kroj, Eden Pajo");
        creditLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        creditLabel.setStyle("-fx-text-fill: #f2ed57;");

        // Search bar
        searchField = new TextField();
        searchField.setPromptText("Search by brand or model...");
        searchField.setPrefWidth(300);
        searchField.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                "-fx-prompt-text-fill: gray; -fx-background-radius: 5;");

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Navigation buttons
        homeButton = createNavButton("Home");
        loginButton = createNavButton("Login");
        signupButton = createNavButton("Signup");
        myAccountButton = createNavButton("My Account");
        workerButton = createNavButton("Worker");
        adminButton = createNavButton("Admin");
        logoutButton = createNavButton("Logout");

        // Add all components
        getChildren().addAll(
                titleLabel,
                creditLabel,
                searchField,
                spacer,
                homeButton,
                loginButton,
                signupButton,
                myAccountButton,
                workerButton,
                adminButton,
                logoutButton
        );
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 5; " +
                "-fx-padding: 8 15 8 15; -fx-cursor: hand;");

        button.setOnMouseEntered(e ->
                button.setStyle("-fx-background-color: #e66d00; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-background-radius: 5; " +
                        "-fx-padding: 8 15 8 15; -fx-cursor: hand;"));

        button.setOnMouseExited(e ->
                button.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-background-radius: 5; " +
                        "-fx-padding: 8 15 8 15; -fx-cursor: hand;"));

        return button;
    }

    // Getters for components
    public TextField getSearchField() {
        return searchField;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getSignupButton() {
        return signupButton;
    }

    public Button getMyAccountButton() {
        return myAccountButton;
    }

    public Button getWorkerButton() {
        return workerButton;
    }

    public Button getAdminButton() {
        return adminButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    /**
     * Updates button visibility based on user role
     */
    public void updateButtonVisibility(boolean isLoggedIn, boolean isEndUser,
                                       boolean isWorker, boolean isAdmin) {
        loginButton.setVisible(!isLoggedIn);
        loginButton.setManaged(!isLoggedIn);

        signupButton.setVisible(!isLoggedIn);
        signupButton.setManaged(!isLoggedIn);

        logoutButton.setVisible(isLoggedIn);
        logoutButton.setManaged(isLoggedIn);

        myAccountButton.setVisible(isEndUser);
        myAccountButton.setManaged(isEndUser);

        workerButton.setVisible(isWorker);
        workerButton.setManaged(isWorker);

        adminButton.setVisible(isAdmin);
        adminButton.setManaged(isAdmin);
    }
}