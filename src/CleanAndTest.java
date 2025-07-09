package src;

public class CleanAndTest {
    
    public static void main(String[] args) {
        System.out.println("Cleaning up and running tests...\n");

        System.out.println("Testing compilation...");
        try {
            Product testProduct = new Product("Test", 10, 2, 1);
            System.out.println("Product class compiles successfully");
            
            InventoryManager testManager = new InventoryManager();
            System.out.println("InventoryManager class compiles successfully");
            
            SimpleTestFramework.assertEquals("Test", testProduct.getName(), "Basic test");
            System.out.println("SimpleTestFramework works correctly");
            
        } catch (Exception e) {
            System.out.println("Compilation error: " + e.getMessage());
            return;
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RUNNING FULL TEST SUITE");
        System.out.println("=".repeat(50));

        ProductTests.runAllTests();
        
        System.out.println("\n" + "=".repeat(50) + "\n");

        SimpleTestFramework.reset();

        InventoryManagerTests.runAllTests();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ALL TESTS COMPLETED!");
        System.out.println("=".repeat(50));
    }
} 