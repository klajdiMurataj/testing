package org.example.carrental.integration;

import org.example.carrental.model.Car;
import org.example.carrental.model.User;
import org.example.carrental.model.Booking;
import org.example.carrental.service.BookingService;
import org.example.carrental.service.CarService;
import org.example.carrental.service.PricingService;
import org.example.carrental.repository.BookingRepository;
import org.example.carrental.repository.CarRepository;
import org.example.carrental.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
public class tets {
    private CarService carService;
    private BookingService bookingService;
    private PricingService pricingService;

    @BeforeEach
    void setUp() {
        // clear repositories so tests don't affect each other
        CarRepository.getInstance().clear();
        UserRepository.getInstance().clear();
        BookingRepository.getInstance().clear();

        carService = new CarService();
        pricingService = new PricingService();
        bookingService = new BookingService();
    }

    @Test
    void testCreateBooking_andPriceCalculation() {
        Car car = new Car("BMW", "X5", 100);
        User user = new User("John", "Doe");

        carService.addCar(car);
        UserRepository.getInstance().save(user);

        Booking booking = bookingService.createBooking(
                user.getId(),
                car.getId(),
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );

        assertNotNull(booking);
        assertEquals(3, booking.getDays());

        double expectedPrice = pricingService.calculatePrice(car, 3);
        assertEquals(expectedPrice, booking.getTotalPrice());
    }

    @Test
    void testOverlappingBookingRejected() {
        Car car = new Car("Audi", "A6", 80);
        User user = new User("Alice", "Smith");

        carService.addCar(car);
        UserRepository.getInstance().save(user);

        bookingService.createBooking(
                user.getId(),
                car.getId(),
                LocalDate.now(),
                LocalDate.now().plusDays(2)
        );

        assertThrows(IllegalArgumentException.class, () ->
                bookingService.createBooking(
                        user.getId(),
                        car.getId(),
                        LocalDate.now().plusDays(1),
                        LocalDate.now().plusDays(3)
                )
        );
    }
}
