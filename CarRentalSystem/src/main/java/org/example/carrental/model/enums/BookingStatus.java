package org.example.carrental.model.enums;

/**
 * Booking status enumeration
 */
public enum BookingStatus {
    PENDING,      // Booking created, awaiting confirmation
    CONFIRMED,    // Booking confirmed
    ACTIVE,       // Car currently rented
    COMPLETED,    // Rental completed
    CANCELLED     // Booking cancelled
}