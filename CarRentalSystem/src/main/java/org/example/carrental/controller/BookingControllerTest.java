package org.example.carrental.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import org.example.carrental.model.booking.PriceBreakdown;
import org.example.carrental.model.vehicles.Car;
import org.example.carrental.service.PricingService;
import org.example.carrental.view.BookingView;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookingControllerTest {

    @Mock
    private BookingView view;

    @Mock
    private PricingService pricingService;

    @Mock
    private Car selectedCar;

    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private Label totalLabel;
    private Button confirmButton;

    private BookingController controller;

    // ---- JavaFX bootstrap (ONCE) ----
    @BeforeAll
    static void initJavaFx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // JavaFX already started
        }
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // REAL JavaFX controls (no rendering)
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        totalLabel = new Label();
        confirmButton = new Button();

        // Stub view to return controls
        when(view.getStartDatePicker()).thenReturn(startDatePicker);
        when(view.getEndDatePicker()).thenReturn(endDatePicker);
        when(view.getTotalLabel()).thenReturn(totalLabel);
        when(view.getConfirmButton()).thenReturn(confirmButton);
        when(view.getSelectedCar()).thenReturn(selectedCar);

        controller = new BookingController(view, pricingService);
    }

    @Test
    void updatePrice_startDateOrEndDateNull() {
        startDatePicker.setValue(null);
        endDatePicker.setValue(LocalDate.now());

        controller.updatePrice();

        assertEquals("Select dates to see price", totalLabel.getText());
        assertTrue(confirmButton.isDisable());
    }

    @Test
    void updatePrice_startDateInPast() {
        startDatePicker.setValue(LocalDate.now().minusDays(1));
        endDatePicker.setValue(LocalDate.now().plusDays(2));

        controller.updatePrice();

        assertEquals("Start date cannot be in the past", totalLabel.getText());
        assertTrue(confirmButton.isDisable());
    }

    @Test
    void updatePrice_endDateBeforeStartDate() {
        startDatePicker.setValue(LocalDate.now().plusDays(5));
        endDatePicker.setValue(LocalDate.now().plusDays(2));

        controller.updatePrice();

        assertEquals("End date must be after start date", totalLabel.getText());
        assertTrue(confirmButton.isDisable());
    }

    @Test
    void updatePrice_endDateNull() {
        startDatePicker.setValue(LocalDate.now().plusDays(1));
        endDatePicker.setValue(null);

        controller.updatePrice();

        assertEquals("Select dates to see price", totalLabel.getText());
        assertTrue(confirmButton.isDisable());
    }

    @Test
    void updatePrice_validDates_success() {
        startDatePicker.setValue(LocalDate.now().plusDays(1));
        endDatePicker.setValue(LocalDate.now().plusDays(5));

        PriceBreakdown breakdown = mock(PriceBreakdown.class);
        when(breakdown.getTotalPrice()).thenReturn(500.0);
        when(pricingService.calculatePrice(any(), any()))
                .thenReturn(breakdown);

        controller.updatePrice();

        assertEquals("500.0", totalLabel.getText());
        assertFalse(confirmButton.isDisable());
    }

    @Test
    void updatePrice_exceptionThrown() {
        startDatePicker.setValue(LocalDate.now().plusDays(1));
        endDatePicker.setValue(LocalDate.now().plusDays(5));

        when(pricingService.calculatePrice(any(), any()))
                .thenThrow(new RuntimeException("Boom"));

        controller.updatePrice();

        assertEquals("Error calculating price", totalLabel.getText());
        assertTrue(confirmButton.isDisable());
    }
}
