//THOMAS KROJ
package org.example.carrental.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorsIsNotBlankTest {

    @Test
    void isNotBlank_validString_returnsTrue() {
        assertTrue(Validators.isNotEmpty("John"));
    }

    @Test
    void isNotBlank_blankString_returnsFalse() {
        assertFalse(Validators.isNotEmpty("   "));
    }

    @Test
    void isNotBlank_null_returnsFalse() {
        assertFalse(Validators.isNotEmpty(null));
    }
}
