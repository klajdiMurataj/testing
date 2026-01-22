package org.example.carrental.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.carrental.model.booking.Booking;

/**
 * Component for displaying a booking in a row layout
 */
public class BookingRow extends HBox {

    private Booking booking;
    private String carDetails;
    private String userDetails;

    public BookingRow(Booking booking, String carDetails, String userDetails) {
        this.booking = booking;
        this.carDetails = carDetails;
        this.userDetails = userDetails;
        setupUI();
    }

    private void setupUI() {
        setStyle("-fx-background-color: #3a3a3a; -fx-background-radius: 8; " +
                "-fx-border-color: #555555; -fx-border-width: 1; -fx-border-radius: 8;");
        setPadding(new Insets(15));
        setSpacing(20);
        setAlignment(Pos.CENTER_LEFT);
        setPrefHeight(100);

        // Booking ID section
        VBox idBox = new VBox(5);
        idBox.setPrefWidth(150);
        Label idTitleLabel = new Label("Booking ID");
        idTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        idTitleLabel.setStyle("-fx-text-fill: #f2ed57;");

        Label idLabel = new Label(booking.getId());
        idLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
        idLabel.setStyle("-fx-text-fill: white;");

        idBox.getChildren().addAll(idTitleLabel, idLabel);

        // Customer section
        VBox customerBox = new VBox(5);
        customerBox.setPrefWidth(200);
        HBox.setHgrow(customerBox, Priority.ALWAYS);

        Label customerTitleLabel = new Label("Customer");
        customerTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        customerTitleLabel.setStyle("-fx-text-fill: #f2ed57;");

        Label customerLabel = new Label(userDetails);
        customerLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
        customerLabel.setStyle("-fx-text-fill: white;");

        customerBox.getChildren().addAll(customerTitleLabel, customerLabel);

        // Car section
        VBox carBox = new VBox(5);
        carBox.setPrefWidth(200);
        HBox.setHgrow(carBox, Priority.ALWAYS);

        Label carTitleLabel = new Label("Vehicle");
        carTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        carTitleLabel.setStyle("-fx-text-fill: #f2ed57;");

        Label carLabel = new Label(carDetails);
        carLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
        carLabel.setStyle("-fx-text-fill: white;");

        carBox.getChildren().addAll(carTitleLabel, carLabel);

        // Date section
        VBox dateBox = new VBox(5);
        dateBox.setPrefWidth(200);

        Label dateTitleLabel = new Label("Rental Period");
        dateTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        dateTitleLabel.setStyle("-fx-text-fill: #f2ed57;");

        Label dateLabel = new Label(booking.getDateRange().toString());
        dateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
        dateLabel.setStyle("-fx-text-fill: white;");

        dateBox.getChildren().addAll(dateTitleLabel, dateLabel);

        // Price section
        VBox priceBox = new VBox(5);
        priceBox.setPrefWidth(120);
        priceBox.setAlignment(Pos.CENTER_RIGHT);

        Label priceTitleLabel = new Label("Total");
        priceTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        priceTitleLabel.setStyle("-fx-text-fill: #f2ed57;");

        Label priceLabel = new Label(String.format("€%.2f",
                booking.getPriceBreakdown().getTotalPrice()));
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        priceLabel.setStyle("-fx-text-fill: #ff7c0a;");

        priceBox.getChildren().addAll(priceTitleLabel, priceLabel);

        // Status section
        VBox statusBox = new VBox(5);
        statusBox.setPrefWidth(100);
        statusBox.setAlignment(Pos.CENTER);

        Label statusLabel = new Label(booking.getStatus().toString());
        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        String statusColor;
        switch (booking.getStatus()) {
            case CONFIRMED:
                statusColor = "#4CAF50";
                break;
            case ACTIVE:
                statusColor = "#2196F3";
                break;
            case COMPLETED:
                statusColor = "#9E9E9E";
                break;
            case CANCELLED:
                statusColor = "#f44336";
                break;
            default:
                statusColor = "#FFC107";
                break;
        }
        statusLabel.setStyle("-fx-text-fill: " + statusColor + ";");

        statusBox.getChildren().add(statusLabel);

        getChildren().addAll(idBox, customerBox, carBox, dateBox, priceBox, statusBox);
    }

    public Booking getBooking() {
        return booking;
    }
}