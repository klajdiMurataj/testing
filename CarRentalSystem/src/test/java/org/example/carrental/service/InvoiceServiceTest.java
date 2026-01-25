//Eden Pajo
package org.example.carrental.service;

import org.example.carrental.model.booking.Booking;
import org.example.carrental.model.booking.Invoice;
import org.example.carrental.model.booking.PriceBreakdown;
import org.example.carrental.model.common.Credential;
import org.example.carrental.model.common.DateRange;
import org.example.carrental.model.enums.BookingStatus;
import org.example.carrental.model.enums.CarCategory;
import org.example.carrental.model.enums.Role;
import org.example.carrental.model.users.Account;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.storage.repositories.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceServiceTest {

    private InvoiceService invoiceService;
    private InvoiceRepository invoiceRepository;

    @BeforeEach
    void setUp() {
        // REAL repo (no Mockito)
        invoiceRepository = new InvoiceRepository();
        invoiceService = new InvoiceService(invoiceRepository);
    }

    @Test
    void createInvoice_createsInvoiceSuccessfully_andCanReadContent() {

        // Arrange
        Account user = new Account(
                "USER1", "John", "Doe", "john@doe.com", "123",
                new Credential("john", new byte[]{1}, new byte[]{2}),
                Role.END_USER
        ) {};

        Car car = new Car(
                "CAR1", "Toyota", "Corolla", 2020,
                "AA123BB", 100.0, true,
                CarCategory.ECONOMY, 5, "Automatic",
                "Petrol", "photo.png"
        );

        DateRange range = new DateRange(LocalDate.now(), LocalDate.now().plusDays(2));
        PriceBreakdown pb = new PriceBreakdown(300.0, 60.0, 360.0);

        Booking booking = new Booking(
                "BOOK1",
                user.getId(),
                car.getId(),
                range,
                pb,
                BookingStatus.CONFIRMED,
                LocalDateTime.now(),
                null
        );

        // Act
        Invoice invoice = invoiceService.createInvoice(booking, user, car);

        // Assert
        assertNotNull(invoice);
        assertNotNull(invoice.getId());
        assertEquals("BOOK1", invoice.getBookingId());

        Optional<String> content = invoiceService.getInvoiceContent(invoice.getId());
        assertTrue(content.isPresent()); 
        invoiceService.deleteInvoice(invoice.getId());
    }
}

