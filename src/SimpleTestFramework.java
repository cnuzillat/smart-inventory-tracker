package src;

/**
 * A simple testing framework for unit testing
 *
 * @author Chloe Nuzillat
 */
public class SimpleTestFramework {
    private static int totalTests = 0;
    private static int passedTests = 0;
    static int failedTests = 0;

    /**
     * Asserts that two objects are equal
     *
     * @param expected the expected value
     * @param actual the actual value
     * @param testName the name of the test
     */
    public static void assertEquals(Object expected, Object actual, String testName) {
        totalTests++;
        if (expected == null && actual == null) {
            passedTests++;
            System.out.println("✓ " + testName);
        } else if (expected != null && expected.equals(actual)) {
            passedTests++;
            System.out.println("✓ " + testName);
        } else {
            failedTests++;
            System.out.println("✗ " + testName + " - Expected: " + expected + ", Actual: " + actual);
        }
    }

    /**
     * Asserts that a condition is true
     *
     * @param condition the condition to check
     * @param testName the name of the test
     */
    public static void assertTrue(boolean condition, String testName) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.println("✓ " + testName);
        } else {
            failedTests++;
            System.out.println("✗ " + testName + " - Expected: true, Actual: false");
        }
    }

    /**
     * Asserts that a condition is false
     *
     * @param condition the condition to check
     * @param testName the name of the test
     */
    public static void assertFalse(boolean condition, String testName) {
        totalTests++;
        if (!condition) {
            passedTests++;
            System.out.println("✓ " + testName);
        } else {
            failedTests++;
            System.out.println("✗ " + testName + " - Expected: false, Actual: true");
        }
    }

    /**
     * Asserts that an object is not null
     *
     * @param object the object to check
     * @param testName the name of the test
     */
    public static void assertNotNull(Object object, String testName) {
        totalTests++;
        if (object != null) {
            passedTests++;
            System.out.println("✓ " + testName);
        } else {
            failedTests++;
            System.out.println("✗ " + testName + " - Expected: not null, Actual: null");
        }
    }

    /**
     * Asserts that an object is null
     *
     * @param object the object to check
     * @param testName the name of the test
     */
    public static void assertNull(Object object, String testName) {
        totalTests++;
        if (object == null) {
            passedTests++;
            System.out.println("✓ " + testName);
        } else {
            failedTests++;
            System.out.println("✗ " + testName + " - Expected: null, Actual: " + object);
        }
    }

    /**
     * Asserts that a method throws a specific exception
     *
     * @param runnable the code to run
     * @param expectedException the expected exception class name
     * @param testName the name of the test
     */
    public static void assertThrows(Runnable runnable, String expectedException, String testName) {
        totalTests++;
        try {
            runnable.run();
            failedTests++;
            System.out.println("✗ " + testName + " - Expected exception: " + expectedException + ", but no exception was thrown");
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals(expectedException)) {
                passedTests++;
                System.out.println("✓ " + testName);
            } else {
                failedTests++;
                System.out.println("✗ " + testName + " - Expected exception: " + expectedException + ", Actual: " + e.getClass().getSimpleName());
            }
        }
    }

    /**
     * Asserts that a method does not throw an exception
     *
     * @param runnable the code to run
     * @param testName the name of the test
     */
    public static void assertDoesNotThrow(Runnable runnable, String testName) {
        totalTests++;
        try {
            runnable.run();
            passedTests++;
            System.out.println("✓ " + testName);
        } catch (Exception e) {
            failedTests++;
            System.out.println("✗ " + testName + " - Unexpected exception: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    /**
     * Resets the test counters
     */
    public static void reset() {
        totalTests = 0;
        passedTests = 0;
        failedTests = 0;
    }

    /**
     * Prints a summary of all test results
     */
    public static void printSummary() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("TEST SUMMARY");
        System.out.println("=".repeat(40));
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
        System.out.println("Success Rate: " + (totalTests > 0 ? String.format("%.1f%%", (double) passedTests / totalTests * 100) : "0%"));
        System.out.println("=".repeat(40));
    }
}