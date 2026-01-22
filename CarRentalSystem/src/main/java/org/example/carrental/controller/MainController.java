package org.example.carrental.controller;

import org.example.carrental.AppNavigator;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.service.AuthService;
import org.example.carrental.service.CarService;
import org.example.carrental.service.Session;
import org.example.carrental.view.BookingView;
import org.example.carrental.view.LoginView;
import org.example.carrental.view.MainView;
import org.example.carrental.view.SignupView;
import org.example.carrental.view.WorkerView;
import org.example.carrental.view.AdminView;
import org.example.carrental.view.components.CarCard;
import org.example.carrental.util.Dialogs;

import java.util.List;

/**
 * Controller for the main view (home page)
 */
public class MainController {

    private final MainView view;
    private final CarService carService;
    private final AuthService authService;

    public MainController(MainView view, CarService carService, AuthService authService) {
        this.view = view;
        this.carService = carService;
        this.authService = authService;

        setupEventHandlers();
        loadCars();
        updateNavigation();
    }

    private void setupEventHandlers() {
        // Top bar navigation
        view.getTopBar().getHomeButton().setOnAction(e -> refresh());

        view.getTopBar().getLoginButton().setOnAction(e -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView, authService, carService);
            AppNavigator.getInstance().navigateTo(loginView);
        });

        view.getTopBar().getSignupButton().setOnAction(e -> {
            SignupView signupView = new SignupView();
            new SignupController(signupView, authService);
            AppNavigator.getInstance().navigateTo(signupView);
        });

        view.getTopBar().getMyAccountButton().setOnAction(e -> {
            org.example.carrental.view.MyAccountView myAccountView = new org.example.carrental.view.MyAccountView();
            new org.example.carrental.controller.MyAccountController(myAccountView, authService, carService);
            AppNavigator.getInstance().navigateTo(myAccountView);
        });

        view.getTopBar().getWorkerButton().setOnAction(e -> {
            WorkerView workerView = new WorkerView();
            new WorkerController(workerView, carService, authService);
            AppNavigator.getInstance().navigateTo(workerView);
        });

        view.getTopBar().getAdminButton().setOnAction(e -> {
            AdminView adminView = new AdminView();
            new AdminController(adminView, authService);
            AppNavigator.getInstance().navigateTo(adminView);
        });

        view.getTopBar().getLogoutButton().setOnAction(e -> logout());

        // Search functionality
        view.getTopBar().getSearchField().setOnAction(e -> performSearch());
    }

    private void loadCars() {
        view.clearCarList();
        List<Car> cars = carService.getAllCars();
        displayCars(cars);
    }

    private void displayCars(List<Car> cars) {
        view.clearCarList();

        if (cars.isEmpty()) {
            // No cars message - won't normally happen due to seed data
            return;
        }

        boolean isEndUser = Session.getInstance().isEndUser();

        for (Car car : cars) {
            CarCard carCard = new CarCard(car, isEndUser, false);

            if (isEndUser && carCard.getBookButton() != null) {
                carCard.getBookButton().setOnAction(e -> {
                    BookingView bookingView = new BookingView(car);
                    new BookingController(bookingView, carService, authService);
                    AppNavigator.getInstance().navigateTo(bookingView);
                });
            }

            view.getCarListContainer().getChildren().add(carCard);
        }
    }

    private void performSearch() {
        String query = view.getTopBar().getSearchField().getText();
        List<Car> cars = carService.searchCars(query);
        displayCars(cars);
    }

    private void logout() {
        authService.logout();
        Dialogs.showInfo("Logged Out", "You have been logged out successfully.");
        refresh();
    }

    private void refresh() {
        MainView newView = new MainView();
        new MainController(newView, carService, authService);
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