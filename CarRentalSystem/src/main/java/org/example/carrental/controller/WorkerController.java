package org.example.carrental.controller;

import javafx.stage.FileChooser;
import org.example.carrental.AppNavigator;
import org.example.carrental.model.booking.Booking;
import org.example.carrental.model.enums.CarCategory;
import org.example.carrental.model.users.Account;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.service.AuthService;
import org.example.carrental.service.BookingService;
import org.example.carrental.service.CarService;
import org.example.carrental.service.Session;
import org.example.carrental.storage.repositories.BookingRepository;
import org.example.carrental.storage.repositories.InvoiceRepository;
import org.example.carrental.storage.repositories.UserRepository;
import org.example.carrental.util.Dialogs;
import org.example.carrental.util.Validators;
import org.example.carrental.view.MainView;
import org.example.carrental.view.WorkerView;
import org.example.carrental.view.components.BookingRow;
import org.example.carrental.view.components.CarCard;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the worker view
 */
public class WorkerController {

    private final WorkerView view;
    private final CarService carService;
    private final AuthService authService;
    private final BookingService bookingService;
    private File selectedPhotoFile;

    public WorkerController(WorkerView view, CarService carService, AuthService authService) {
        this.view = view;
        this.carService = carService;
        this.authService = authService;

        BookingRepository bookingRepository = new BookingRepository();
        InvoiceRepository invoiceRepository = new InvoiceRepository();
        this.bookingService = new BookingService(
                bookingRepository,
                carService,
                new org.example.carrental.service.PricingService(),
                new org.example.carrental.service.InvoiceService(invoiceRepository)
        );

        setupEventHandlers();
        loadCars();
        loadBookings();
        updateNavigation();
    }

    private void setupEventHandlers() {
        // Upload photo button
        view.getUploadPhotoButton().setOnAction(e -> handlePhotoUpload());

        // Add car button
        view.getAddCarButton().setOnAction(e -> handleAddCar());

        // Top bar navigation
        view.getTopBar().getHomeButton().setOnAction(e -> goToHome());
        view.getTopBar().getLogoutButton().setOnAction(e -> logout());
    }

    private void handlePhotoUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Car Photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        selectedPhotoFile = fileChooser.showOpenDialog(AppNavigator.getInstance().getPrimaryStage());

        if (selectedPhotoFile != null) {
            view.getPhotoLabel().setText(selectedPhotoFile.getName());
        }
    }

    private void handleAddCar() {
        // Get all field values
        String brand = view.getBrandField().getText().trim();
        String model = view.getModelField().getText().trim();
        String yearText = view.getYearField().getText().trim();
        String licensePlate = view.getLicensePlateField().getText().trim();
        String dailyRateText = view.getDailyRateField().getText().trim();
        CarCategory category = view.getCategoryComboBox().getValue();
        String seatsText = view.getSeatsField().getText().trim();
        String transmission = view.getTransmissionComboBox().getValue();
        String fuelType = view.getFuelTypeComboBox().getValue();

        // Validate inputs
        if (!validateCarInputs(brand, model, yearText, licensePlate, dailyRateText,
                category, seatsText, transmission, fuelType)) {
            return;
        }

        try {
            int year = Integer.parseInt(yearText);
            double dailyRate = Double.parseDouble(dailyRateText);
            int seats = Integer.parseInt(seatsText);

            carService.addCar(brand, model, year, licensePlate, dailyRate,
                    category, seats, transmission, fuelType, selectedPhotoFile);

            Dialogs.showSuccess("Car added successfully!");
            clearAddCarForm();
            loadCars();

        } catch (NumberFormatException e) {
            Dialogs.showValidationError("Please enter valid numbers for year, daily rate, and seats.");
        } catch (IllegalArgumentException e) {
            Dialogs.showValidationError(e.getMessage());
        } catch (Exception e) {
            Dialogs.showError("Error", "An error occurred: " + e.getMessage());
        }
    }

    private boolean validateCarInputs(String brand, String model, String yearText,
                                      String licensePlate, String dailyRateText,
                                      CarCategory category, String seatsText,
                                      String transmission, String fuelType) {
        if (!Validators.isNotEmpty(brand) || !Validators.isNotEmpty(model)) {
            Dialogs.showValidationError("Brand and model are required.");
            return false;
        }

        if (!Validators.isNotEmpty(yearText) || !Validators.isNotEmpty(dailyRateText) ||
                !Validators.isNotEmpty(seatsText)) {
            Dialogs.showValidationError("Year, daily rate, and seats are required.");
            return false;
        }

        if (!Validators.isValidLicensePlate(licensePlate)) {
            Dialogs.showValidationError("Invalid license plate format. Use 3-10 alphanumeric characters.");
            return false;
        }

        if (category == null) {
            Dialogs.showValidationError("Please select a category.");
            return false;
        }

        if (transmission == null) {
            Dialogs.showValidationError("Please select a transmission type.");
            return false;
        }

        if (fuelType == null) {
            Dialogs.showValidationError("Please select a fuel type.");
            return false;
        }

        return true;
    }

    private void clearAddCarForm() {
        view.getBrandField().clear();
        view.getModelField().clear();
        view.getYearField().clear();
        view.getLicensePlateField().clear();
        view.getDailyRateField().clear();
        view.getCategoryComboBox().setValue(null);
        view.getSeatsField().clear();
        view.getTransmissionComboBox().setValue(null);
        view.getFuelTypeComboBox().setValue(null);
        view.getPhotoLabel().setText("No photo selected");
        selectedPhotoFile = null;
    }

    private void loadCars() {
        view.getCarListContainer().getChildren().clear();
        List<Car> cars = carService.getAllCars();

        for (Car car : cars) {
            CarCard carCard = new CarCard(car, false, true);

            if (carCard.getRemoveButton() != null) {
                carCard.getRemoveButton().setOnAction(e -> handleRemoveCar(car));
            }

            view.getCarListContainer().getChildren().add(carCard);
        }
    }

    private void handleRemoveCar(Car car) {
        boolean confirmed = Dialogs.showConfirmation(
                "Remove Car",
                "Are you sure you want to remove " + car.getDisplayName() + "?"
        );

        if (confirmed) {
            try {
                carService.removeCar(car.getId());
                Dialogs.showSuccess("Car removed successfully!");
                loadCars();
            } catch (Exception e) {
                Dialogs.showError("Error", "Failed to remove car: " + e.getMessage());
            }
        }
    }

    private void loadBookings() {
        view.getBookingListContainer().getChildren().clear();
        List<Booking> bookings = bookingService.getAllBookings();

        UserRepository userRepository = new UserRepository();

        for (Booking booking : bookings) {
            // Get car details
            String carDetails = "Unknown Car";
            Optional<Car> carOpt = carService.getCarById(booking.getCarId());
            if (carOpt.isPresent()) {
                carDetails = carOpt.get().getDisplayName();
            }

            // Get user details
            String userDetails = "Unknown User";
            Optional<Account> userOpt = userRepository.findById(booking.getUserId());
            if (userOpt.isPresent()) {
                userDetails = userOpt.get().getFullName();
            }

            BookingRow bookingRow = new BookingRow(booking, carDetails, userDetails);
            view.getBookingListContainer().getChildren().add(bookingRow);
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