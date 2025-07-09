package src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

@DisplayName("InventoryManager Class Tests")
class InventoryManagerTest {

    private InventoryManager manager;
    private static final String TEST_INVENTORY_FILE = "test_inventory.dat";

    @BeforeEach
    void setUp() {
        manager = new InventoryManager();

        manager.addProduct("Laptop", 10, 2, 1);
        manager.addProduct("Mouse", 50, 5, 2);
        manager.addProduct("Keyboard", 15, 3, 3);
        manager.addProduct("Monitor", 8, 2, 4);

        List<Product> products = manager.getAllProducts();
        for (Product product : products) {
            if (product.getId() == 1) {
                product.setPrice(999.99);
                product.setCategory("Electronics");
            } else if (product.getId() == 2) {
                product.setPrice(25.50);
                product.setCategory("Accessories");
            } else if (product.getId() == 3) {
                product.setPrice(75.00);
                product.setCategory("Accessories");
            } else if (product.getId() == 4) {
                product.setPrice(299.99);
                product.setCategory("Electronics");
            }
        }
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(TEST_INVENTORY_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Nested
    @DisplayName("Product Management Tests")
    class ProductManagementTests {

        @Test
        @DisplayName("Should add product successfully")
        void shouldAddProductSuccessfully() {
        }
    }
}