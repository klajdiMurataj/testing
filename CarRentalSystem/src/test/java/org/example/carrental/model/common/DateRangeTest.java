//Eden Pajo
package org.example.carrental.model.common;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeTest {

    @Test
    void getDays_inclusiveRange() {
        DateRange range = new DateRange(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 3)
        );

        assertEquals(3, range.getDays());
    }

    @Test
    void overlaps_trueWhenOverlapping() {
        DateRange r1 = new DateRange(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 5)
        );

        DateRange r2 = new DateRange(
                LocalDate.of(2024, 1, 4),
                LocalDate.of(2024, 1, 10)
        );

        assertTrue(r1.overlaps(r2));
    }

    @Test
    void overlaps_falseWhenSeparate() {
        DateRange r1 = new DateRange(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 3)
        );

        DateRange r2 = new DateRange(
                LocalDate.of(2024, 1, 4),
                LocalDate.of(2024, 1, 6)
        );

        assertFalse(r1.overlaps(r2));
    }

    @Test
    void constructor_throwsWhenEndBeforeStart() {
        assertThrows(IllegalArgumentException.class, () ->
                new DateRange(
                        LocalDate.of(2024, 1, 5),
                        LocalDate.of(2024, 1, 1)
                )
        );
    }
}
