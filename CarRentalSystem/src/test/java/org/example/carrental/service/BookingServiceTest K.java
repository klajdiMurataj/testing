package org.example.booking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.example.carrental.model.booking.Booking;
import org.example.carrental.model.enums.BookingStatus;
import org.example.carrental.service.BookingService;
import org.example.carrental.service.CarService;
import org.example.carrental.service.InvoiceService;
import org.example.carrental.service.PricingService;
import org.example.carrental.storage.repositories.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CarService carService;

    @Mock
    private PricingService pricingService;

    @Mock
    private InvoiceService invoiceService;

    private BookingService bookingService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        bookingService = new BookingService(bookingRepository, carService, pricingService, invoiceService);
    }

    @Test
    void cancelBooking_bookingNotFound_throwsException() {
        String bookingId = "B001";
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bookingService.cancelBooking(bookingId)
        );

        assertEquals("Booking not found", ex.getMessage());
    }

    @Test
    void cancelBooking_alreadyCancelled_throwsException() {
        String bookingId = "B002";
        Booking booking = mock(Booking.class);
        when(booking.getStatus()).thenReturn(BookingStatus.CANCELLED);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bookingService.cancelBooking(bookingId)
        );

        assertEquals("Booking is already cancelled", ex.getMessage());
    }

    @Test
    void cancelBooking_completedBooking_throwsException() {
        String bookingId = "B003";
        Booking booking = mock(Booking.class);
        when(booking.getStatus()).thenReturn(BookingStatus.COMPLETED);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                bookingService.cancelBooking(bookingId)
        );

        assertEquals("Cannot cancel completed booking", ex.getMessage());
    }

    @Test
    void cancelBooking_validBooking_returnsTrue() {
        String bookingId = "B004";
        Booking booking = mock(Booking.class);
        when(booking.getStatus()).thenReturn(BookingStatus.CONFIRMED);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        boolean result = bookingService.cancelBooking(bookingId);

        assertTrue(result);
        verify(booking).setStatus(BookingStatus.CANCELLED);
        verify(bookingRepository).update(booking);
    }
}
