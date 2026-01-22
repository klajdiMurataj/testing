//THOMAS KROJ
package org.example.carrental.controller;

import org.example.carrental.service.AuthService;
import org.example.carrental.storage.repositories.UserRepository;
import org.example.carrental.testutil.FxTestUtils_Thomas;
import org.example.carrental.util.Dialogs;
import org.example.carrental.view.AdminView;
import org.junit.jupiter.api.*;

import org.mockito.MockedStatic;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerValidateWorkerInputsTest {

    private AdminController controller;
    private Method validateMethod;

    @BeforeAll
    static void initFx() {
        FxTestUtils_Thomas.initFx();
    }

    @BeforeEach
    void setup() throws Exception {
        AdminView view = FxTestUtils_Thomas.supplyOnFxAndWait(AdminView::new);

        // Real AuthService is fine; controller mostly needs it to construct.
        AuthService authService = new AuthService(new UserRepository());

        controller = FxTestUtils_Thomas.supplyOnFxAndWait(() -> new AdminController(view, authService));

        validateMethod = AdminController.class.getDeclaredMethod(
                "validateWorkerInputs",
                String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class
        );
        validateMethod.setAccessible(true);
    }

    private boolean callValidate(String username, String password, String firstName, String lastName,
                                 String email, String phone, String employeeId, String department) {
        try (MockedStatic<Dialogs> dialogsMock = mockStatic(Dialogs.class)) {
            // Prevent any real UI dialogs from opening
            dialogsMock.when(() -> Dialogs.showValidationError(anyString())).thenAnswer(inv -> null);

            try {
                return (boolean) validateMethod.invoke(controller,
                        username, password, firstName, lastName,
                        email, phone, employeeId, department
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void ADM_0_allValid_returnsTrue() {
        boolean ok = callValidate(
                "john", "secret1", "John", "Doe",
                "john@doe.com", "0691234567", "E123", "IT"
        );
        assertTrue(ok);
    }

    @Test
    void ADM_1_usernameEmpty_returnsFalse() {
        assertFalse(callValidate(
                "", "secret1", "John", "Doe",
                "john@doe.com", "0691234567", "E123", "IT"
        ));
    }

    @Test
    void ADM_2_passwordTooShort_returnsFalse() {
        assertFalse(callValidate(
                "john", "12345", "John", "Doe",
                "john@doe.com", "0691234567", "E123", "IT"
        ));
    }

    @Test
    void ADM_3_firstNameEmpty_returnsFalse() {
        assertFalse(callValidate(
                "john", "secret1", "", "Doe",
                "john@doe.com", "0691234567", "E123", "IT"
        ));
    }

    @Test
    void ADM_4_emailInvalid_returnsFalse() {
        assertFalse(callValidate(
                "john", "secret1", "John", "Doe",
                "abc", "0691234567", "E123", "IT"
        ));
    }

    @Test
    void ADM_5_phoneInvalid_returnsFalse() {
        assertFalse(callValidate(
                "john", "secret1", "John", "Doe",
                "john@doe.com", "123", "E123", "IT"
        ));
    }

    @Test
    void ADM_6_employeeIdBlank_returnsFalse() {
        assertFalse(callValidate(
                "john", "secret1", "John", "Doe",
                "john@doe.com", "0691234567", "   ", "IT"
        ));
    }

    @Test
    void ADM_7_departmentNull_returnsFalse() {
        assertFalse(callValidate(
                "john", "secret1", "John", "Doe",
                "john@doe.com", "0691234567", "E123", null
        ));
    }
}
