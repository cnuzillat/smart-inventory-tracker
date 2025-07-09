package src;

public class SimpleTest {
    
    public static void main(String[] args) {
        System.out.println("Simple Test - Testing Framework Verification");
        System.out.println("=".repeat(50));

        SimpleTestFramework.assertEquals("hello", "hello", "String equality test");
        SimpleTestFramework.assertEquals(42, 42, "Integer equality test");
        SimpleTestFramework.assertTrue(true, "True assertion test");
        SimpleTestFramework.assertFalse(false, "False assertion test");
        SimpleTestFramework.assertNotNull("not null", "NotNull test");
        SimpleTestFramework.assertNull(null, "Null test");

        Product product = new Product("Test Product", 100, 10, 1);
        SimpleTestFramework.assertEquals("Test Product", product.getName(), "Product name test");
        SimpleTestFramework.assertEquals(100, product.getQuantity(), "Product quantity test");

        SimpleTestFramework.assertThrows(() -> {
            new Product(null, 10, 2, 1);
        }, "IllegalArgumentException", "Exception test");
        
        SimpleTestFramework.printSummary();
    }
} 