package org.example.carrental.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.carrental.model.booking.PriceBreakdown;
import org.example.carrental.model.common.DateRange;
import org.example.carrental.model.vehicles.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PricingServiceUnitTest {

    private PricingService pricingService;

    @BeforeEach
    void setup() {
        pricingService = new PricingService();
    }

    // UT01 – calculateBasePrice normal
    @Test
    void testCalculateBasePriceNormal() {
        double result = pricingService.calculateBasePrice(100, 3);
        assertEquals(300, result);
    }

    // UT02 – calculateBasePrice zero values
    @Test
    void testCalculateBasePriceZero() {
        double result = pricingService.calculateBasePrice(0, 0);
        assertEquals(0, result);
    }

    // UT03 – calculateBasePrice negative rate
    @Test
    void testCalculateBasePriceNegative() {
        double result = pricingService.calculateBasePrice(-50, 3);
        assertEquals(-150, result);
    }

    // UT04 – calculateVat normal
    @Test
    void testCalculateVatNormal() {
        double result = pricingService.calculateVat(100);
        assertEquals(20, result);
    }

    // UT05 – calculateVat zero
    @Test
    void testCalculateVatZero() {
        double result = pricingService.calculateVat(0);
        assertEquals(0, result);
    }

    // UT06 – calculateVat negative
    @Test
    void testCalculateVatNegative() {
        double result = pricingService.calculateVat(-100);
        assertEquals(-20, result);
    }

    // UT07 – calculateTotal normal
    @Test
    void testCalculateTotalNormal() {
        double result = pricingService.calculateTotal(100);
        assertEquals(120, result);
    }

    // UT08 – calculateTotal zero
    @Test
    void testCalculateTotalZero() {
        double result = pricingService.calculateTotal(0);
        assertEquals(0, result);
    }

    // UT09 – calculateTotal negative
    @Test
    void testCalculateTotalNegative() {
        double result = pricingService.calculateTotal(-50);
        assertEquals(-60, result);
    }

    // UT12 – getVatRatePercentage
    @Test
    void testGetVatRatePercentage() {
        double result = pricingService.getVatRatePercentage();
        assertEquals(20, result);
    }
}
