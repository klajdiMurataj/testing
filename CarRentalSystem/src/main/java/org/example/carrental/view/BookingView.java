package org.example.carrental.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.view.components.TopBar;

/**
 * Booking view for selecting dates and confirming rental
 */
public class BookingView extends BorderPane {

    private TopBar topBar;
    private Car selectedCar;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private Label carNameLabel;
    private Label priceLabel;
    private Label totalLabel;
    private Button confirmButton;
    private Button backButton;

    public BookingView(Car car) {
        this.selectedCar = car;
        setupUI();
    }

    private void setupUI() {
        setStyle("-fx-background-color: #2e2e2e;");

        // Top bar
        topBar = new TopBar();
        setTop(topBar);

        // Booking form
        VBox formBox = new VBox(25);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(50));
        formBox.setMaxWidth(500);
        formBox.setStyle("-fx-background-color: #3a3a3a; -fx-background-radius: 10; " +
                "-fx-border-color: #ff7c0a; -fx-border-width: 2; -fx-border-radius: 10;");

        // Title
        Label titleLabel = new Label("Book Your Rental");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setStyle("-fx-text-fill: #f2ed57;");

        // Car details
        carNameLabel = new Label(selectedCar.getDisplayName());
        carNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        carNameLabel.setStyle("-fx-text-fill: white;");

        priceLabel = new Label(String.format("Daily Rate: €%.2f", selectedCar.getDailyRate()));
        priceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        priceLabel.setStyle("-fx-text-fill: #ff7c0a;");

        // Date selection grid
        GridPane dateGrid = new GridPane();
        dateGrid.setHgap(20);
        dateGrid.setVgap(15);
        dateGrid.setAlignment(Pos.CENTER);

        // Start date
        Label startDateLabel = new Label("Start Date:");
        startDateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        startDateLabel.setStyle("-fx-text-fill: white;");

        startDatePicker = new DatePicker();
        startDatePicker.setPrefWidth(250);
        startDatePicker.setStyle("-fx-background-color: white; -fx-font-size: 13;");

        dateGrid.add(startDateLabel, 0, 0);
        dateGrid.add(startDatePicker, 1, 0);

        // End date
        Label endDateLabel = new Label("End Date:");
        endDateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        endDateLabel.setStyle("-fx-text-fill: white;");

        endDatePicker = new DatePicker();
        endDatePicker.setPrefWidth(250);
        endDatePicker.setStyle("-fx-background-color: white; -fx-font-size: 13;");

        dateGrid.add(endDateLabel, 0, 1);
        dateGrid.add(endDatePicker, 1, 1);

        // Total price display
        VBox totalBox = new VBox(10);
        totalBox.setAlignment(Pos.CENTER);
        totalBox.setPadding(new Insets(20, 0, 0, 0));

        Label totalTitleLabel = new Label("Total Price (including VAT):");
        totalTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        totalTitleLabel.setStyle("-fx-text-fill: #f2ed57;");

        totalLabel = new Label("Select dates to see price");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        totalLabel.setStyle("-fx-text-fill: #ff7c0a;");

        totalBox.getChildren().addAll(totalTitleLabel, totalLabel);

        // Confirm button
        confirmButton = new Button("Confirm Booking");
        confirmButton.setPrefWidth(250);
        confirmButton.setPrefHeight(45);
        confirmButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-font-size: 16; " +
                "-fx-background-radius: 5; -fx-cursor: hand;");
        confirmButton.setDisable(true);

        confirmButton.setOnMouseEntered(e -> {
            if (!confirmButton.isDisabled()) {
                confirmButton.setStyle("-fx-background-color: #e66d00; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 16; " +
                        "-fx-background-radius: 5; -fx-cursor: hand;");
            }
        });

        confirmButton.setOnMouseExited(e -> {
            if (!confirmButton.isDisabled()) {
                confirmButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-font-size: 16; " +
                        "-fx-background-radius: 5; -fx-cursor: hand;");
            }
        });

        // Back button
        backButton = new Button("Back to Cars");
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

        formBox.getChildren().addAll(
                titleLabel,
                carNameLabel,
                priceLabel,
                dateGrid,
                totalBox,
                confirmButton,
                backButton
        );

        // Center the form
        VBox centerContainer = new VBox(formBox);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setStyle("-fx-background-color: #2e2e2e;");

        setCenter(centerContainer);
    }

    // Getters
    public TopBar getTopBar() {
        return topBar;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public DatePicker getStartDatePicker() {
        return startDatePicker;
    }

    public DatePicker getEndDatePicker() {
        return endDatePicker;
    }

    public Label getTotalLabel() {
        return totalLabel;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}