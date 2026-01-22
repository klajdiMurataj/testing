package org.example.carrental.model.booking;

import org.example.carrental.model.common.Identifiable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an invoice for a booking
 */
public class Invoice implements Identifiable, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String bookingId;
    private String userId;
    private String userFullName;
    private String userEmail;
    private String carDetails;       // e.g., "2023 Toyota Camry"
    private String rentalPeriod;     // e.g., "2024-06-01 to 2024-06-05"
    private PriceBreakdown priceBreakdown;
    private LocalDateTime issuedAt;
    private String filePath;         // Path to the .txt invoice file

    public Invoice() {
    }

    public Invoice(String id, String bookingId, String userId, String userFullName,
                   String userEmail, String carDetails, String rentalPeriod,
                   PriceBreakdown priceBreakdown, LocalDateTime issuedAt, String filePath) {
        this.id = id;
        this.bookingId = bookingId;
        this.userId = userId;
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.carDetails = carDetails;
        this.rentalPeriod = rentalPeriod;
        this.priceBreakdown = priceBreakdown;
        this.issuedAt = issuedAt;
        this.filePath = filePath;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(String carDetails) {
        this.carDetails = carDetails;
    }

    public String getRentalPeriod() {
        return rentalPeriod;
    }

    public void setRentalPeriod(String rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }

    public PriceBreakdown getPriceBreakdown() {
        return priceBreakdown;
    }

    public void setPriceBreakdown(PriceBreakdown priceBreakdown) {
        this.priceBreakdown = priceBreakdown;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", customer='" + userFullName + '\'' +
                ", car='" + carDetails + '\'' +
                ", total=" + priceBreakdown.getTotalPrice() +
                '}';
    }
}