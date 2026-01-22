package org.example.carrental;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.carrental.controller.MainController;
import org.example.carrental.service.AuthService;
import org.example.carrental.service.CarService;
import org.example.carrental.storage.StoragePaths;
import org.example.carrental.storage.bootstrap.SeedData;
import org.example.carrental.storage.repositories.CarRepository;
import org.example.carrental.storage.repositories.UserRepository;
import org.example.carrental.view.MainView;

/**
 * Main JavaFX Application class
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Ensure data directories exist
            StoragePaths.ensureDirectoriesExist();

            // Initialize repositories
            UserRepository userRepository = new UserRepository();
            CarRepository carRepository = new CarRepository();

            // Seed initial data
            SeedData seedData = new SeedData(userRepository, carRepository);
            seedData.seedIfEmpty();

            // Initialize services
            AuthService authService = new AuthService(userRepository);
            CarService carService = new CarService(carRepository);

            // Initialize navigator
            AppNavigator navigator = AppNavigator.getInstance();
            navigator.init(primaryStage);

            // Create and show main view
            MainView mainView = new MainView();
            new MainController(mainView, carService, authService);
            navigator.navigateTo(mainView);

            // Configure stage
            primaryStage.setTitle("Car Rental System - developed by Thomas Kroj, Eden Pajo");
            primaryStage.setMinWidth(1000);
            primaryStage.setMinHeight(700);
            primaryStage.show();

            System.out.println("=".repeat(60));
            System.out.println("Car Rental System Started Successfully");
            System.out.println("=".repeat(60));
            System.out.println("Default Admin Credentials:");
            System.out.println("  Username: admin");
            System.out.println("  Password: admin123");
            System.out.println("=".repeat(60));

        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}