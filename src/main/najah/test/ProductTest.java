package main.najah.test;

import main.najah.code.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("Create product: valid price")
    void create_product_with_valid_price() {
        Product product = new Product("Test Product", 10.0);

        assertNotNull(product);
        assertEquals("Test Product", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals(0.0, product.getDiscount());
    }

    @Test
    @DisplayName("Create product: negative price")
    void create_product_with_invalid_price() {
        IllegalArgumentException error = assertThrows(
            IllegalArgumentException.class,
            () -> new Product("Test Product", -5.0)
        );

        assertEquals("Price must be non-negative", error.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Apply discount: valid values")
    @CsvSource({
        "10.0, 10.0",
        "20.0, 20.0",
        "50.0, 50.0"
    })
    void apply_valid_discounts(double discountPercentage, double expectedDiscount) {
        Product product = new Product("Test Product", 100.0);
        product.applyDiscount(discountPercentage);

        assertEquals(expectedDiscount, product.getDiscount());
    }

    @ParameterizedTest
    @DisplayName("Apply discount: invalid values")
    @ValueSource(doubles = {-10.0, 51.0})
    void apply_invalid_discounts(double discountPercentage) {
        Product product = new Product("Test Product", 100.0);

        IllegalArgumentException error = assertThrows(
            IllegalArgumentException.class,
            () -> product.applyDiscount(discountPercentage)
        );

        assertEquals("Invalid discount", error.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Final price: valid discounts")
    @CsvSource({
        "100.0, 0.0, 100.0",
        "100.0, 10.0, 90.0",
        "100.0, 50.0, 50.0"
    })
    void calculate_final_price_with_valid_discounts(double price, double discountPercentage, double expectedFinalPrice) {
        Product product = new Product("Test Product", price);
        product.applyDiscount(discountPercentage);

        assertEquals(expectedFinalPrice, product.getFinalPrice());
    }

    @Test
    @DisplayName("Final price: after discount")
    void final_price_after_discount() {
        Product product = new Product("Test Product", 100.0);
        product.applyDiscount(20);

        assertEquals(20.0, product.getDiscount());
        assertEquals(80.0, product.getFinalPrice());
    }

    @Test
    @DisplayName("Performance test")
    @Timeout(2)
    void performance_test() {
        Product product = new Product("Test Product", 100.0);
        product.applyDiscount(10);
        product.getFinalPrice();

        assertNotNull(product);
    }
}