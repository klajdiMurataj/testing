package org.example.carrental.service;

import org.example.carrental.model.booking.PriceBreakdown;
import org.example.carrental.model.common.DateRange;
import org.example.carrental.model.vehicles.Car;

/**
 * Service for calculating rental prices
 */
public class PricingService {

    private static final double VAT_RATE = 0.20; // 20% VAT

    /**
     * Calculates the price breakdown for a rental
     */
    public PriceBreakdown calculatePrice(Car car, DateRange dateRange) {
        long days = dateRange.getDays();
        double dailyRate = car.getDailyRate();

        // Calculate base price
        double basePrice = dailyRate * days;

        // Calculate VAT
        double vatAmount = basePrice * VAT_RATE;

        // Calculate total
        double totalPrice = basePrice + vatAmount;

        return new PriceBreakdown(basePrice, vatAmount, totalPrice);
    }

    /**
     * Gets the VAT rate as a percentage
     */
    public double getVatRatePercentage() {
        return VAT_RATE * 100;
    }

    /**
     * Calculates base price only (without VAT)
     */
    public double calculateBasePrice(double dailyRate, long days) {
        return dailyRate * days;
    }

    /**
     * Calculates VAT amount
     */
    public double calculateVat(double basePrice) {
        return basePrice * VAT_RATE;
    }

    /**
     * Calculates total price including VAT
     */
    public double calculateTotal(double basePrice) {
        return basePrice + calculateVat(basePrice);
    }
}