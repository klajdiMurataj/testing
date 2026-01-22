package org.example.carrental.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.carrental.AppNavigator;
import org.example.carrental.model.booking.Booking;
import org.example.carrental.model.booking.PriceBreakdown;
import org.example.carrental.model.common.DateRange;
import org.example.carrental.model.enums.BookingStatus;
import org.example.carrental.model.users.Account;
import org.example.carrental.model.users.EndUser;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.service.*;
import org.example.carrental.storage.repositories.BookingRepository;
import org.example.carrental.storage.repositories.InvoiceRepository;
import org.example.carrental.util.Dialogs;
import org.example.carrental.util.Validators;
import org.example.carrental.view.MainView;
import org.example.carrental.view.MyAccountView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controller for My Account view
 */
public class MyAccountController {

    private final MyAccountView view;
    private final AuthService authService;
    private final CarService carService;
    private final BookingService bookingService;
    private final InvoiceService invoiceService;
    private final PricingService pricingService;
    private final BookingRepository bookingRepository;

    public MyAccountController(MyAccountView view, AuthService authService, CarService carService) {
        this.view = view;
        this.authService = authService;
        this.carService = carService;

        this.bookingRepository = new BookingRepository();
        InvoiceRepository invoiceRepository = new InvoiceRepository();
        this.pricingService = new PricingService();
        this.invoiceService = new InvoiceService(invoiceRepository);
        this.bookingService = new BookingService(bookingRepository, carService, pricingService, invoiceService);

        setupEventHandlers();
        loadAccountDetails();
        loadBookings();
        loadInvoices();
        updateNavigation();
    }

    private void setupEventHandlers() {
        view.getTopBar().getHomeButton().setOnAction(e -> goToHome());
        view.getTopBar().getLogoutButton().setOnAction(e -> logout());
        view.getTopBar().getMyAccountButton().setOnAction(e -> refresh());
    }

    private void loadAccountDetails() {
        Account currentUser = Session.getInstance().getCurrentUser();

        if (currentUser instanceof EndUser) {
            EndUser endUser = (EndUser) currentUser;

            view.getNameLabel().setText("Name: " + endUser.getFullName());
            view.getEmailLabel().setText("Email: " + endUser.getEmail());
            view.getPhoneLabel().setText("Phone: " + endUser.getPhoneNumber());
            view.getLicenseLabel().setText("Driver License: " + endUser.getDriverLicenseNumber());
            view.getAddressLabel().setText("Address: " + endUser.getAddress());
        }
    }

    private void loadBookings() {
        view.getBookingListContainer().getChildren().clear();

        String userId = Session.getInstance().getCurrentUserId();
        List<Booking> bookings = bookingService.getUserBookings(userId);

        if (bookings.isEmpty()) {
            Label noBookingsLabel = new Label("No bookings found");
            noBookingsLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 16;");
            view.getBookingListContainer().getChildren().add(noBookingsLabel);
            return;
        }

        for (Booking booking : bookings) {
            HBox bookingRow = createBookingRow(booking);
            view.getBookingListContainer().getChildren().add(bookingRow);
        }
    }

    private HBox createBookingRow(Booking booking) {
        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(15));
        row.setStyle("-fx-background-color: #3a3a3a; -fx-background-radius: 8; " +
                "-fx-border-color: #555555; -fx-border-width: 1; -fx-border-radius: 8;");

        // Get car details
        String carDetails = "Unknown Car";
        Optional<Car> carOpt = carService.getCarById(booking.getCarId());
        if (carOpt.isPresent()) {
            carDetails = carOpt.get().getDisplayName();
        }

        // Booking info
        VBox infoBox = new VBox(8);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        Label carLabel = new Label(carDetails);
        carLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        carLabel.setStyle("-fx-text-fill: #f2ed57;");

        Label dateLabel = new Label(booking.getDateRange().toString());
        dateLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        dateLabel.setStyle("-fx-text-fill: white;");

        Label priceLabel = new Label(String.format("Total: €%.2f", booking.getPriceBreakdown().getTotalPrice()));
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        priceLabel.setStyle("-fx-text-fill: #ff7c0a;");

        Label statusLabel = new Label("Status: " + booking.getStatus());
        statusLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        statusLabel.setStyle("-fx-text-fill: " + getStatusColor(booking.getStatus()) + ";");

        infoBox.getChildren().addAll(carLabel, dateLabel, priceLabel, statusLabel);

        // Buttons
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPrefWidth(120);

        if (booking.getStatus() == BookingStatus.CONFIRMED || booking.getStatus() == BookingStatus.PENDING) {
            // Edit button
            Button editButton = new Button("Edit");
            editButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; " +
                    "-fx-font-weight: bold; -fx-background-radius: 5; " +
                    "-fx-padding: 8 16 8 16; -fx-cursor: hand;");

            editButton.setOnMouseEntered(e ->
                    editButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-background-radius: 5; " +
                            "-fx-padding: 8 16 8 16; -fx-cursor: hand;"));

            editButton.setOnMouseExited(e ->
                    editButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-background-radius: 5; " +
                            "-fx-padding: 8 16 8 16; -fx-cursor: hand;"));

            editButton.setOnAction(e -> handleEditBooking(booking));

            // Cancel button
            Button cancelButton = new Button("Cancel");
            cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; " +
                    "-fx-font-weight: bold; -fx-background-radius: 5; " +
                    "-fx-padding: 8 16 8 16; -fx-cursor: hand;");

            cancelButton.setOnMouseEntered(e ->
                    cancelButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-background-radius: 5; " +
                            "-fx-padding: 8 16 8 16; -fx-cursor: hand;"));

            cancelButton.setOnMouseExited(e ->
                    cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-background-radius: 5; " +
                            "-fx-padding: 8 16 8 16; -fx-cursor: hand;"));

            cancelButton.setOnAction(e -> handleCancelBooking(booking));

            buttonBox.getChildren().addAll(editButton, cancelButton);
        }

        row.getChildren().addAll(infoBox, buttonBox);

        return row;
    }

    private void handleEditBooking(Booking booking) {
        // Get car for this booking
        Optional<Car> carOpt = carService.getCarById(booking.getCarId());
        if (carOpt.isEmpty()) {
            Dialogs.showError("Error", "Car not found.");
            return;
        }

        Car car = carOpt.get();

        // Create dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Booking");
        dialog.setHeaderText("Modify your booking dates");

        // Create form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Label carLabel = new Label("Car: " + car.getDisplayName());
        carLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label startLabel = new Label("New Start Date:");
        DatePicker startDatePicker = new DatePicker(booking.getDateRange().getStartDate());

        Label endLabel = new Label("New End Date:");
        DatePicker endDatePicker = new DatePicker(booking.getDateRange().getEndDate());

        Label priceLabel = new Label("Total: Calculating...");
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        priceLabel.setStyle("-fx-text-fill: #ff7c0a;");

        // Update price when dates change
        Runnable updatePrice = () -> {
            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();

            if (start != null && end != null && !end.isBefore(start)) {
                try {
                    DateRange newRange = new DateRange(start, end);
                    PriceBreakdown breakdown = pricingService.calculatePrice(car, newRange);
                    priceLabel.setText(String.format("Total: €%.2f (including VAT)", breakdown.getTotalPrice()));
                } catch (Exception e) {
                    priceLabel.setText("Total: Invalid dates");
                }
            } else {
                priceLabel.setText("Total: Invalid dates");
            }
        };

        startDatePicker.setOnAction(e -> updatePrice.run());
        endDatePicker.setOnAction(e -> updatePrice.run());

        grid.add(carLabel, 0, 0, 2, 1);
        grid.add(startLabel, 0, 1);
        grid.add(startDatePicker, 1, 1);
        grid.add(endLabel, 0, 2);
        grid.add(endDatePicker, 1, 2);
        grid.add(priceLabel, 0, 3, 2, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Initial price calculation
        updatePrice.run();

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            LocalDate newStart = startDatePicker.getValue();
            LocalDate newEnd = endDatePicker.getValue();

            if (newStart == null || newEnd == null) {
                Dialogs.showValidationError("Please select both start and end dates.");
                return;
            }

            if (!Validators.isNotPastDate(newStart)) {
                Dialogs.showValidationError("Start date cannot be in the past.");
                return;
            }

            if (!Validators.isValidDateRange(newStart, newEnd)) {
                Dialogs.showValidationError("End date must be after start date.");
                return;
            }

            try {
                DateRange newDateRange = new DateRange(newStart, newEnd);

                // Check for overlapping bookings (excluding current booking)
                List<Booking> carBookings = bookingRepository.findByCarId(booking.getCarId());
                boolean hasConflict = carBookings.stream()
                        .filter(b -> !b.getId().equals(booking.getId())) // Exclude current booking
                        .filter(b -> b.getStatus() == BookingStatus.CONFIRMED ||
                                b.getStatus() == BookingStatus.ACTIVE)
                        .anyMatch(b -> b.getDateRange().overlaps(newDateRange));

                if (hasConflict) {
                    Dialogs.showError("Date Conflict", "The car is already booked for the selected dates.");
                    return;
                }

                // Update booking
                PriceBreakdown newPriceBreakdown = pricingService.calculatePrice(car, newDateRange);
                booking.setDateRange(newDateRange);
                booking.setPriceBreakdown(newPriceBreakdown);
                bookingRepository.update(booking);

                // Delete old invoice
                if (booking.getInvoiceId() != null) {
                    invoiceService.deleteInvoice(booking.getInvoiceId());
                }

                // Generate new invoice
                Account currentUser = Session.getInstance().getCurrentUser();
                org.example.carrental.model.booking.Invoice newInvoice =
                        invoiceService.createInvoice(booking, currentUser, car);

                booking.setInvoiceId(newInvoice.getId());
                bookingRepository.update(booking);

                Dialogs.showSuccess("Booking updated successfully!\nNew invoice generated.");
                loadBookings();
                loadInvoices();

            } catch (Exception e) {
                Dialogs.showError("Error", "Failed to update booking: " + e.getMessage());
            }
        }
    }

    private void handleCancelBooking(Booking booking) {
        boolean confirmed = Dialogs.showConfirmation(
                "Cancel Booking",
                "Are you sure you want to cancel this booking?\nThis will delete the invoice and free up the car."
        );

        if (confirmed) {
            try {
                // Delete invoice
                if (booking.getInvoiceId() != null) {
                    invoiceService.deleteInvoice(booking.getInvoiceId());
                }

                // Cancel booking
                bookingService.cancelBooking(booking.getId());

                // Free up car
                carService.setCarAvailability(booking.getCarId(), true);

                Dialogs.showSuccess("Booking cancelled successfully!");
                loadBookings();
                loadInvoices();

            } catch (Exception e) {
                Dialogs.showError("Error", "Failed to cancel booking: " + e.getMessage());
            }
        }
    }

    private void loadInvoices() {
        view.getInvoiceListContainer().getChildren().clear();

        String userId = Session.getInstance().getCurrentUserId();
        List<Booking> bookings = bookingService.getUserBookings(userId);

        boolean hasInvoices = false;
        for (Booking booking : bookings) {
            if (booking.getInvoiceId() != null && booking.getStatus() != BookingStatus.CANCELLED) {
                VBox invoiceBox = createInvoiceBox(booking);
                view.getInvoiceListContainer().getChildren().add(invoiceBox);
                hasInvoices = true;
            }
        }

        if (!hasInvoices) {
            Label noInvoicesLabel = new Label("No invoices found");
            noInvoicesLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 16;");
            view.getInvoiceListContainer().getChildren().add(noInvoicesLabel);
        }
    }

    private VBox createInvoiceBox(Booking booking) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-background-color: #3a3a3a; -fx-background-radius: 8; " +
                "-fx-border-color: #555555; -fx-border-width: 1; -fx-border-radius: 8;");

        Label invoiceIdLabel = new Label("Invoice ID: " + booking.getInvoiceId());
        invoiceIdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        invoiceIdLabel.setStyle("-fx-text-fill: #f2ed57;");

        Label bookingIdLabel = new Label("Booking ID: " + booking.getId());
        bookingIdLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        bookingIdLabel.setStyle("-fx-text-fill: white;");

        Button viewButton = new Button("View Invoice");
        viewButton.setStyle("-fx-background-color: #ff7c0a; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");

        viewButton.setOnAction(e -> showInvoiceContent(booking.getInvoiceId()));

        box.getChildren().addAll(invoiceIdLabel, bookingIdLabel, viewButton);

        return box;
    }

    private void showInvoiceContent(String invoiceId) {
        Optional<String> contentOpt = invoiceService.getInvoiceContent(invoiceId);

        if (contentOpt.isPresent()) {
            TextArea textArea = new TextArea(contentOpt.get());
            textArea.setEditable(false);
            textArea.setPrefRowCount(20);
            textArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");

            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Invoice - " + invoiceId);
            dialog.getDialogPane().setContent(textArea);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.showAndWait();
        } else {
            Dialogs.showError("Error", "Invoice not found.");
        }
    }

    private String getStatusColor(BookingStatus status) {
        switch (status) {
            case CONFIRMED: return "#4CAF50";
            case ACTIVE: return "#2196F3";
            case COMPLETED: return "#9E9E9E";
            case CANCELLED: return "#f44336";
            default: return "#FFC107";
        }
    }

    private void logout() {
        authService.logout();
        Dialogs.showInfo("Logged Out", "You have been logged out successfully.");
        goToHome();
    }

    private void goToHome() {
        MainView mainView = new MainView();
        new MainController(mainView, carService, authService);
        AppNavigator.getInstance().navigateTo(mainView);
    }

    private void refresh() {
        MyAccountView newView = new MyAccountView();
        new MyAccountController(newView, authService, carService);
        AppNavigator.getInstance().navigateTo(newView);
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