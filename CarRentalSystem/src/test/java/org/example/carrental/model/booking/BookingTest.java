package org.example.carrental.model.booking;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.example.carrental.model.common.DateRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookingTest {

    private Booking booking;

    @BeforeEach
    void setup() {
        booking = new Booking();
    }

    @Test
    void getId_whenIdIsNull_returnsNull() {
        booking.setId(null);
        assertNull(booking.getId(), "Expected getId() to return null when id is null");
    }

    @Test
    void getId_whenIdUninitialized_returnsNull() {
        // Default constructor leaves id uninitialized
        assertNull(booking.getId(), "Expected getId() to return null when id is uninitialized");
    }

    @Test
    void getId_whenIdNormal_returnsCorrectId() {
        booking.setId("B123");
        assertEquals("B123", booking.getId(), "Expected getId() to return the normal id string");
    }

    /*@Test
    void setId_objectToString_resultsInString() {
        Object obj = new Object();
        booking.setId(obj);
        assertEquals(obj, booking.getId(), "Expected getId() to return error or the address of the obj");
    }
     */

    @Test
    void setId_normalString_resultsInCorrectId() {
        booking.setId("B001");
        assertEquals("B001", booking.getId(), "Expected getId() to return the normal string id");
    }

    @Test
    void setId_null_resultsInNull() {
        booking.setId(null);
        assertNull(booking.getId(), "Expected getId() to return null when setId(null) is called");
    }

    @Test
    void getDateRange_uninitialized_returnsNull() {
        assertNull(booking.getDateRange(), "Expected getDateRange() to return null if not initialized");
    }

    @Test
    void setDateRange_normalRange_getDateRangeReturnsSame() {
        DateRange range = new DateRange(LocalDate.now(), LocalDate.now().plusDays(3));
        booking.setDateRange(range);
        assertEquals(range, booking.getDateRange(), "Expected getDateRange() to return the set date range");
    }

    @Test
    void setDateRange_nullRange_getDateRangeReturnsNull() {
        booking.setDateRange(null);
        assertNull(booking.getDateRange(), "Expected getDateRange() to return null when setDateRange(null) is called");
    }

    @Test
    void setDateRange_startEqualsEnd_getDateRangeReturnsSame() {
        LocalDate today = LocalDate.now();
        DateRange range = new DateRange(today, today);
        booking.setDateRange(range);
        assertEquals(range, booking.getDateRange(), "Expected getDateRange() to handle start == end date range");
    }

    @Test
    void setDateRange_normalRange_getDateRangeReturnsSam() {
        DateRange range = new DateRange(LocalDate.now().plusDays(6), LocalDate.now());
        booking.setDateRange(range);
        assertEquals(range, booking.getDateRange(), "not supposed to work");
    }
}
