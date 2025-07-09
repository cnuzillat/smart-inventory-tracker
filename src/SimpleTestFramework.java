package src;

import java.util.ArrayList;
import java.util.List;
public class SimpleTestFramework {
    
    private static List<TestResult> testResults = new ArrayList<>();
    private static int totalTests = 0;
    private static int passedTests = 0;
    static int failedTests = 0;

    private static class TestResult {
        String testName;
        boolean passed;
        String message;
        String expected;
        String actual;
        
        TestResult(String testName, boolean passed, String message, String expected, String actual) {
            this.testName = testName;
            this.passed = passed;
            this.message = message;
            this.expected = expected;
            this.actual = actual;
        }
    }

    public static void assertEquals(Object expected, Object actual, String testName) {
        totalTests++;
        boolean passed = (expected == null && actual == null) || 
                        (expected != null && expected.equals(actual));
        
        if (passed) {
            passedTests++;
            System.out.println("PASS: " + testName);
        } else {
            failedTests++;
            String expectedStr = expected != null ? expected.toString() : "null";
            String actualStr = actual != null ? actual.toString() : "null";
            testResults.add(new TestResult(testName, false, "Values not equal", expectedStr, actualStr));
            System.out.println("FAIL: " + testName + " - Expected: " + expectedStr + ", Actual: " + actualStr);
        }
    }

    public static void assertTrue(boolean condition, String testName) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.println("PASS: " + testName);
        } else {
            failedTests++;
            testResults.add(new TestResult(testName, false, "Condition was false", "true",
                    "false"));
            System.out.println("FAIL: " + testName + " - Condition was false");
        }
    }

    public static void assertFalse(boolean condition, String testName) {
        totalTests++;
        if (!condition) {
            passedTests++;
            System.out.println("PASS: " + testName);
        } else {
            failedTests++;
            testResults.add(new TestResult(testName, false, "Condition was true", "false",
                    "true"));
            System.out.println("FAIL: " + testName + " - Condition was true");
        }
    }

    public static void assertNotNull(Object obj, String testName) {
        totalTests++;
        if (obj != null) {
            passedTests++;
            System.out.println("PASS: " + testName);
        } else {
            failedTests++;
            testResults.add(new TestResult(testName, false, "Object was null", "not null",
                    "null"));
            System.out.println("FAIL: " + testName + " - Object was null");
        }
    }

    public static void assertNull(Object obj, String testName) {
        totalTests++;
        if (obj == null) {
            passedTests++;
            System.out.println("PASS: " + testName);
        } else {
            failedTests++;
            testResults.add(new TestResult(testName, false, "Object was not null", "null",
                    "not null"));
            System.out.println("FAIL: " + testName + " - Object was not null");
        }
    }

    public static void assertThrows(Runnable runnable, String expectedException, String testName) {
        totalTests++;
        try {
            runnable.run();
            failedTests++;
            testResults.add(new TestResult(testName, false, "No exception thrown", expectedException,
                    "no exception"));
            System.out.println("FAIL: " + testName + " - Expected " + expectedException + " but no exception was thrown");
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals(expectedException) || 
                e.getClass().getName().contains(expectedException)) {
                passedTests++;
                System.out.println("PASS: " + testName);
            } else {
                failedTests++;
                testResults.add(new TestResult(testName, false, "Wrong exception type", expectedException,
                        e.getClass().getSimpleName()));
                System.out.println("FAIL: " + testName + " - Expected " + expectedException + " but got " +
                        e.getClass().getSimpleName());
            }
        }
    }

    public static void assertDoesNotThrow(Runnable runnable, String testName) {
        totalTests++;
        try {
            runnable.run();
            passedTests++;
            System.out.println("PASS: " + testName);
        } catch (Exception e) {
            failedTests++;
            testResults.add(new TestResult(testName, false, "Exception was thrown",
                    "no exception", e.getClass().getSimpleName()));
            System.out.println("FAIL: " + testName + " - Unexpected exception: " + e.getClass().getSimpleName());
        }
    }

    public static void printSummary() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("TEST SUMMARY");
        System.out.println("=".repeat(50));
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
        System.out.println("Success Rate: " + (totalTests > 0 ? String.format("%.1f%%",
                (double)passedTests/totalTests*100) : "0%"));
        
        if (failedTests > 0) {
            System.out.println("\nFAILED TESTS:");
            System.out.println("-".repeat(30));
            for (TestResult result : testResults) {
                if (!result.passed) {
                    System.out.println("• " + result.testName);
                    System.out.println("  Expected: " + result.expected);
                    System.out.println("  Actual: " + result.actual);
                    System.out.println();
                }
            }
        }
        
        System.out.println("=".repeat(50));
    }

    public static void reset() {
        testResults.clear();
        totalTests = 0;
        passedTests = 0;
        failedTests = 0;
    }
} 