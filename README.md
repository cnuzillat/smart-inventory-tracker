# Smart Inventory Tracker

A Java-based inventory management system with file-based storage and comprehensive testing.

## Features

- **Product Management**: Add, update, delete, and search products
- **Inventory Tracking**: Monitor quantities and low stock alerts
- **File-based Storage**: Data persists between application runs
- **Modern GUI**: JavaFX-based user interface
- **Comprehensive Testing**: Custom testing framework with full test coverage

## Technology Stack

- **Java 17** - Core programming language
- **JavaFX** - GUI framework
- **File-based Storage** - Simple .dat file persistence
- **Custom Testing Framework** - Comprehensive unit testing

## Setup and Installation

### Prerequisites
- Java 17 or higher

### Compilation
```bash
javac src/*.java
```

### Running the Application
```bash
java -cp src src.InventoryGUI
```

### Running Tests
```bash
java -cp src src.TestRunner
```

## Project Structure

```
smart-inventory-tracker/
├── src/
│   ├── Product.java                    # Core product model
│   ├── InventoryManager.java           # File-based inventory manager
│   ├── InventoryGUI.java              # Main GUI application
│   ├── styles.css                     # GUI styling
│   ├── SimpleTestFramework.java       # Custom testing framework
│   ├── ProductTests.java              # Product tests
│   ├── InventoryManagerTests.java     # Inventory manager tests
│   └── TestRunner.java                # Test runner
├── inventory.dat                      # Data file (created automatically)
├── README.md                          # Project documentation
├── .gitignore                         # Git ignore rules
└── LICENSE                            # Project license
```

## Author

Chloe Nuzillat

## License

This project is licensed under the MIT License - see the LICENSE file for details.
