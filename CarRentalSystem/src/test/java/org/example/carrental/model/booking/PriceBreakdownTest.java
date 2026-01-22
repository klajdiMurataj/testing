
//Eden Pajo
package org.example.carrental.model.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceBreakdownTest {

    @Test
    void constructor_setsFieldsCorrectly() {
        PriceBreakdown pb = new PriceBreakdown(100.0, 20.0, 120.0);

        assertEquals(100.0, pb.getBasePrice(), 1e-9);
        assertEquals(20.0, pb.getVatAmount(), 1e-9);
        assertEquals(120.0, pb.getTotalPrice(), 1e-9);
    }

    @Test
    void setters_updateFieldsCorrectly() {
        PriceBreakdown pb = new PriceBreakdown();
        pb.setBasePrice(50.0);
        pb.setVatAmount(10.0);
        pb.setTotalPrice(60.0);

        assertEquals(50.0, pb.getBasePrice(), 1e-9);
        assertEquals(10.0, pb.getVatAmount(), 1e-9);
        assertEquals(60.0, pb.getTotalPrice(), 1e-9);
    }
}
