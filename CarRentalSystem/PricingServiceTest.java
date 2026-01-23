package org.example.carrental.service;

import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.*; 

  
class PricingServiceTest { 

    PricingService pricingService = new PricingService(); 

    @Test 
    void testCalculateTotal_zero() { 

        double total = pricingService.calculateTotal(0); 

        assertEquals(0, total, 0.0001); 

    } 

    @Test 
    void testCalculateTotal_small() { 

        double total = pricingService.calculateTotal(1); 

        assertEquals(1.2, total, 0.0001); 

    } 

    @Test 
    void testCalculateTotal_typical() { 

        double total = pricingService.calculateTotal(100); 

        assertEquals(120, total, 0.0001); 

    } 

    @Test 
    void testCalculateTotal_large() { 

        double total = pricingService.calculateTotal(1_000_000); 

        assertEquals(1_200_000, total, 0.0001); 

    } 

   @Test 
    void testCalculateTotal_negative() { 

        double total = pricingService.calculateTotal(-1); 

        assertEquals(-1.2, total, 0.0001); 

    } 

}
