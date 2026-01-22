package org.example.carrental.service;

import org.example.carrental.model.booking.Booking;
import org.example.carrental.model.booking.Invoice;
import org.example.carrental.model.booking.PriceBreakdown;
import org.example.carrental.model.common.DateRange;
import org.example.carrental.model.enums.BookingStatus;
import org.example.carrental.model.users.Account;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.storage.repositories.BookingRepository;
import org.example.carrental.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing bookings
 */
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CarService carService;
    private final PricingService pricingService;
    private final InvoiceService invoiceService;

    public BookingService(BookingRepository bookingRepository,
                          CarService carService,
                          PricingService pricingService,
                          InvoiceService invoiceService) {
        this.bookingRepository = bookingRepository;
        this.carService = carService;
        this.pricingService = pricingService;
        this.invoiceService = invoiceService;
    }

    /**
     * Creates a new booking
     */
    public Booking createBooking(String carId, DateRange dateRange, Account user) {
        // Validate car exists and is available
        Optional<Car> carOpt = carService.getCarById(carId);
        if (carOpt.isEmpty()) {
            throw new IllegalArgumentException("Car not found");
        }

        Car car = carOpt.get();
        if (!car.isAvailable()) {
            throw new IllegalArgumentException("Car is not available");
        }

        // Check for overlapping bookings
        if (hasOverlappingBooking(carId, dateRange)) {
            throw new IllegalArgumentException("Car is already booked for the selected dates");
        }

        // Calculate price
        PriceBreakdown priceBreakdown = pricingService.calculatePrice(car, dateRange);

        // Create booking
        String bookingId = IdGenerator.generateId("BOOK");
        Booking booking = new Booking(
                bookingId,
                user.getId(),
                carId,
                dateRange,
                priceBreakdown,
                BookingStatus.CONFIRMED,
                LocalDateTime.now(),
                null // Invoice ID will be set after invoice creation
        );

        // Save booking
        bookingRepository.add(booking);

        // Generate invoice
        Invoice invoice = invoiceService.createInvoice(booking, user, car);

        // Update booking with invoice ID
        booking.setInvoiceId(invoice.getId());
        bookingRepository.update(booking);

        // Mark car as unavailable (optional, depending on business logic)
        // carService.setCarAvailability(carId, false);

        return booking;
    }

    /**
     * Checks if a car has overlapping bookings for the given date range
     */
    private boolean hasOverlappingBooking(String carId, DateRange dateRange) {
        List<Booking> carBookings = bookingRepository.findByCarId(carId);

        return carBookings.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.CONFIRMED ||
                        booking.getStatus() == BookingStatus.ACTIVE)
                .anyMatch(booking -> booking.getDateRange().overlaps(dateRange));
    }

    /**
     * Gets all bookings for a user
     */
    public List<Booking> getUserBookings(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    /**
     * Gets all bookings
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Gets a booking by ID
     */
    public Optional<Booking> getBookingById(String bookingId) {
        return bookingRepository.findById(bookingId);
    }

    /**
     * Cancels a booking
     */
    public boolean cancelBooking(String bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking not found");
        }

        Booking booking = bookingOpt.get();

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Booking is already cancelled");
        }

        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new IllegalArgumentException("Cannot cancel completed booking");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.update(booking);

        // Optionally make car available again
        // carService.setCarAvailability(booking.getCarId(), true);

        return true;
    }

    /**
     * Updates booking status
     */
    public void updateBookingStatus(String bookingId, BookingStatus status) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking not found");
        }

        Booking booking = bookingOpt.get();
        booking.setStatus(status);
        bookingRepository.update(booking);
    }
}