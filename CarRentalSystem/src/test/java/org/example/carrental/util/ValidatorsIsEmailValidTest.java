//THOMAS KROJ
package org.example.carrental.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorsIsEmailValidTest {

    @Test
    void isEmailValid_validEmail_returnsTrue() {
        assertTrue(Validators.isValidEmail("user@test.com"));
    }

    @Test
    void isEmailValid_invalidEmail_returnsFalse() {
        assertFalse(Validators.isValidEmail("invalid-email"));
    }

    @Test
    void isEmailValid_null_returnsFalse() {
        assertFalse(Validators.isValidEmail(null));
    }
}
