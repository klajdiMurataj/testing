package org.example.carrental.storage.repositories;

import org.example.carrental.model.enums.CarCategory;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.storage.BinaryDataStore;
import org.example.carrental.storage.DataStore;
import org.example.carrental.storage.StoragePaths;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository for managing cars
 */
public class CarRepository {

    private final DataStore<Car> dataStore;
    private List<Car> cars;

    public CarRepository() {
        this.dataStore = new BinaryDataStore<>(StoragePaths.CARS_FILE);
        this.cars = new ArrayList<>();
        load();
    }

    /**
     * Loads all cars from storage
     */
    public void load() {
        this.cars = dataStore.load();
    }

    /**
     * Saves all cars to storage
     */
    public void save() {
        dataStore.save(cars);
    }

    /**
     * Adds a new car
     */
    public void add(Car car) {
        cars.add(car);
        save();
    }

    /**
     * Removes a car by ID
     */
    public boolean remove(String carId) {
        boolean removed = cars.removeIf(car -> car.getId().equals(carId));
        if (removed) {
            save();
        }
        return removed;
    }

    /**
     * Updates an existing car
     */
    public void update(Car updatedCar) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getId().equals(updatedCar.getId())) {
                cars.set(i, updatedCar);
                save();
                return;
            }
        }
    }

    /**
     * Finds a car by ID
     */
    public Optional<Car> findById(String carId) {
        return cars.stream()
                .filter(car -> car.getId().equals(carId))
                .findFirst();
    }

    /**
     * Finds cars by brand
     */
    public List<Car> findByBrand(String brand) {
        return cars.stream()
                .filter(car -> car.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    /**
     * Finds cars by model
     */
    public List<Car> findByModel(String model) {
        return cars.stream()
                .filter(car -> car.getModel().toLowerCase().contains(model.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Finds cars by category
     */
    public List<Car> findByCategory(CarCategory category) {
        return cars.stream()
                .filter(car -> car.getCategory() == category)
                .collect(Collectors.toList());
    }

    /**
     * Finds available cars
     */
    public List<Car> findAvailable() {
        return cars.stream()
                .filter(Car::isAvailable)
                .collect(Collectors.toList());
    }

    /**
     * Searches cars by brand or model
     */
    public List<Car> search(String query) {
        String lowerQuery = query.toLowerCase();
        return cars.stream()
                .filter(car -> car.getBrand().toLowerCase().contains(lowerQuery) ||
                        car.getModel().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    /**
     * Gets all cars
     */
    public List<Car> findAll() {
        return new ArrayList<>(cars);
    }

    /**
     * Checks if a license plate already exists
     */
    public boolean existsByLicensePlate(String licensePlate) {
        return cars.stream()
                .anyMatch(car -> car.getLicensePlate().equalsIgnoreCase(licensePlate));
    }

    /**
     * Gets the total number of cars
     */
    public int count() {
        return cars.size();
    }

    /**
     * Clears all cars
     */
    public void clear() {
        cars.clear();
        dataStore.clear();
    }
}