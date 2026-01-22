package org.example.carrental.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.carrental.model.vehicles.Car;

import java.io.File;

/**
 * Component for displaying a car in a card layout
 */
public class CarCard extends HBox {

    private Car car;
    private Button bookButton;
    private Button removeButton;

    public CarCard(Car car, boolean showBookButton, boolean showRemoveButton) {
        this.car = car;
        setupUI(showBookButton, showRemoveButton);
    }

    private void setupUI(boolean showBookButton, boolean showRemoveButton) {
        setStyle("-fx-background-color: #3a3a3a; -fx-background-radius: 10; " +
                "-fx-border-color: #ff7c0a; -fx-border-width: 2; -fx-border-radius: 10;");
        setPadding(new Insets(15));
        setSpacing(20);
        setAlignment(Pos.CENTER_LEFT);
        setPrefHeight(150);

        // Car image
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);

        if (car.getPhotoPath() != null && new File(car.getPhotoPath()).exists()) {
            try {
                Image image = new Image(new File(car.getPhotoPath()).toURI().toString());
                imageView.setImage(image);
            } catch (Exception e) {
                setPlaceholderImage(imageView);
            }
        } else {
            setPlaceholderImage(imageView);
        }

        // Car details
        VBox detailsBox = new VBox(8);
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(detailsBox, Priority.ALWAYS);

        Label nameLabel = new Label(car.getDisplayName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        nameLabel.setStyle("-fx-text-fill: #f2ed57;");

        Label categoryLabel = new Label(car.getCategory().toString());
        categoryLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        categoryLabel.setStyle("-fx-text-fill: white;");

        Label specsLabel = new Label(car.getSeats() + " seats • " +
                car.getTransmission() + " • " +
                car.getFuelType());
        specsLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        specsLabel.setStyle("-fx-text-fill: #cccccc;");

        Label licensePlateLabel = new Label("License: " + car.getLicensePlate());
        licensePlateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        licensePlateLabel.setStyle("-fx-text-fill: #999999;");

        detailsBox.getChildren().addAll(nameLabel, categoryLabel, specsLabel, licensePlateLabel);

        // Price and availability
        VBox priceBox = new VBox(8);
        priceBox.setAlignment(Pos.CENTER_RIGHT);

        Label priceLabel = new Label(String.format("€%.2f/day", car.getDailyRate()));
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        priceLabel.setStyle("-fx-text-fill: #ff7c0a;");

        Label availabilityLabel = new Label(car.isAvailable() ? "Available" : "Not Available");
        availabilityLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        availabilityLabel.setStyle("-fx-text-fill: " +
                (car.isAvailable() ? "#4CAF50" : "#f44336") + ";");

        priceBox.getChildren().addAll(priceLabel, availabilityLabel);

        // Buttons
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        if (showBookButton) {
            bookButton = new Button("Book Now");
            bookButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                    "-fx-font-weight: bold; -fx-background-radius: 5; " +
                    "-fx-padding: 10 20 10 20; -fx-cursor: hand;");
            bookButton.setDisable(!car.isAvailable());

            bookButton.setOnMouseEntered(e -> {
                if (car.isAvailable()) {
                    bookButton.setStyle("-fx-background-color: #e66d00; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-background-radius: 5; " +
                            "-fx-padding: 10 20 10 20; -fx-cursor: hand;");
                }
            });

            bookButton.setOnMouseExited(e -> {
                if (car.isAvailable()) {
                    bookButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-background-radius: 5; " +
                            "-fx-padding: 10 20 10 20; -fx-cursor: hand;");
                }
            });

            buttonBox.getChildren().add(bookButton);
        }

        if (showRemoveButton) {
            removeButton = new Button("Remove");
            removeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; " +
                    "-fx-font-weight: bold; -fx-background-radius: 5; " +
                    "-fx-padding: 8 16 8 16; -fx-cursor: hand;");

            removeButton.setOnMouseEntered(e ->
                    removeButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-background-radius: 5; " +
                            "-fx-padding: 8 16 8 16; -fx-cursor: hand;"));

            removeButton.setOnMouseExited(e ->
                    removeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-background-radius: 5; " +
                            "-fx-padding: 8 16 8 16; -fx-cursor: hand;"));

            buttonBox.getChildren().add(removeButton);
        }

        getChildren().addAll(imageView, detailsBox, priceBox, buttonBox);
    }

    private void setPlaceholderImage(ImageView imageView) {
        // Create a simple placeholder
        imageView.setStyle("-fx-background-color: #555555;");
    }

    public Car getCar() {
        return car;
    }

    public Button getBookButton() {
        return bookButton;
    }

    public Button getRemoveButton() {
        return removeButton;
    }
}