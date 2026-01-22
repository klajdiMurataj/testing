package org.example.carrental.model.vehicles;

import org.example.carrental.model.enums.CarCategory;

/**
 * Represents a car available for rental
 */
public class Car extends Vehicle {
    private static final long serialVersionUID = 1L;

    private CarCategory category;
    private int seats;
    private String transmission; // "Automatic" or "Manual"
    private String fuelType; // "Petrol", "Diesel", "Electric", "Hybrid"
    private String photoPath; // Path to car photo

    public Car() {
        super();
    }

    public Car(String id, String brand, String model, int year,
               String licensePlate, double dailyRate, boolean available,
               CarCategory category, int seats, String transmission,
               String fuelType, String photoPath) {
        super(id, brand, model, year, licensePlate, dailyRate, available);
        this.category = category;
        this.seats = seats;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.photoPath = photoPath;
    }

    public CarCategory getCategory() {
        return category;
    }

    public void setCategory(CarCategory category) {
        this.category = category;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + getId() + '\'' +
                ", brand='" + getBrand() + '\'' +
                ", model='" + getModel() + '\'' +
                ", year=" + getYear() +
                ", category=" + category +
                ", seats=" + seats +
                ", transmission='" + transmission + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", dailyRate=" + getDailyRate() +
                ", available=" + isAvailable() +
                '}';
    }
}