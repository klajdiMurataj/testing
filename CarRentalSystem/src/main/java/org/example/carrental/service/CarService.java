package org.example.carrental.service;

import org.example.carrental.model.enums.CarCategory;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.storage.StoragePaths;
import org.example.carrental.storage.repositories.CarRepository;
import org.example.carrental.util.IdGenerator;
import org.example.carrental.util.Validators;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing cars
 */
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Adds a new car to the system
     */
    public boolean addCar(String brand, String model, int year, String licensePlate,
                          double dailyRate, CarCategory category, int seats,
                          String transmission, String fuelType, File photoFile) {
        // Validate inputs
        if (!Validators.isNotEmpty(brand) || !Validators.isNotEmpty(model)) {
            throw new IllegalArgumentException("Brand and model are required");
        }

        if (!Validators.isValidYear(year)) {
            throw new IllegalArgumentException("Invalid year");
        }

        if (!Validators.isValidLicensePlate(licensePlate)) {
            throw new IllegalArgumentException("Invalid license plate format");
        }

        if (!Validators.isPositive(dailyRate)) {
            throw new IllegalArgumentException("Daily rate must be positive");
        }

        if (seats < 2 || seats > 9) {
            throw new IllegalArgumentException("Seats must be between 2 and 9");
        }

        // Check if license plate already exists
        if (carRepository.existsByLicensePlate(licensePlate)) {
            throw new IllegalArgumentException("License plate already exists");
        }

        // Handle photo upload
        String photoPath = null;
        if (photoFile != null) {
            photoPath = saveCarPhoto(photoFile);
        }

        // Create car
        Car car = new Car(
                IdGenerator.generateId("CAR"),
                brand,
                model,
                year,
                licensePlate.toUpperCase(),
                dailyRate,
                true, // Available by default
                category,
                seats,
                transmission,
                fuelType,
                photoPath
        );

        carRepository.add(car);
        return true;
    }

    /**
     * Saves a car photo to the photos directory
     */
    private String saveCarPhoto(File sourceFile) {
        try {
            String fileName = System.currentTimeMillis() + "_" + sourceFile.getName();
            Path destinationPath = StoragePaths.PHOTOS_DIR.resolve(fileName);

            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            return destinationPath.toString();
        } catch (IOException e) {
            System.err.println("Error saving photo: " + e.getMessage());
            return null;
        }
    }

    /**
     * Removes a car from the system
     */
    public boolean removeCar(String carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty()) {
            throw new IllegalArgumentException("Car not found");
        }

        // Delete photo if it exists
        Car car = carOpt.get();
        if (car.getPhotoPath() != null) {
            try {
                Files.deleteIfExists(Path.of(car.getPhotoPath()));
            } catch (IOException e) {
                System.err.println("Error deleting photo: " + e.getMessage());
            }
        }

        return carRepository.remove(carId);
    }

    /**
     * Updates car availability
     */
    public void setCarAvailability(String carId, boolean available) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isEmpty()) {
            throw new IllegalArgumentException("Car not found");
        }

        Car car = carOpt.get();
        car.setAvailable(available);
        carRepository.update(car);
    }

    /**
     * Gets all cars
     */
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Gets all available cars
     */
    public List<Car> getAvailableCars() {
        return carRepository.findAvailable();
    }

    /**
     * Searches cars by brand or model
     */
    public List<Car> searchCars(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllCars();
        }
        return carRepository.search(query.trim());
    }

    /**
     * Gets a car by ID
     */
    public Optional<Car> getCarById(String carId) {
        return carRepository.findById(carId);
    }

    /**
     * Gets cars by category
     */
    public List<Car> getCarsByCategory(CarCategory category) {
        return carRepository.findByCategory(category);
    }
}