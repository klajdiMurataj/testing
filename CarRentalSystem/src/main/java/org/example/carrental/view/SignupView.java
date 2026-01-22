package org.example.carrental.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.carrental.view.components.TopBar;

/**
 * Signup/Registration view for end users
 */
public class SignupView extends BorderPane {

    private TopBar topBar;
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField phoneField;
    private TextField licenseField;
    private TextField addressField;
    private Button signupButton;
    private Button backButton;

    public SignupView() {
        setupUI();
    }

    private void setupUI() {
        setStyle("-fx-background-color: #2e2e2e;");

        // Top bar
        topBar = new TopBar();
        setTop(topBar);

        // Signup form
        VBox formBox = new VBox(20);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(40));
        formBox.setMaxWidth(600);
        formBox.setStyle("-fx-background-color: #3a3a3a; -fx-background-radius: 10; " +
                "-fx-border-color: #ff7c0a; -fx-border-width: 2; -fx-border-radius: 10;");

        // Title
        Label titleLabel = new Label("Create Account");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        // Form grid
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        // Username
        Label usernameLabel = createLabel("Username:");
        usernameField = createTextField("Enter username");
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);

        // Password
        Label passwordLabel = createLabel("Password:");
        passwordField = createPasswordField("Enter password (min 6 characters)");
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        // Confirm Password
        Label confirmPasswordLabel = createLabel("Confirm Password:");
        confirmPasswordField = createPasswordField("Re-enter password");
        grid.add(confirmPasswordLabel, 0, 2);
        grid.add(confirmPasswordField, 1, 2);

        // First Name
        Label firstNameLabel = createLabel("First Name:");
        firstNameField = createTextField("Enter first name");
        grid.add(firstNameLabel, 0, 3);
        grid.add(firstNameField, 1, 3);

        // Last Name
        Label lastNameLabel = createLabel("Last Name:");
        lastNameField = createTextField("Enter last name");
        grid.add(lastNameLabel, 0, 4);
        grid.add(lastNameField, 1, 4);

        // Email
        Label emailLabel = createLabel("Email:");
        emailField = createTextField("Enter email address");
        grid.add(emailLabel, 0, 5);
        grid.add(emailField, 1, 5);

        // Phone
        Label phoneLabel = createLabel("Phone:");
        phoneField = createTextField("Enter phone number");
        grid.add(phoneLabel, 0, 6);
        grid.add(phoneField, 1, 6);

        // Driver License
        Label licenseLabel = createLabel("Driver License:");
        licenseField = createTextField("Enter driver license number");
        grid.add(licenseLabel, 0, 7);
        grid.add(licenseField, 1, 7);

        // Address
        Label addressLabel = createLabel("Address:");
        addressField = createTextField("Enter address");
        grid.add(addressLabel, 0, 8);
        grid.add(addressField, 1, 8);

        // Signup button
        signupButton = new Button("Create Account");
        signupButton.setPrefWidth(250);
        signupButton.setPrefHeight(45);
        signupButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 16; " +
                "-fx-background-radius: 5; -fx-cursor: hand;");

        signupButton.setOnMouseEntered(e ->
                signupButton.setStyle("-fx-background-color: #e66d00; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 16; " +
                        "-fx-background-radius: 5; -fx-cursor: hand;"));

        signupButton.setOnMouseExited(e ->
                signupButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 16; " +
                        "-fx-background-radius: 5; -fx-cursor: hand;"));

        // Back button
        backButton = new Button("Back to Home");
        backButton.setPrefWidth(250);
        backButton.setPrefHeight(40);
        backButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");

        backButton.setOnMouseEntered(e ->
                backButton.setStyle("-fx-background-color: #666666; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;"));

        backButton.setOnMouseExited(e ->
                backButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;"));

        formBox.getChildren().addAll(titleLabel, grid, signupButton, backButton);

        // Scroll pane for form
        ScrollPane scrollPane = new ScrollPane(formBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #2e2e2e; -fx-background-color: #2e2e2e;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Center the form
        VBox centerContainer = new VBox(scrollPane);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setStyle("-fx-background-color: #2e2e2e;");
        centerContainer.setPadding(new Insets(20));

        setCenter(centerContainer);
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        label.setStyle("-fx-text-fill: white;");
        return label;
    }

    private TextField createTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setPrefWidth(300);
        field.setPrefHeight(35);
        field.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                "-fx-prompt-text-fill: gray; -fx-background-radius: 5;");
        return field;
    }

    private PasswordField createPasswordField(String prompt) {
        PasswordField field = new PasswordField();
        field.setPromptText(prompt);
        field.setPrefWidth(300);
        field.setPrefHeight(35);
        field.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                "-fx-prompt-text-fill: gray; -fx-background-radius: 5;");
        return field;
    }

    // Getters
    public TopBar getTopBar() {
        return topBar;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public PasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public TextField getLicenseField() {
        return licenseField;
    }

    public TextField getAddressField() {
        return addressField;
    }

    public Button getSignupButton() {
        return signupButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}