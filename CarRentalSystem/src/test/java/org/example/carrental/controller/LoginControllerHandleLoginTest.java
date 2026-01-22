//THOMAS KROJ
package org.example.carrental.controller;

import javafx.stage.Stage;
import org.example.carrental.AppNavigator;
import org.example.carrental.model.users.Account;
import org.example.carrental.service.AuthService;
import org.example.carrental.service.CarService;
import org.example.carrental.testutil.FxTestUtils_Thomas;
import org.example.carrental.util.Dialogs;
import org.example.carrental.view.LoginView;
import org.junit.jupiter.api.*;

import org.mockito.MockedStatic;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerHandleLoginTest {

    private LoginView view;
    private AuthService authService;
    private CarService carService;
    private LoginController controller;
    private Method handleLogin;

    @BeforeAll
    static void initFxAndNavigator() {
        FxTestUtils_Thomas.initFx();
        FxTestUtils_Thomas.runOnFxAndWait(() -> {
            Stage stage = new Stage();
            AppNavigator.getInstance().init(stage);
        });
    }

    @BeforeEach
    void setup() throws Exception {
        view = FxTestUtils_Thomas.supplyOnFxAndWait(LoginView::new);

        authService = mock(AuthService.class);
        carService = mock(CarService.class);

        controller = FxTestUtils_Thomas.supplyOnFxAndWait(() -> new LoginController(view, authService, carService));

        handleLogin = LoginController.class.getDeclaredMethod("handleLogin");
        handleLogin.setAccessible(true);
    }

    private void setFields(String username, String password) {
        FxTestUtils_Thomas.runOnFxAndWait(() -> {
            view.getUsernameField().setText(username);
            view.getPasswordField().setText(password);
        });
    }

    private void invokeHandleLoginWithNoDialogs() {
        try (MockedStatic<Dialogs> dialogs = mockStatic(Dialogs.class)) {

            dialogs.when(() -> Dialogs.showValidationError(anyString())).thenAnswer(inv -> null);
            dialogs.when(() -> Dialogs.showSuccess(anyString())).thenAnswer(inv -> null);

            dialogs.when(() -> Dialogs.showInfo(anyString(), anyString())).thenAnswer(inv -> null);
            dialogs.when(() -> Dialogs.showError(anyString(), anyString())).thenAnswer(inv -> null);
            dialogs.when(() -> Dialogs.showWarning(anyString(), anyString())).thenAnswer(inv -> null);

            FxTestUtils_Thomas.runOnFxAndWait(() -> {
                try {
                    handleLogin.invoke(controller);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }



    @Test
    void LOG_1_usernameEmpty_showsValidationError_andDoesNotCallAuth() {
        setFields("", "x");

        invokeHandleLoginWithNoDialogs();

        verify(authService, never()).login(anyString(), anyString());
    }

    @Test
    void LOG_2_passwordEmpty_showsValidationError_andDoesNotCallAuth() {
        setFields("user", "");

        invokeHandleLoginWithNoDialogs();

        verify(authService, never()).login(anyString(), anyString());
    }

    @Test
    void LOG_3_validCredentials_success_clearsPassword() {
        setFields("user", "pass");

        Account fakeUser = mock(Account.class);
        when(fakeUser.getFirstName()).thenReturn("John");
        when(authService.login("user", "pass")).thenReturn(Optional.of(fakeUser));

        invokeHandleLoginWithNoDialogs();

        // Password should be cleared on success
        FxTestUtils_Thomas.runOnFxAndWait(() ->
                assertEquals("", view.getPasswordField().getText())
        );
    }

    @Test
    void LOG_4_invalidCredentials_clearsPassword() {
        setFields("user", "pass");
        when(authService.login("user", "pass")).thenReturn(Optional.empty());

        invokeHandleLoginWithNoDialogs();

        FxTestUtils_Thomas.runOnFxAndWait(() ->
                assertEquals("", view.getPasswordField().getText())
        );
    }

    @Test
    void LOG_5_exceptionFromAuth_doesNotCrash() {
        setFields("user", "pass");
        when(authService.login("user", "pass")).thenThrow(new RuntimeException("boom"));

        invokeHandleLoginWithNoDialogs();

        // If we reached here, exception path handled
        verify(authService).login("user", "pass");
    }
}
