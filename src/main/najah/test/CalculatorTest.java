package main.najah.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.*;

import main.najah.code.Calculator;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Calculator Test Suite")
public class CalculatorTest {
    
    private Calculator calculator;
    
@BeforeAll
static void setUpBeforeAll() {
    System.out.println("Start tests");
}

@AfterAll
static void tearDownAfterAll() {
    System.out.println("End tests");
}

@BeforeEach
void setUp() {
    calculator = new Calculator();
    System.out.println("Setup done");
}

@AfterEach
void tearDown() {
    calculator = null;
    System.out.println("Test done");
}
    
    // Addition Tests 
    @Test
    @Order(1)
    @DisplayName("Add: no input")
    void when_no_numbers_given_should_return_zero() {
        int result = calculator.add();
        assertEquals(0, result);
        assertTrue(result >= 0, "Result should be non-negative");
    }
    
    @Test
    @Order(2)
    @DisplayName("Add: one number")
    void adding_single_number_returns_same_number() {
        int result = calculator.add(7);
        assertEquals(7, result);
        assertNotEquals(0, result);
    }
    
    @Test
    @Order(3)
    @DisplayName("Add: two numbers")
    void adding_two_positive_numbers() {
        int result = calculator.add(5, 3);
        assertEquals(8, result);
        assertTrue(result > 0);
    }
    
    @ParameterizedTest
    @Order(4)
    @DisplayName("Add: parameterized")
    @CsvSource({
        "1, 2, 3",
        "10, -5, 5",
        "-2, -3, -5",
        "0, 0, 0"
    })
    void parameterized_addition_test(int a, int b, int expected) {
        int result = calculator.add(a, b);
        assertEquals(expected, result);
        assertNotNull(calculator);
    }
    
    @Test
    @Order(5)
    @DisplayName("Add: multiple numbers")
    void adding_multiple_numbers() {
        int result = calculator.add(1, 2, 3, 4);
        assertEquals(10, result);
        assertTrue(result > 0);
    }
    
    @Test
    @Order(6)
    @DisplayName("Add: negative numbers")
    void adding_negative_numbers() {
        int result = calculator.add(-2, -3);
        assertEquals(-5, result);
        assertTrue(result < 0);
    }

    // Division Tests
    @Test
    @Order(7)
    @DisplayName("Divide: normal case")
    void simple_division_with_no_remainder() {
        int result = calculator.divide(10, 2);
        assertEquals(5, result);
        assertTrue(result > 0);
    }
    
    @Test
    @Order(8)
    @DisplayName("Divide: with remainder")
    void division_with_remainder() {
        int result = calculator.divide(10, 3);
        assertEquals(3, result);
        assertTrue(result > 0);
    }
    
    @Test
    @Order(9)
    @DisplayName("Divide: by zero")
    void dividing_by_zero_should_throw_exception() {
        ArithmeticException error = assertThrows(
            ArithmeticException.class,
            () -> calculator.divide(10, 0)
        );
        assertEquals("Cannot divide by zero", error.getMessage());
        assertNotNull(error.getMessage());
    }
    
    @Test
    @Order(10)
    @DisplayName("Divide: negative dividend")
    void dividing_negative_number() {
        int result = calculator.divide(-10, 2);
        assertEquals(-5, result);
        assertTrue(result < 0);
    }
    
    @Test
    @Order(11)
    @DisplayName("Divide: negative divisor")
    void dividing_by_negative_number() {
        int result = calculator.divide(10, -2);
        assertEquals(-5, result);
        assertTrue(result < 0);
    }
    
    @ParameterizedTest
    @Order(12)
   	@DisplayName("Divide: parameterized")
    @CsvSource({
        "20, 4, 5",
        "15, 3, 5",
        "-12, 4, -3",
        "7, 2, 3"
    })
    void parameterized_division_test(int dividend, int divisor, int expected) {
        int result = calculator.divide(dividend, divisor);
        assertEquals(expected, result);
        assertNotNull(calculator);
    }

    // Factorial Tests
    @Test
@Order(13)
@DisplayName("Factorial: 0")
void factorial_of_zero() {
    int result = calculator.factorial(0);
    assertEquals(1, result);
    assertTrue(result > 0);
}

@Test
@Order(14)
@DisplayName("Factorial: 1")
void factorial_of_one() {
    int result = calculator.factorial(1);
    assertEquals(1, result);
    assertTrue(result > 0);
}

@Test
@Order(15)
@DisplayName("Factorial: positive number")
void factorial_of_positive_number() {
    int result = calculator.factorial(5);
    assertEquals(120, result);
    assertTrue(result > 0);
}

@Test
@Order(16)
@DisplayName("Factorial: negative input")
void factorial_negative_should_throw_exception() {
    IllegalArgumentException error = assertThrows(
        IllegalArgumentException.class,
        () -> calculator.factorial(-1)
    );
    assertEquals("Negative input", error.getMessage());
    assertNotNull(error.getMessage());
}

@ParameterizedTest
@Order(17)
@DisplayName("Factorial: parameterized")
@CsvSource({
    "0, 1",
    "1, 1",
    "3, 6",
    "4, 24"
})
void parameterized_factorial_test(int input, int expected) {
    int result = calculator.factorial(input);
    assertEquals(expected, result);
    assertTrue(result > 0 || input == 0);
}

    // Performance Tests 
    @Test
    @Order(18)
    @Timeout(2)
    @DisplayName("Performance test")
    void factorial_performance_test() {
        calculator.factorial(10);
        assertNotNull(calculator);
    }

    // Edge Case Test 
    @Test
    @Order(19)
    @DisplayName("Instance not null")
    void calculator_instance_should_not_be_null() {
        assertNotNull(calculator);
        assertNotNull(calculator.getClass());
    }

    // Disabled Failing Test
    @Test
    @Order(20)
    @Disabled("Fix expected value: 2 + 3 should be 5, not 10")
    @DisplayName("Failing test (disabled)")
    void failing_test() {
        assertEquals(10, calculator.add(2, 3));
    }
}