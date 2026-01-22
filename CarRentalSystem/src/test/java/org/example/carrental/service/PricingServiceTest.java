//THOMAS KROJ
package org.example.carrental.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    @Test
    void PS_1_normalCase() {
        PricingService ps = new PricingService();
        assertEquals(10.0, ps.calculateBasePrice(10.0, 1), 1e-9);
    }

    @Test
    void PS_2_daysZero() {
        PricingService ps = new PricingService();
        assertEquals(0.0, ps.calculateBasePrice(10.0, 0), 1e-9);
    }

    @Test
    void PS_3_rateZero() {
        PricingService ps = new PricingService();
        assertEquals(0.0, ps.calculateBasePrice(0.0, 5), 1e-9);
    }

    @Test
    void PS_4_negativeRate() {
        PricingService ps = new PricingService();
        assertEquals(-20.0, ps.calculateBasePrice(-10.0, 2), 1e-9);
    }

    @Test
    void PS_5_decimalRate() {
        PricingService ps = new PricingService();
        assertEquals(50.0, ps.calculateBasePrice(12.5, 4), 1e-9);
    }
}
