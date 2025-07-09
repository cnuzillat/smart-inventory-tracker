package src;

public class ProductTests {
    
    public static void runAllTests() {
        System.out.println("Running Product Tests...\n");
        
        testConstructor();
        testGetters();
        testSellMethod();
        testRestockMethod();
        testLowStock();
        testPriceAndValue();
        testCategoryAndDescription();
        testToString();
        
        SimpleTestFramework.printSummary();
    }
    
    private static void testConstructor() {
        System.out.println("Constructor Tests:");
        System.out.println("-".repeat(30));

        Product validProduct = new Product("Test Product", 100, 10, 1);
        SimpleTestFramework.assertEquals("Test Product", validProduct.getName(), "Valid constructor - " +
                "name");
        SimpleTestFramework.assertEquals(100, validProduct.getQuantity(), "Valid constructor - " +
                "quantity");
        SimpleTestFramework.assertEquals(10, validProduct.getQuantityThreshold(), "Valid constructor - " +
                "threshold");
        SimpleTestFramework.assertEquals(1, validProduct.getId(), "Valid constructor - ID");

        Product trimmedProduct = new Product("  Trimmed Name  ", 10, 2, 2);
        SimpleTestFramework.assertEquals("Trimmed Name", trimmedProduct.getName(), "Constructor trims " +
                "name");

        SimpleTestFramework.assertThrows(() -> {
            new Product(null, 10, 2, 3);
        }, "IllegalArgumentException", "Constructor with null name");

        SimpleTestFramework.assertThrows(() -> {
            new Product("", 10, 2, 4);
        }, "IllegalArgumentException", "Constructor with empty name");

        SimpleTestFramework.assertThrows(() -> {
            new Product("   ", 10, 2, 5);
        }, "IllegalArgumentException", "Constructor with whitespace name");

        SimpleTestFramework.assertThrows(() -> {
            new Product("Test", -5, 2, 6);
        }, "IllegalArgumentException", "Constructor with negative quantity");

        SimpleTestFramework.assertThrows(() -> {
            new Product("Test", 10, -2, 7);
        }, "IllegalArgumentException", "Constructor with negative threshold");

        SimpleTestFramework.assertThrows(() -> {
            new Product("Test", 10, 2, -1);
        }, "IllegalArgumentException", "Constructor with negative ID");
        
        System.out.println();
    }
    
    private static void testGetters() {
        System.out.println("Getter Tests:");
        System.out.println("-".repeat(30));
        
        Product product = new Product("Test Product", 100, 10, 1);
        
        SimpleTestFramework.assertEquals("Test Product", product.getName(), "getName returns correct " +
                "name");
        SimpleTestFramework.assertEquals(100, product.getQuantity(), "getQuantity returns correct " +
                "quantity");
        SimpleTestFramework.assertEquals(10, product.getQuantityThreshold(), "getThreshold returns " +
                "correct threshold");
        SimpleTestFramework.assertEquals(1, product.getId(), "getId returns correct ID");
        
        System.out.println();
    }
    
    private static void testSellMethod() {
        System.out.println("Sell Method Tests:");
        System.out.println("-".repeat(30));
        
        Product product = new Product("Test Product", 100, 10, 1);

        product.sell(20);
        SimpleTestFramework.assertEquals(80, product.getQuantity(), "Sell reduces quantity correctly");

        product.sell(80);
        SimpleTestFramework.assertEquals(0, product.getQuantity(), "Sell exact quantity");

        SimpleTestFramework.assertThrows(() -> {
            product.sell(0);
        }, "IllegalArgumentException", "Sell zero amount");

        SimpleTestFramework.assertThrows(() -> {
            product.sell(-5);
        }, "IllegalArgumentException", "Sell negative amount");

        Product newProduct = new Product("New Product", 50, 5, 2);
        SimpleTestFramework.assertThrows(() -> {
            newProduct.sell(60);
        }, "IllegalArgumentException", "Sell more than available");
        
        System.out.println();
    }
    
    private static void testRestockMethod() {
        System.out.println("Restock Method Tests:");
        System.out.println("-".repeat(30));
        
        Product product = new Product("Test Product", 100, 10, 1);

        product.restock(50);
        SimpleTestFramework.assertEquals(150, product.getQuantity(), "Restock increases quantity");

        product.restock(0);
        SimpleTestFramework.assertEquals(150, product.getQuantity(), "Restock zero amount");

        product.restock(-10);
        SimpleTestFramework.assertEquals(140, product.getQuantity(), "Restock negative amount");
        
        System.out.println();
    }
    
    private static void testLowStock() {
        System.out.println("Low Stock Tests:");
        System.out.println("-".repeat(30));
        
        Product product = new Product("Test Product", 100, 10, 1);

        SimpleTestFramework.assertFalse(product.isLowStock(), "Not low stock when above threshold");

        product.sell(90);
        SimpleTestFramework.assertTrue(product.isLowStock(), "Low stock when equals threshold");

        Product product2 = new Product("Test Product 2", 100, 10, 2);
        product2.sell(95);
        SimpleTestFramework.assertTrue(product2.isLowStock(), "Low stock when below threshold");

        Product product3 = new Product("Test Product 3", 100, 10, 3);
        product3.sell(100);
        SimpleTestFramework.assertTrue(product3.isLowStock(), "Low stock when quantity is zero");
        
        System.out.println();
    }
    
    private static void testPriceAndValue() {
        System.out.println("Price and Value Tests:");
        System.out.println("-".repeat(30));
        
        Product product = new Product("Test Product", 100, 10, 1);

        product.setPrice(25.99);
        SimpleTestFramework.assertEquals(25.99, product.getPrice(), "Set and get price");

        SimpleTestFramework.assertEquals(2599.0, product.getTotalValue(), "Calculate total value");

        product.setPrice(0.0);
        SimpleTestFramework.assertEquals(0.0, product.getTotalValue(), "Zero price total value");

        product.updatePrice(15.99);
        SimpleTestFramework.assertEquals(15.99, product.getPrice(), "Update price");
        SimpleTestFramework.assertNotNull(product.getLastUpdated(), "Update price sets lastUpdated");

        product.updatePrice(-5.0);
        SimpleTestFramework.assertEquals(15.99, product.getPrice(), "Negative price does not update");
        
        System.out.println();
    }
    
    private static void testCategoryAndDescription() {
        System.out.println("Category and Description Tests:");
        System.out.println("-".repeat(30));
        
        Product product = new Product("Test Product", 100, 10, 1);

        product.setCategory("Electronics");
        SimpleTestFramework.assertEquals("Electronics", product.getCategory(), "Set and get category");

        product.setDescription("A test product for testing");
        SimpleTestFramework.assertEquals("A test product for testing", product.getDescription(), "Set " +
                "and get description");

        product.setCategory(null);
        SimpleTestFramework.assertNull(product.getCategory(), "Null category");

        product.setDescription(null);
        SimpleTestFramework.assertNull(product.getDescription(), "Null description");
        
        System.out.println();
    }
    
    private static void testToString() {
        System.out.println("ToString Tests:");
        System.out.println("-".repeat(30));
        
        Product product = new Product("Test Product", 100, 10, 1);
        String expected = "Test Product: 100 (Low threshold: 10) - 1";
        SimpleTestFramework.assertEquals(expected, product.toString(), "Correct string representation");

        product.sell(30);
        String expectedAfterSell = "Test Product: 70 (Low threshold: 10) - 1";
        SimpleTestFramework.assertEquals(expectedAfterSell, product.toString(), "String reflects quantity " +
                "changes");
        
        System.out.println();
    }
}