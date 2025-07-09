# Testing Guide for Smart Inventory Tracker

## Overview

This project now includes a comprehensive testing suite using a custom SimpleTestFramework that doesn't require external dependencies.

## What Testing Entails

### 1. **Unit Testing**
- Testing individual methods and classes in isolation
- Verifying that each piece of code works correctly
- Catching bugs early in development

### 2. **Test Structure**
- **Test methods** - Individual test cases
- **Assertions** - Verifying expected vs actual results
- **Setup/Teardown** - Preparing and cleaning up test data
- **Test organization** - Grouping related tests together

### 3. **Assertions Available**
- **assertEquals()** - Checks if expected equals actual
- **assertTrue()/assertFalse()** - Checks boolean conditions
- **assertNotNull()/assertNull()** - Checks for null values
- **assertThrows()** - Verifies exceptions are thrown
- **assertDoesNotThrow()** - Verifies no exceptions occur

## Test Files

### 1. **SimpleTestFramework.java**
A lightweight testing framework that provides:
- Basic assertion methods
- Test result tracking
- Detailed reporting
- No external dependencies

### 2. **ProductTests.java**
Tests the Product class including:
- Constructor validation
- Getter methods
- Sell/restock operations
- Low stock detection
- Price and value calculations
- Category and description management

### 3. **InventoryManagerTests.java**
Tests the InventoryManager class including:
- Product management (add, delete, exists)
- Inventory operations (sell, restock)
- Search and filtering functionality
- Category management
- Total value calculations
- Edge cases

### 4. **TestRunner.java**
Main test runner that:
- Executes all test suites
- Provides comprehensive reporting
- Shows pass/fail statistics

## Running the Tests

### Option 1: Using TestRunner
```bash
javac src/*.java
java src.TestRunner
```

### Option 2: Using IDE
- Right-click on `TestRunner.java` in your IDE
- Select "Run TestRunner.main()"

### Option 3: Individual Test Suites
```bash
# Run only Product tests
java -cp src ProductTests

# Run only InventoryManager tests  
java -cp src InventoryManagerTests
```