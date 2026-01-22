package org.example.carrental.storage.repositories;

import org.example.carrental.model.booking.Booking;
import org.example.carrental.model.enums.BookingStatus;
import org.example.carrental.storage.BinaryDataStore;
import org.example.carrental.storage.DataStore;
import org.example.carrental.storage.StoragePaths;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository for managing bookings
 */
public class BookingRepository {

    private final DataStore<Booking> dataStore;
    private List<Booking> bookings;

    public BookingRepository() {
        this.dataStore = new BinaryDataStore<>(StoragePaths.BOOKINGS_FILE);
        this.bookings = new ArrayList<>();
        load();
    }

    /**
     * Loads all bookings from storage
     */
    public void load() {
        this.bookings = dataStore.load();
    }

    /**
     * Saves all bookings to storage
     */
    public void save() {
        dataStore.save(bookings);
    }

    /**
     * Adds a new booking
     */
    public void add(Booking booking) {
        bookings.add(booking);
        save();
    }

    /**
     * Removes a booking by ID
     */
    public boolean remove(String bookingId) {
        boolean removed = bookings.removeIf(booking -> booking.getId().equals(bookingId));
        if (removed) {
            save();
        }
        return removed;
    }

    /**
     * Updates an existing booking
     */
    public void update(Booking updatedBooking) {
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getId().equals(updatedBooking.getId())) {
                bookings.set(i, updatedBooking);
                save();
                return;
            }
        }
    }

    /**
     * Finds a booking by ID
     */
    public Optional<Booking> findById(String bookingId) {
        return bookings.stream()
                .filter(booking -> booking.getId().equals(bookingId))
                .findFirst();
    }

    /**
     * Finds all bookings for a user
     */
    public List<Booking> findByUserId(String userId) {
        return bookings.stream()
                .filter(booking -> booking.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    /**
     * Finds all bookings for a car
     */
    public List<Booking> findByCarId(String carId) {
        return bookings.stream()
                .filter(booking -> booking.getCarId().equals(carId))
                .collect(Collectors.toList());
    }

    /**
     * Finds bookings by status
     */
    public List<Booking> findByStatus(BookingStatus status) {
        return bookings.stream()
                .filter(booking -> booking.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Gets all bookings
     */
    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }

    /**
     * Gets the total number of bookings
     */
    public int count() {
        return bookings.size();
    }

    /**
     * Clears all bookings
     */
    public void clear() {
        bookings.clear();
        dataStore.clear();
    }
}