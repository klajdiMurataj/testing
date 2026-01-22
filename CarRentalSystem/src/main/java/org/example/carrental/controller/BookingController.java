package org.example.carrental.controller;

import org.example.carrental.AppNavigator;
import org.example.carrental.model.booking.Booking;
import org.example.carrental.model.booking.PriceBreakdown;
import org.example.carrental.model.common.DateRange;
import org.example.carrental.model.users.Account;
import org.example.carrental.service.AuthService;
import org.example.carrental.service.BookingService;
import org.example.carrental.service.CarService;
import org.example.carrental.service.PricingService;
import org.example.carrental.service.Session;
import org.example.carrental.storage.repositories.BookingRepository;
import org.example.carrental.storage.repositories.InvoiceRepository;
import org.example.carrental.util.Dialogs;
import org.example.carrental.util.Validators;
import org.example.carrental.view.BookingView;
import org.example.carrental.view.MainView;

import java.time.LocalDate;

/**
 * Controller for the booking view
 */
public class BookingController {

    private final BookingView view;
    private final CarService carService;
    private final AuthService authService;
    private final PricingService pricingService;
    private final BookingService bookingService;

    public BookingController(BookingView view, CarService carService, AuthService authService) {
        this.view = view;
        this.carService = carService;
        this.authService = authService;
        this.pricingService = new PricingService();

        BookingRepository bookingRepository = new BookingRepository();
        InvoiceRepository invoiceRepository = new InvoiceRepository();
        this.bookingService = new BookingService(
                bookingRepository,
                carService,
                pricingService,
                new org.example.carrental.service.InvoiceService(invoiceRepository)
        );

        setupEventHandlers();
        updateNavigation();
    }

    private void setupEventHandlers() {
        // Date pickers - update price when dates change
        view.getStartDatePicker().setOnAction(e -> updatePrice());
        view.getEndDatePicker().setOnAction(e -> updatePrice());

        // Confirm booking button
        view.getConfirmButton().setOnAction(e -> handleBooking());

        // Back button
        view.getBackButton().setOnAction(e -> goToHome());

        // Top bar navigation
        view.getTopBar().getHomeButton().setOnAction(e -> goToHome());
    }

    private void updatePrice() {
        LocalDate startDate = view.getStartDatePicker().getValue();
        LocalDate endDate = view.getEndDatePicker().getValue();

        if (startDate == null || endDate == null) {
            view.getTotalLabel().setText("Select dates to see price");
            view.getConfirmButton().setDisable(true);
            return;
        }

        // Validate dates
        if (!Validators.isNotPastDate(startDate)) {
            view.getTotalLabel().setText("Start date cannot be in the past");
            view.getConfirmButton().setDisable(true);
            return;
        }

        if (!Validators.isValidDateRange(startDate, endDate)) {
            view.getTotalLabel().setText("End date must be after start date");
            view.getConfirmButton().setDisable(true);
            return;
        }

        try {
            DateRange dateRange = new DateRange(startDate, endDate);
            PriceBreakdown breakdown = pricingService.calculatePrice(view.getSelectedCar(), dateRange);

            view.getTotalLabel().setText(String.format("€%.2f", breakdown.getTotalPrice()));
            view.getConfirmButton().setDisable(false);

        } catch (Exception e) {
            view.getTotalLabel().setText("Error calculating price");
            view.getConfirmButton().setDisable(true);
        }
    }

    private void handleBooking() {
        LocalDate startDate = view.getStartDatePicker().getValue();
        LocalDate endDate = view.getEndDatePicker().getValue();

        if (startDate == null || endDate == null) {
            Dialogs.showValidationError("Please select both start and end dates.");
            return;
        }

        // Validate dates
        if (!Validators.isNotPastDate(startDate)) {
            Dialogs.showValidationError("Start date cannot be in the past.");
            return;
        }

        if (!Validators.isValidDateRange(startDate, endDate)) {
            Dialogs.showValidationError("End date must be after start date.");
            return;
        }

        // Confirm booking
        boolean confirmed = Dialogs.showConfirmation(
                "Confirm Booking",
                "Are you sure you want to book this car from " + startDate + " to " + endDate + "?"
        );

        if (!confirmed) {
            return;
        }

        try {
            DateRange dateRange = new DateRange(startDate, endDate);
            Account currentUser = Session.getInstance().getCurrentUser();

            Booking booking = bookingService.createBooking(
                    view.getSelectedCar().getId(),
                    dateRange,
                    currentUser
            );

            Dialogs.showSuccess(
                    "Booking confirmed!\n\n" +
                            "Booking ID: " + booking.getId() + "\n" +
                            "Invoice ID: " + booking.getInvoiceId() + "\n\n" +
                            "An invoice has been generated in the invoices folder."
            );

            goToHome();

        } catch (IllegalArgumentException e) {
            Dialogs.showError("Booking Failed", e.getMessage());
        } catch (Exception e) {
            Dialogs.showError("Booking Error", "An error occurred: " + e.getMessage());
        }
    }

    private void goToHome() {
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