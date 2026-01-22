package org.example.carrental.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.carrental.model.enums.CarCategory;
import org.example.carrental.view.components.TopBar;

/**
 * Worker view for managing cars and viewing bookings
 */
public class WorkerView extends BorderPane {

    private TopBar topBar;
    private TabPane tabPane;

    // Add Car tab components
    private TextField brandField;
    private TextField modelField;
    private TextField yearField;
    private TextField licensePlateField;
    private TextField dailyRateField;
    private ComboBox<CarCategory> categoryComboBox;
    private TextField seatsField;
    private ComboBox<String> transmissionComboBox;
    private ComboBox<String> fuelTypeComboBox;
    private Button uploadPhotoButton;
    private Label photoLabel;
    private Button addCarButton;

    // Manage Cars tab
    private VBox carListContainer;
    private ScrollPane carScrollPane;

    // View Bookings tab
    private VBox bookingListContainer;
    private ScrollPane bookingScrollPane;

    public WorkerView() {
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

        // Add Car tab
        Tab addCarTab = new Tab("Add Car");
        addCarTab.setClosable(false);
        addCarTab.setContent(createAddCarPane());

        // Manage Cars tab
        Tab manageCarsTab = new Tab("Manage Cars");
        manageCarsTab.setClosable(false);
        manageCarsTab.setContent(createManageCarsPane());

        // View Bookings tab
        Tab viewBookingsTab = new Tab("View Bookings");
        viewBookingsTab.setClosable(false);
        viewBookingsTab.setContent(createViewBookingsPane());

        tabPane.getTabs().addAll(addCarTab, manageCarsTab, viewBookingsTab);

        setCenter(tabPane);
    }

    private VBox createAddCarPane() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setAlignment(Pos.TOP_CENTER);
        container.setStyle("-fx-background-color: #2e2e2e;");

        // Title
        Label titleLabel = new Label("Add New Car");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        // Form
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);
        grid.setMaxWidth(600);

        // Brand
        grid.add(createLabel("Brand:"), 0, 0);
        brandField = createTextField("e.g., Toyota");
        grid.add(brandField, 1, 0);

        // Model
        grid.add(createLabel("Model:"), 0, 1);
        modelField = createTextField("e.g., Camry");
        grid.add(modelField, 1, 1);

        // Year
        grid.add(createLabel("Year:"), 0, 2);
        yearField = createTextField("e.g., 2023");
        grid.add(yearField, 1, 2);

        // License Plate
        grid.add(createLabel("License Plate:"), 0, 3);
        licensePlateField = createTextField("e.g., AA-123-BB");
        grid.add(licensePlateField, 1, 3);

        // Daily Rate
        grid.add(createLabel("Daily Rate (€):"), 0, 4);
        dailyRateField = createTextField("e.g., 50.00");
        grid.add(dailyRateField, 1, 4);

        // Category
        grid.add(createLabel("Category:"), 0, 5);
        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(CarCategory.values());
        categoryComboBox.setPrefWidth(300);
        categoryComboBox.setStyle("-fx-background-color: white;");
        grid.add(categoryComboBox, 1, 5);

        // Seats
        grid.add(createLabel("Seats:"), 0, 6);
        seatsField = createTextField("e.g., 5");
        grid.add(seatsField, 1, 6);

        // Transmission
        grid.add(createLabel("Transmission:"), 0, 7);
        transmissionComboBox = new ComboBox<>();
        transmissionComboBox.getItems().addAll("Manual", "Automatic");
        transmissionComboBox.setPrefWidth(300);
        transmissionComboBox.setStyle("-fx-background-color: white;");
        grid.add(transmissionComboBox, 1, 7);

        // Fuel Type
        grid.add(createLabel("Fuel Type:"), 0, 8);
        fuelTypeComboBox = new ComboBox<>();
        fuelTypeComboBox.getItems().addAll("Petrol", "Diesel", "Electric", "Hybrid");
        fuelTypeComboBox.setPrefWidth(300);
        fuelTypeComboBox.setStyle("-fx-background-color: white;");
        grid.add(fuelTypeComboBox, 1, 8);

        // Photo upload
        grid.add(createLabel("Photo:"), 0, 9);
        VBox photoBox = new VBox(10);
        uploadPhotoButton = new Button("Choose Photo");
        uploadPhotoButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; " +
                "-fx-background-radius: 5; -fx-cursor: hand;");
        photoLabel = new Label("No photo selected");
        photoLabel.setStyle("-fx-text-fill: #cccccc;");
        photoBox.getChildren().addAll(uploadPhotoButton, photoLabel);
        grid.add(photoBox, 1, 9);

        // Add button
        addCarButton = new Button("Add Car");
        addCarButton.setPrefWidth(200);
        addCarButton.setPrefHeight(45);
        addCarButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 16; " +
                "-fx-background-radius: 5; -fx-cursor: hand;");

        addCarButton.setOnMouseEntered(e ->
                addCarButton.setStyle("-fx-background-color: #e66d00; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 16; " +
                        "-fx-background-radius: 5; -fx-cursor: hand;"));

        addCarButton.setOnMouseExited(e ->
                addCarButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 16; " +
                        "-fx-background-radius: 5; -fx-cursor: hand;"));

        container.getChildren().addAll(titleLabel, grid, addCarButton);

        return container;
    }

    private ScrollPane createManageCarsPane() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: #2e2e2e;");

        Label titleLabel = new Label("Manage Cars");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        carListContainer = new VBox(15);
        carListContainer.setAlignment(Pos.TOP_CENTER);

        container.getChildren().addAll(titleLabel, carListContainer);

        carScrollPane = new ScrollPane(container);
        carScrollPane.setFitToWidth(true);
        carScrollPane.setStyle("-fx-background: #2e2e2e; -fx-background-color: #2e2e2e;");

        return carScrollPane;
    }

    private ScrollPane createViewBookingsPane() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: #2e2e2e;");

        Label titleLabel = new Label("All Bookings");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        bookingListContainer = new VBox(15);
        bookingListContainer.setAlignment(Pos.TOP_CENTER);

        container.getChildren().addAll(titleLabel, bookingListContainer);

        bookingScrollPane = new ScrollPane(container);
        bookingScrollPane.setFitToWidth(true);
        bookingScrollPane.setStyle("-fx-background: #2e2e2e; -fx-background-color: #2e2e2e;");

        return bookingScrollPane;
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

    public TextField getBrandField() {
        return brandField;
    }

    public TextField getModelField() {
        return modelField;
    }

    public TextField getYearField() {
        return yearField;
    }

    public TextField getLicensePlateField() {
        return licensePlateField;
    }

    public TextField getDailyRateField() {
        return dailyRateField;
    }

    public ComboBox<CarCategory> getCategoryComboBox() {
        return categoryComboBox;
    }

    public TextField getSeatsField() {
        return seatsField;
    }

    public ComboBox<String> getTransmissionComboBox() {
        return transmissionComboBox;
    }

    public ComboBox<String> getFuelTypeComboBox() {
        return fuelTypeComboBox;
    }

    public Button getUploadPhotoButton() {
        return uploadPhotoButton;
    }

    public Label getPhotoLabel() {
        return photoLabel;
    }

    public Button getAddCarButton() {
        return addCarButton;
    }

    public VBox getCarListContainer() {
        return carListContainer;
    }

    public VBox getBookingListContainer() {
        return bookingListContainer;
    }
}