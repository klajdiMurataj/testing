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
 * Admin view for managing workers and viewing all users
 */
public class AdminView extends BorderPane {

    private TopBar topBar;
    private TabPane tabPane;

    // Create Worker tab components
    private TextField workerUsernameField;
    private PasswordField workerPasswordField;
    private TextField workerFirstNameField;
    private TextField workerLastNameField;
    private TextField workerEmailField;
    private TextField workerPhoneField;
    private TextField employeeIdField;
    private TextField departmentField;
    private Button createWorkerButton;

    // View Users tab
    private VBox userListContainer;
    private ScrollPane userScrollPane;

    public AdminView() {
        setupUI();
    }

    private void setupUI() {
        setStyle("-fx-background-color: #2e2e2e;");

        // Top bar
        topBar = new TopBar();
        setTop(topBar);

        // Tab pane
        tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: #2e2e2e;");

        // Create Worker tab
        Tab createWorkerTab = new Tab("Create Worker");
        createWorkerTab.setClosable(false);
        createWorkerTab.setContent(createWorkerPane());

        // View Users tab
        Tab viewUsersTab = new Tab("View All Users");
        viewUsersTab.setClosable(false);
        viewUsersTab.setContent(createViewUsersPane());

        tabPane.getTabs().addAll(createWorkerTab, viewUsersTab);

        setCenter(tabPane);
    }

    private VBox createWorkerPane() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setAlignment(Pos.TOP_CENTER);
        container.setStyle("-fx-background-color: #2e2e2e;");

        // Title
        Label titleLabel = new Label("Create Worker Account");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        // Form
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);
        grid.setMaxWidth(600);

        // Username
        grid.add(createLabel("Username:"), 0, 0);
        workerUsernameField = createTextField("Enter username");
        grid.add(workerUsernameField, 1, 0);

        // Password
        grid.add(createLabel("Password:"), 0, 1);
        workerPasswordField = new PasswordField();
        workerPasswordField.setPromptText("Enter password (min 6 characters)");
        workerPasswordField.setPrefWidth(300);
        workerPasswordField.setPrefHeight(35);
        workerPasswordField.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                "-fx-prompt-text-fill: gray; -fx-background-radius: 5;");
        grid.add(workerPasswordField, 1, 1);

        // First Name
        grid.add(createLabel("First Name:"), 0, 2);
        workerFirstNameField = createTextField("Enter first name");
        grid.add(workerFirstNameField, 1, 2);

        // Last Name
        grid.add(createLabel("Last Name:"), 0, 3);
        workerLastNameField = createTextField("Enter last name");
        grid.add(workerLastNameField, 1, 3);

        // Email
        grid.add(createLabel("Email:"), 0, 4);
        workerEmailField = createTextField("Enter email address");
        grid.add(workerEmailField, 1, 4);

        // Phone
        grid.add(createLabel("Phone:"), 0, 5);
        workerPhoneField = createTextField("Enter phone number");
        grid.add(workerPhoneField, 1, 5);

        // Employee ID
        grid.add(createLabel("Employee ID:"), 0, 6);
        employeeIdField = createTextField("Enter employee ID");
        grid.add(employeeIdField, 1, 6);

        // Department
        grid.add(createLabel("Department:"), 0, 7);
        departmentField = createTextField("Enter department");
        grid.add(departmentField, 1, 7);

        // Create button
        createWorkerButton = new Button("Create Worker");
        createWorkerButton.setPrefWidth(200);
        createWorkerButton.setPrefHeight(45);
        createWorkerButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 16; " +
                "-fx-background-radius: 5; -fx-cursor: hand;");

        createWorkerButton.setOnMouseEntered(e ->
                createWorkerButton.setStyle("-fx-background-color: #e66d00; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 16; " +
                        "-fx-background-radius: 5; -fx-cursor: hand;"));

        createWorkerButton.setOnMouseExited(e ->
                createWorkerButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 16; " +
                        "-fx-background-radius: 5; -fx-cursor: hand;"));

        container.getChildren().addAll(titleLabel, grid, createWorkerButton);

        return container;
    }

    private ScrollPane createViewUsersPane() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: #2e2e2e;");

        Label titleLabel = new Label("All Users");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        userListContainer = new VBox(15);
        userListContainer.setAlignment(Pos.TOP_CENTER);

        container.getChildren().addAll(titleLabel, userListContainer);

        userScrollPane = new ScrollPane(container);
        userScrollPane.setFitToWidth(true);
        userScrollPane.setStyle("-fx-background: #2e2e2e; -fx-background-color: #2e2e2e;");

        return userScrollPane;
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

    // Getters
    public TopBar getTopBar() {
        return topBar;
    }

    public TextField getWorkerUsernameField() {
        return workerUsernameField;
    }

    public PasswordField getWorkerPasswordField() {
        return workerPasswordField;
    }

    public TextField getWorkerFirstNameField() {
        return workerFirstNameField;
    }

    public TextField getWorkerLastNameField() {
        return workerLastNameField;
    }

    public TextField getWorkerEmailField() {
        return workerEmailField;
    }

    public TextField getWorkerPhoneField() {
        return workerPhoneField;
    }

    public TextField getEmployeeIdField() {
        return employeeIdField;
    }

    public TextField getDepartmentField() {
        return departmentField;
    }

    public Button getCreateWorkerButton() {
        return createWorkerButton;
    }

    public VBox getUserListContainer() {
        return userListContainer;
    }
}