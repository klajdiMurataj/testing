package org.example.carrental.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.carrental.view.components.TopBar;

/**
 * Login view
 */
public class LoginView extends BorderPane {

    private TopBar topBar;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button backButton;

    public LoginView() {
        setupUI();
    }

    private void setupUI() {
        setStyle("-fx-background-color: #2e2e2e;");

        // Top bar
        topBar = new TopBar();
        setTop(topBar);

        // Login form
        VBox formBox = new VBox(20);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(50));
        formBox.setMaxWidth(400);
        formBox.setStyle("-fx-background-color: #3a3a3a; -fx-background-radius: 10; " +
                "-fx-border-color: #ff7c0a; -fx-border-width: 2; -fx-border-radius: 10;");

        // Title
        Label titleLabel = new Label("Login");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        // Username field
        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        usernameLabel.setStyle("-fx-text-fill: white;");

        usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefHeight(40);
        usernameField.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                "-fx-prompt-text-fill: gray; -fx-font-size: 14; " +
                "-fx-background-radius: 5;");

        // Password field
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        passwordLabel.setStyle("-fx-text-fill: white;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefHeight(40);
        passwordField.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                "-fx-prompt-text-fill: gray; -fx-font-size: 14; " +
                "-fx-background-radius: 5;");

        // Login button
        loginButton = new Button("Login");
        loginButton.setPrefWidth(200);
        loginButton.setPrefHeight(45);
        loginButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 16; " +
                "-fx-background-radius: 5; -fx-cursor: hand;");

        loginButton.setOnMouseEntered(e ->
                loginButton.setStyle("-fx-background-color: #e66d00; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 16; " +
                        "-fx-background-radius: 5; -fx-cursor: hand;"));

        loginButton.setOnMouseExited(e ->
                loginButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 16; " +
                        "-fx-background-radius: 5; -fx-cursor: hand;"));

        // Back button
        backButton = new Button("Back to Home");
        backButton.setPrefWidth(200);
        backButton.setPrefHeight(40);
        backButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");

        backButton.setOnMouseEntered(e ->
                backButton.setStyle("-fx-background-color: #666666; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;"));

        backButton.setOnMouseExited(e ->
                backButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;"));

        formBox.getChildren().addAll(
                titleLabel,
                usernameLabel,
                usernameField,
                passwordLabel,
                passwordField,
                loginButton,
                backButton
        );

        // Center the form
        VBox centerContainer = new VBox(formBox);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setStyle("-fx-background-color: #2e2e2e;");

        setCenter(centerContainer);
    }

    public TopBar getTopBar() {
        return topBar;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}