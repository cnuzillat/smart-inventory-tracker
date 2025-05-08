package src;

import java.util.HashMap;

/**
 * Manages the inventory of a product
 *
 * @author Chloe Nuzillat
 */
public class InventoryManager {
    private HashMap<String, Product> inventory;

    /**
     * Constructs the inventory manager
     */
    public InventoryManager() {
        inventory = new HashMap<>();
    }

    /**
     * Adds a product to the inventory
     *
     * @param name the name of the product
     * @param quantity the amount of product in stock
     * @param quantityThreshold the threshold that determines if a product is low stock
     */
    public void addProduct(String name, int quantity, int quantityThreshold) {
        inventory.put(name, new Product(name, quantity, quantityThreshold));
    }

    /**
     * Removes a certain amount of product from the inventory
     *
     * @param name the name of the product
     * @param quantity the amount of product in stock
     */
    public void sellProduct(String name, int quantity) {
        Product product = inventory.get(name);
        if (product != null) {
            product.sell(quantity);
            if (product.isLowStock()) {
                System.out.println("Low stock product: " + name);
            }
        }
    }

    /**
     * Adds a certain amount of product to the inventory
     *
     * @param name the name of the product
     * @param quantity the amount of product in stock
     */
    public void restockProduct(String name, int quantity) {
        Product product = inventory.get(name);
        if (product != null) {
            product.restock(quantity);
        }
    }

    /**
     * Prints out the entire inventory to the user
     */
    public void showInventory() {
        for (Product product : inventory.values()) {
            System.out.println(product);
        }
    }
}