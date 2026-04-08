package main.najah.test;

import main.najah.code.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private final UserService userService = new UserService();

    //  Email Tests 
    @ParameterizedTest
    @DisplayName("Email: valid & invalid")
    @CsvSource({
		"test@example.com, true",
        "test@example, false",
        "test.example.com, false",
        "test@, false",
        "@example.com, false"
    })
    void validateEmailFormat(String email, boolean expectedResult) {
        boolean result = userService.isValidEmail(email);

        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @DisplayName("Email: null or empty")
    @NullAndEmptySource
    void validateEmailWithNullOrEmptyValues(String email) {
        boolean result = userService.isValidEmail(email);

        assertFalse(result);
    }

    //  Authentication Tests 
    @Test
    @DisplayName("Login: correct")
    void authenticateValidUser() {
        boolean result = userService.authenticate("admin", "1234");

        assertTrue(result);
        assertEquals(true, result);
    }

    @ParameterizedTest
    @DisplayName("Login: wrong")
    @CsvSource({
        "admin, 1235",
        "user, 1234",
        "admin, ",
        ", 1234"
    })
    void authenticateInvalidUser(String username, String password) {
        boolean result = userService.authenticate(username, password);

        assertFalse(result);
    }

    //  Performance Test
    @Test
    @DisplayName("Performance test")
    @Timeout(2)
    void performance_test() {
        userService.isValidEmail("test@gmail.com");
        userService.authenticate("admin", "1234");

        assertNotNull(userService);
    }
}