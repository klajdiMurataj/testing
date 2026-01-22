package org.example.carrental.model.booking;

import org.example.carrental.model.common.DateRange;
import org.example.carrental.model.common.Identifiable;
import org.example.carrental.model.enums.BookingStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a car rental booking
 */
public class Booking implements Identifiable, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String userId;           // ID of the customer who made the booking
    private String carId;            // ID of the booked car
    private DateRange dateRange;     // Rental period
    private PriceBreakdown priceBreakdown;
    private BookingStatus status;
    private LocalDateTime createdAt;
    private String invoiceId;        // Reference to invoice

    public Booking() {
    }

    public Booking(String id, String userId, String carId, DateRange dateRange,
                   PriceBreakdown priceBreakdown, BookingStatus status,
                   LocalDateTime createdAt, String invoiceId) {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.dateRange = dateRange;
        this.priceBreakdown = priceBreakdown;
        this.status = status;
        this.createdAt = createdAt;
        this.invoiceId = invoiceId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public PriceBreakdown getPriceBreakdown() {
        return priceBreakdown;
    }

    public void setPriceBreakdown(PriceBreakdown priceBreakdown) {
        this.priceBreakdown = priceBreakdown;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", carId='" + carId + '\'' +
                ", dateRange=" + dateRange +
                ", status=" + status +
                ", total=" + priceBreakdown.getTotalPrice() +
                '}';
    }
}