package org.example.carrental.model.vehicles;

import org.example.carrental.model.common.Identifiable;

import java.io.Serializable;

/**
 * Abstract base class for all vehicles
 */
public abstract class Vehicle implements Identifiable, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String brand;
    private String model;
    private int year;
    private String licensePlate;
    private double dailyRate;
    private boolean available;

    public Vehicle() {
    }

    public Vehicle(String id, String brand, String model, int year,
                   String licensePlate, double dailyRate, boolean available) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.dailyRate = dailyRate;
        this.available = available;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDisplayName() {
        return year + " " + brand + " " + model;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", licensePlate='" + licensePlate + '\'' +
                ", dailyRate=" + dailyRate +
                ", available=" + available +
                '}';
    }
}