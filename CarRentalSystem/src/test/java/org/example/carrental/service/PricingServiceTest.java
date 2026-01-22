//Eden Pajo
package org.example.carrental.service;

import org.example.carrental.model.booking.PriceBreakdown;
import org.example.carrental.model.common.DateRange;
import org.example.carrental.model.enums.CarCategory;
import org.example.carrental.model.vehicles.Car;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PricingServiceTest {

    @Test
    void calculateVat_positive() {
        PricingService pricingService = new PricingService();
        assertEquals(20.0, pricingService.calculateVat(100.0), 1e-9);
    }

    @Test
    void calculateVat_negative_documentsBehavior() {
        PricingService pricingService = new PricingService();
        assertEquals(-0.2, pricingService.calculateVat(-1.0), 1e-9);
    }

    @Test
    void calculatePrice_returnsCorrectBreakdown() {
        PricingService pricingService = new PricingService();

        // Arrange
        Car car = new Car(
                "CAR1", "Toyota", "Corolla", 2020,
                "AA123BB", 100.0, true,
                CarCategory.ECONOMY, 5, "Automatic",
                "Petrol", "photo.png"
        );

        DateRange range = new DateRange(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 3) // 3 days because +1 in getDays()
        );

        // Act
        PriceBreakdown pb = pricingService.calculatePrice(car, range);

        // Assert
        assertEquals(300.0, pb.getBasePrice(), 1e-9);   // 100 * 3
        assertEquals(60.0, pb.getVatAmount(), 1e-9);    // 20% of 300
        assertEquals(360.0, pb.getTotalPrice(), 1e-9);  // 300 + 60
    }
}
