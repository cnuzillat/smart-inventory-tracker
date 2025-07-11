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
     *
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void updatePrice(double newPrice) {
        if (newPrice >= 0) {
            this.price = newPrice;
            this.lastUpdated = LocalDateTime.now();
        }
    }

    public double getTotalValue() {
        return price * quantity;
    }
}
