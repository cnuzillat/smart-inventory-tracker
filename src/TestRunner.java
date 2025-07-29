package src;

/**
 * Main test runner for the inventory management system
 *
 * @author Chloe Nuzillat
 */
public class TestRunner {
    
    /**
     * Main method to run all tests
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println(" SMART INVENTORY TRACKER - TEST SUITE");
        System.out.println("=".repeat(50));
        System.out.println();

        ProductTests.runAllTests();
        
        System.out.println("\n" + "=".repeat(50) + "\n");

        SimpleTestFramework.reset();

        InventoryManagerTests.runAllTests();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ALL TESTS COMPLETED!");
        System.out.println("=".repeat(50));

        if (SimpleTestFramework.failedTests > 0) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }
} 