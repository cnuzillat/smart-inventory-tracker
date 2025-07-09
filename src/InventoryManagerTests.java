package src;

import java.util.List;

public class InventoryManagerTests {
    
    public static void runAllTests() {
        System.out.println("Running InventoryManager Tests...\n");
        
        testProductManagement();
        testInventoryOperations();
        testSearchAndFilter();
        testCategoryManagement();
        testInventoryValue();
        testDataRetrieval();
        testEdgeCases();
        
        SimpleTestFramework.printSummary();
    }
    
    private static void testProductManagement() {
        System.out.println(" Product Management Tests:");
        System.out.println("-".repeat(30));
        
        InventoryManager manager = new InventoryManager();
        
        // Test adding product
        manager.addProduct("New Product", 20, 5, 1);
        SimpleTestFramework.assertTrue(manager.productExists(1), "Product exists after adding");
        
        // Test overwriting existing product
        manager.addProduct("Updated Product", 30, 8, 1);
        List<Product> products = manager.getAllProducts();
        Product updatedProduct = products.stream()
            .filter(p -> p.getId() == 1)
            .findFirst()
            .orElse(null);
        SimpleTestFramework.assertNotNull(updatedProduct, "Product found after update");
        assert updatedProduct != null;
        SimpleTestFramework.assertEquals("Updated Product", updatedProduct.getName(), "Product name " +
                "updated");
        SimpleTestFramework.assertEquals(30, updatedProduct.getQuantity(), "Product quantity updated");

        SimpleTestFramework.assertTrue(manager.deleteProduct(1), "Delete existing product returns true");
        SimpleTestFramework.assertFalse(manager.productExists(1), "Product does not exist after deletion");

        SimpleTestFramework.assertFalse(manager.deleteProduct(999), "Delete non-existent product returns " +
                "false");

        manager.addProduct("Test Product", 10, 2, 2);
        SimpleTestFramework.assertTrue(manager.productExists(2), "Product exists check");
        SimpleTestFramework.assertFalse(manager.productExists(999), "Non-existent product check");
        
        System.out.println();
    }
    
    private static void testInventoryOperations() {
        System.out.println("Inventory Operations Tests:");
        System.out.println("-".repeat(30));
        
        InventoryManager manager = new InventoryManager();
        manager.addProduct("Test Product", 100, 10, 1);

        manager.sellProduct(1, 30);
        List<Product> products = manager.getAllProducts();
        Product product = products.stream()
            .filter(p -> p.getId() == 1)
            .findFirst()
            .orElse(null);
        SimpleTestFramework.assertNotNull(product, "Product found after selling");
        SimpleTestFramework.assertEquals(70, product.getQuantity(), "Quantity reduced after selling");

        manager.restockProduct(20, 1);
        products = manager.getAllProducts();
        product = products.stream()
            .filter(p -> p.getId() == 1)
            .findFirst()
            .orElse(null);
        assert product != null;
        SimpleTestFramework.assertEquals(90, product.getQuantity(), "Quantity increased after " +
                "restocking");

        SimpleTestFramework.assertDoesNotThrow(() -> {
            manager.sellProduct(999, 10);
        }, "Selling non-existent product");

        SimpleTestFramework.assertDoesNotThrow(() -> {
            manager.restockProduct(10, 999);
        }, "Restocking non-existent product");
        
        System.out.println();
    }
    
    private static void testSearchAndFilter() {
        System.out.println("Search and Filter Tests:");
        System.out.println("-".repeat(30));
        
        InventoryManager manager = new InventoryManager();
        manager.addProduct("Laptop", 10, 2, 1);
        manager.addProduct("Mouse", 50, 5, 2);
        manager.addProduct("Keyboard", 15, 3, 3);

        List<Product> products = manager.getAllProducts();
        for (Product product : products) {
            if (product.getId() == 1) {
                product.setCategory("Electronics");
            } else if (product.getId() == 2) {
                product.setCategory("Accessories");
            } else if (product.getId() == 3) {
                product.setCategory("Accessories");
            }
        }

        List<Product> searchResults = manager.searchProducts("laptop");
        SimpleTestFramework.assertEquals(1, searchResults.size(), "Search by name returns correct " +
                "count");
        SimpleTestFramework.assertEquals("Laptop", searchResults.getFirst().getName(), "Search by name " +
                "returns correct product");

        searchResults = manager.searchProducts("LAPTOP");
        SimpleTestFramework.assertEquals(1, searchResults.size(), "Case insensitive search");

        searchResults = manager.searchProducts("");
        SimpleTestFramework.assertEquals(3, searchResults.size(), "Empty search returns all products");

        searchResults = manager.searchProducts(null);
        SimpleTestFramework.assertEquals(3, searchResults.size(), "Null search returns all products");

        searchResults = manager.searchProducts("   ");
        SimpleTestFramework.assertEquals(3, searchResults.size(), "Whitespace search returns all " +
                "products");

        List<Product> categoryResults = manager.getProductsByCategory("Electronics");
        SimpleTestFramework.assertEquals(1, categoryResults.size(), "Filter by category returns " +
                "correct count");
        SimpleTestFramework.assertEquals("Electronics", categoryResults.get(0).getCategory(), "Filter " +
                "by category returns correct category");

        categoryResults = manager.getProductsByCategory("NonExistent");
        SimpleTestFramework.assertEquals(0, categoryResults.size(), "Non-existent category returns " +
                "empty list");

        categoryResults = manager.getProductsByCategory(null);
        SimpleTestFramework.assertEquals(0, categoryResults.size(), "Null category returns empty list");
        
        System.out.println();
    }
    
    private static void testCategoryManagement() {
        System.out.println("Category Management Tests:");
        System.out.println("-".repeat(30));
        
        InventoryManager manager = new InventoryManager();
        manager.addProduct("Laptop", 10, 2, 1);
        manager.addProduct("Mouse", 50, 5, 2);
        manager.addProduct("Keyboard", 15, 3, 3);

        List<Product> products = manager.getAllProducts();
        for (Product product : products) {
            if (product.getId() == 1) {
                product.setCategory("Electronics");
            } else if (product.getId() == 2) {
                product.setCategory("Accessories");
            } else if (product.getId() == 3) {
                product.setCategory("Accessories");
            }
        }

        List<String> categories = manager.getAllCategories();
        SimpleTestFramework.assertEquals(2, categories.size(), "Correct number of unique categories");
        SimpleTestFramework.assertTrue(categories.contains("Electronics"), "Electronics category present");
        SimpleTestFramework.assertTrue(categories.contains("Accessories"), "Accessories category present");

        manager.addProduct("No Category Product", 5, 1, 4);
        categories = manager.getAllCategories();
        SimpleTestFramework.assertEquals(2, categories.size(), "Categories count unchanged with null " +
                "category product");
        
        System.out.println();
    }
    
    private static void testInventoryValue() {
        System.out.println("Inventory Value Tests:");
        System.out.println("-".repeat(30));
        
        InventoryManager manager = new InventoryManager();
        manager.addProduct("Product 1", 10, 2, 1);
        manager.addProduct("Product 2", 5, 1, 2);

        List<Product> products = manager.getAllProducts();
        for (Product product : products) {
            if (product.getId() == 1) {
                product.setPrice(100.0);
            } else if (product.getId() == 2) {
                product.setPrice(50.0);
            }
        }

        double expectedValue = (100.0 * 10) + (50.0 * 5);
        double actualValue = manager.getTotalInventoryValue();
        SimpleTestFramework.assertEquals(expectedValue, actualValue, "Total inventory value calculation");

        manager.addProduct("Free Product", 10, 2, 3);
        double value = manager.getTotalInventoryValue();
        SimpleTestFramework.assertTrue(Double.isFinite(value), "Total value is finite with zero prices");
        SimpleTestFramework.assertTrue(value >= 0, "Total value is non-negative");
        
        System.out.println();
    }
    
    private static void testDataRetrieval() {
        System.out.println("Data Retrieval Tests:");
        System.out.println("-".repeat(30));
        
        InventoryManager manager = new InventoryManager();
        manager.addProduct("Product 1", 10, 2, 1);
        manager.addProduct("Product 2", 20, 5, 2);

        List<Product> products = manager.getAllProducts();
        SimpleTestFramework.assertEquals(2, products.size(), "Get all products returns correct count");

        List<Product> products2 = manager.getAllProducts();
        SimpleTestFramework.assertFalse(products == products2, "Returned lists are different instances");
        SimpleTestFramework.assertEquals(products.size(), products2.size(), "Returned lists have same size");
        
        System.out.println();
    }
    
    private static void testEdgeCases() {
        System.out.println("Edge Cases Tests:");
        System.out.println("-".repeat(30));

        InventoryManager emptyManager = new InventoryManager();
        SimpleTestFramework.assertEquals(0, emptyManager.getAllProducts().size(), "Empty inventory has " +
                "no products");
        SimpleTestFramework.assertEquals(0.0, emptyManager.getTotalInventoryValue(), "Empty inventory " +
                "has zero value");
        SimpleTestFramework.assertEquals(0, emptyManager.getAllCategories().size(), "Empty inventory " +
                "has no categories");

        InventoryManager largeManager = new InventoryManager();
        largeManager.addProduct("Large Stock", 1000000, 1000, 1);
        SimpleTestFramework.assertTrue(largeManager.productExists(1), "Large quantity product exists");
        
        List<Product> products = largeManager.getAllProducts();
        Product largeProduct = products.stream()
            .filter(p -> p.getId() == 1)
            .findFirst()
            .orElse(null);
        SimpleTestFramework.assertNotNull(largeProduct, "Large quantity product found");
        assert largeProduct != null;
        SimpleTestFramework.assertEquals(1000000, largeProduct.getQuantity(), "Large quantity " +
                "preserved");
        
        System.out.println();
    }
} 