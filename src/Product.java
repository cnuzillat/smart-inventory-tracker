package src;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class representation of a product
 *
 * @author Chloe Nuzillat
 */
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String name;
    private int quantity;
    private final int quantityThreshold;
    private final int id;
    private double price;
    private String category;
    private LocalDateTime lastUpdated;
    private String description;

    /**
     * Product constructor
     *
     * @param name the name of the product
     * @param quantity how much product is in stock
     * @param threshold what quantity amount is considered low stock
     * @param id the unique identifier for the product
     */
    public Product(String name, int quantity, int threshold, int id) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold cannot be negative");
        }
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        
        this.name = name.trim();
        this.quantity = quantity;
        this.quantityThreshold = threshold;
        this.id = id;
    }

    /**
     * Gets the name of the product
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the quantity of the product
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the id of the product
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets what quantity amount is considered low stock
     *
     * @return the threshold
     */
    public int getQuantityThreshold() {
        return quantityThreshold;
    }

    /**
     * Sells a certain amount of a product (reduces the quantity)
     *
     * @param amount the amount to sell
     * @throws IllegalArgumentException if amount is not positive or exceeds available stock
     */
    public void sell(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Sell amount must be positive");
        }
        if (amount > quantity) {
            throw new IllegalArgumentException("Cannot sell more than available stock");
        }
        quantity -= amount;
        lastUpdated = LocalDateTime.now();
    }

    /**
     * Restocks a certain amount of a product (adds to the quantity)
     *
     * @param amount the amount to restock
     */
    public void restock(int amount) {
        quantity = quantity + amount;
    }

    /**
     * Is an item considered low stock?
     *
     * @return if an item is low stock
     */
    public boolean isLowStock() {
        return quantity <= quantityThreshold;
    }

    /**
     * String representation of a product
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return name + ": " + quantity + " (Low threshold: " + quantityThreshold + ") - " + id;
    }

    /**
     * Gets the price of the product
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product
     *
     * @param price the new price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the category of the product
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the product
     *
     * @param category the new category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the last updated timestamp
     *
     * @return the last updated timestamp
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Gets the description of the product
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the product
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Updates the price of the product and sets the last updated timestamp
     *
     * @param newPrice the new price (must be non-negative)
     */
    public void updatePrice(double newPrice) {
        if (newPrice >= 0) {
            this.price = newPrice;
            this.lastUpdated = LocalDateTime.now();
        }
    }

    /**
     * Calculates the total value of the product (price * quantity)
     *
     * @return the total value
     */
    public double getTotalValue() {
        return price * quantity;
    }
}
