package src;

import java.io.Serial;
import java.io.Serializable;

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
        if (amount <= quantity) {
            quantity -= amount;
        } else {
            System.out.println("Not enough stock to sell " + amount + " of " + name);
        }
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
}
