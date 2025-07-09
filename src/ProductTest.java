package src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

@DisplayName("Product Class Tests")
class ProductTest {
    
    private Product validProduct;
    
    @BeforeEach
    void setUp() {
        validProduct = new Product("Test Product", 100, 10, 1);
    }
    
    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        
        @Test
        @DisplayName("Should create product with valid parameters")
        void shouldCreateProductWithValidParameters() {
            Product product = new Product("Valid Product", 50, 5, 2);
            
            assertEquals("Valid Product", product.getName());
            assertEquals(50, product.getQuantity());
            assertEquals(5, product.getQuantityThreshold());
            assertEquals(2, product.getId());
        }
        
        @Test
        @DisplayName("Should trim product name")
        void shouldTrimProductName() {
            Product product = new Product("  Trimmed Name  ", 10, 2, 3);
            assertEquals("Trimmed Name", product.getName());
        }
        
        @Test
        @DisplayName("Should throw exception for null name")
        void shouldThrowExceptionForNullName() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Product(null, 10, 2, 1)
            );
            assertEquals("Product name cannot be empty", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should throw exception for empty name")
        void shouldThrowExceptionForEmptyName() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Product("", 10, 2, 1)
            );
            assertEquals("Product name cannot be empty", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should throw exception for whitespace name")
        void shouldThrowExceptionForWhitespaceName() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Product("   ", 10, 2, 1)
            );
            assertEquals("Product name cannot be empty", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should throw exception for negative quantity")
        void shouldThrowExceptionForNegativeQuantity() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Product("Test", -5, 2, 1)
            );
            assertEquals("Quantity cannot be negative", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should throw exception for negative threshold")
        void shouldThrowExceptionForNegativeThreshold() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Product("Test", 10, -2, 1)
            );
            assertEquals("Threshold cannot be negative", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should throw exception for negative ID")
        void shouldThrowExceptionForNegativeId() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Product("Test", 10, 2, -1)
            );
            assertEquals("ID cannot be negative", exception.getMessage());
        }
    }
    
    @Nested
    @DisplayName("Getter Tests")
    class GetterTests {
        
        @Test
        @DisplayName("Should return correct name")
        void shouldReturnCorrectName() {
            assertEquals("Test Product", validProduct.getName());
        }
        
        @Test
        @DisplayName("Should return correct quantity")
        void shouldReturnCorrectQuantity() {
            assertEquals(100, validProduct.getQuantity());
        }
        
        @Test
        @DisplayName("Should return correct threshold")
        void shouldReturnCorrectThreshold() {
            assertEquals(10, validProduct.getQuantityThreshold());
        }
        
        @Test
        @DisplayName("Should return correct ID")
        void shouldReturnCorrectId() {
            assertEquals(1, validProduct.getId());
        }
    }
    
    @Nested
    @DisplayName("Sell Method Tests")
    class SellMethodTests {
        
        @Test
        @DisplayName("Should reduce quantity when selling")
        void shouldReduceQuantityWhenSelling() {
            validProduct.sell(20);
            assertEquals(80, validProduct.getQuantity());
        }
        
        @Test
        @DisplayName("Should update lastUpdated when selling")
        void shouldUpdateLastUpdatedWhenSelling() {
            LocalDateTime beforeSell = LocalDateTime.now();
            validProduct.sell(10);
            LocalDateTime afterSell = LocalDateTime.now();
            
            assertNotNull(validProduct.getLastUpdated());
            assertTrue(validProduct.getLastUpdated().isAfter(beforeSell) || 
                      validProduct.getLastUpdated().equals(beforeSell));
            assertTrue(validProduct.getLastUpdated().isBefore(afterSell) || 
                      validProduct.getLastUpdated().equals(afterSell));
        }
        
        @Test
        @DisplayName("Should throw exception for zero sell amount")
        void shouldThrowExceptionForZeroSellAmount() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validProduct.sell(0)
            );
            assertEquals("Sell amount must be positive", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should throw exception for negative sell amount")
        void shouldThrowExceptionForNegativeSellAmount() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validProduct.sell(-5)
            );
            assertEquals("Sell amount must be positive", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should throw exception for selling more than available")
        void shouldThrowExceptionForSellingMoreThanAvailable() {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validProduct.sell(150)
            );
            assertEquals("Cannot sell more than available stock", exception.getMessage());
        }
        
        @Test
        @DisplayName("Should allow selling exact available quantity")
        void shouldAllowSellingExactAvailableQuantity() {
            validProduct.sell(100);
            assertEquals(0, validProduct.getQuantity());
        }
    }
    
    @Nested
    @DisplayName("Restock Method Tests")
    class RestockMethodTests {
        
        @Test
        @DisplayName("Should increase quantity when restocking")
        void shouldIncreaseQuantityWhenRestocking() {
            validProduct.restock(50);
            assertEquals(150, validProduct.getQuantity());
        }
        
        @Test
        @DisplayName("Should handle restocking zero")
        void shouldHandleRestockingZero() {
            validProduct.restock(0);
            assertEquals(100, validProduct.getQuantity());
        }
        
        @Test
        @DisplayName("Should handle restocking negative amount")
        void shouldHandleRestockingNegativeAmount() {
            validProduct.restock(-10);
            assertEquals(90, validProduct.getQuantity());
        }
    }
    
    @Nested
    @DisplayName("Low Stock Tests")
    class LowStockTests {
        
        @Test
        @DisplayName("Should not be low stock when quantity above threshold")
        void shouldNotBeLowStockWhenQuantityAboveThreshold() {
            assertFalse(validProduct.isLowStock());
        }
        
        @Test
        @DisplayName("Should be low stock when quantity equals threshold")
        void shouldBeLowStockWhenQuantityEqualsThreshold() {
            validProduct.sell(90); // 100 - 90 = 10, which equals threshold
            assertTrue(validProduct.isLowStock());
        }
        
        @Test
        @DisplayName("Should be low stock when quantity below threshold")
        void shouldBeLowStockWhenQuantityBelowThreshold() {
            validProduct.sell(95); // 100 - 95 = 5, which is below threshold of 10
            assertTrue(validProduct.isLowStock());
        }
        
        @Test
        @DisplayName("Should be low stock when quantity is zero")
        void shouldBeLowStockWhenQuantityIsZero() {
            validProduct.sell(100);
            assertTrue(validProduct.isLowStock());
        }
    }
    
    @Nested
    @DisplayName("Price and Value Tests")
    class PriceAndValueTests {
        
        @Test
        @DisplayName("Should set and get price correctly")
        void shouldSetAndGetPriceCorrectly() {
            validProduct.setPrice(25.99);
            assertEquals(25.99, validProduct.getPrice(), 0.001);
        }
        
        @Test
        @DisplayName("Should calculate total value correctly")
        void shouldCalculateTotalValueCorrectly() {
            validProduct.setPrice(10.50);
            assertEquals(1050.0, validProduct.getTotalValue(), 0.001);
        }
        
        @Test
        @DisplayName("Should handle zero price")
        void shouldHandleZeroPrice() {
            validProduct.setPrice(0.0);
            assertEquals(0.0, validProduct.getTotalValue(), 0.001);
        }
        
        @Test
        @DisplayName("Should update price and lastUpdated")
        void shouldUpdatePriceAndLastUpdated() {
            validProduct.updatePrice(15.99);
            assertEquals(15.99, validProduct.getPrice(), 0.001);
            assertNotNull(validProduct.getLastUpdated());
        }
        
        @Test
        @DisplayName("Should not update price for negative value")
        void shouldNotUpdatePriceForNegativeValue() {
            validProduct.setPrice(10.0);
            validProduct.updatePrice(-5.0);
            assertEquals(10.0, validProduct.getPrice(), 0.001);
        }
    }
    
    @Nested
    @DisplayName("Category and Description Tests")
    class CategoryAndDescriptionTests {
        
        @Test
        @DisplayName("Should set and get category correctly")
        void shouldSetAndGetCategoryCorrectly() {
            validProduct.setCategory("Electronics");
            assertEquals("Electronics", validProduct.getCategory());
        }
        
        @Test
        @DisplayName("Should set and get description correctly")
        void shouldSetAndGetDescriptionCorrectly() {
            validProduct.setDescription("A test product for testing");
            assertEquals("A test product for testing", validProduct.getDescription());
        }
        
        @Test
        @DisplayName("Should handle null category")
        void shouldHandleNullCategory() {
            validProduct.setCategory(null);
            assertNull(validProduct.getCategory());
        }
        
        @Test
        @DisplayName("Should handle null description")
        void shouldHandleNullDescription() {
            validProduct.setDescription(null);
            assertNull(validProduct.getDescription());
        }
    }
    
    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {
        
        @Test
        @DisplayName("Should return correct string representation")
        void shouldReturnCorrectStringRepresentation() {
            String expected = "Test Product: 100 (Low threshold: 10) - 1";
            assertEquals(expected, validProduct.toString());
        }
        
        @Test
        @DisplayName("Should reflect quantity changes in toString")
        void shouldReflectQuantityChangesInToString() {
            validProduct.sell(30);
            String expected = "Test Product: 70 (Low threshold: 10) - 1";
            assertEquals(expected, validProduct.toString());
        }
    }
} 