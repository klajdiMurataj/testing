//Eden Pajo
package org.example.carrental.service;

import org.example.carrental.model.booking.Booking;
import org.example.carrental.model.booking.Invoice;
import org.example.carrental.model.common.Credential;
import org.example.carrental.model.common.DateRange;
import org.example.carrental.model.enums.CarCategory;
import org.example.carrental.model.enums.Role;
import org.example.carrental.model.users.Account;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.storage.repositories.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    private BookingService bookingService;
    private BookingRepository bookingRepository;

    private String testCarId;
    private Car testCar;

    @BeforeEach
    void setUp() {
        bookingRepository = new BookingRepository();
        testCarId = "CAR_TEST_" + System.nanoTime();

        PricingService pricingService = new PricingService();

        testCar = new Car(
                testCarId, "Toyota", "Corolla", 2020,
                "AA123BB", 100.0, true,
                CarCategory.ECONOMY, 5, "Automatic",
                "Petrol", "photo.png"
        );

        CarService carService = new FakeCarService(testCar);
        InvoiceService invoiceService = new FakeInvoiceService();

        bookingService = new BookingService(
                bookingRepository,
                carService,
                pricingService,
                invoiceService
        );
    }

    @Test
    void createBooking_createsBookingSuccessfully() {
        Account user = new Account(
                "USER1", "John", "Doe", "john@doe.com", "123",
                new Credential("john", new byte[]{1}, new byte[]{2}),
                Role.END_USER
        ) {};
        LocalDate start = LocalDate.now().plusDays(1000);
        DateRange range = new DateRange(start, start.plusDays(2));

        Booking booking = bookingService.createBooking(testCarId, range, user);

        assertNotNull(booking);
        assertEquals(testCarId, booking.getCarId());
        assertEquals("USER1", booking.getUserId());
        assertNotNull(booking.getInvoiceId());
    }
    static class FakeCarService extends CarService {
        private final Car car;

        FakeCarService(Car car) {
            super(null);
            this.car = car;
        }

        @Override
        public Optional<Car> getCarById(String carId) {
            return car.getId().equals(carId) ? Optional.of(car) : Optional.empty();
        }
    }

    static class FakeInvoiceService extends InvoiceService {
        FakeInvoiceService() {
            super(null);
        }

        @Override
        public Invoice createInvoice(Booking booking, Account user, Car car) {
            return new Invoice(
                    "INV_TEST",
                    booking.getId(),
                    user.getId(),
                    user.getFullName(),
                    user.getEmail(),
                    car.getDisplayName(),
                    booking.getDateRange().toString(),
                    booking.getPriceBreakdown(),
                    LocalDateTime.now(),
                    null
            );
        }
    }
}

