package org.example.carrental.model.booking;

import java.io.Serializable;

/**
 * Represents the price breakdown for a booking
 */
public class PriceBreakdown implements Serializable {
    private static final long serialVersionUID = 1L;

    private double basePrice;      // Daily rate × number of days
    private double vatAmount;      // VAT/Tax amount
    private double totalPrice;     // Final price including VAT

    public PriceBreakdown() {
    }

    public PriceBreakdown(double basePrice, double vatAmount, double totalPrice) {
        this.basePrice = basePrice;
        this.vatAmount = vatAmount;
        this.totalPrice = totalPrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return String.format("Base: €%.2f | VAT: €%.2f | Total: €%.2f",
                basePrice, vatAmount, totalPrice);
    }
}