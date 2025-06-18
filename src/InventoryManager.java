package src;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;

/**
 * Manages the inventory of a product
 *
 * @author Chloe Nuzillat
 */
public class InventoryManager {
    private HashMap<Integer, Product> inventory;

    /**
     * Constructs the inventory manager
     */
    public InventoryManager() {
        inventory = new HashMap<>();
        loadInventory();
    }

    /**
     * Loads existing inventory from inventory.dat
     */
    @SuppressWarnings("unchecked")
    public void loadInventory() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("inventory.dat"))) {
            inventory = (HashMap<Integer, Product>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing inventory found. Starting fresh.");
            inventory = new HashMap<>();
        }
    }

    /**
     * Saves inventory changes to inventory.dat
     */
    public void saveInventory() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("inventory.dat"))) {
            out.writeObject(inventory);
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    /**
     * Adds a product to the inventory
     *
     * @param name the name of the product
     * @param quantity the amount of product in stock
     * @param quantityThreshold the threshold that determines if a product is low stock
     */
    public void addProduct(String name, int quantity, int quantityThreshold, int id) {
        inventory.put(id, new Product(name, quantity, quantityThreshold, id));
    }

    /**
     * Removes a certain amount of product from the inventory
     *
     * @param id the id of the product
     * @param quantity the amount of product in stock
     */
    public void sellProduct(int id, int quantity) {
        Product product = inventory.get(id);
        if (product != null) {
            product.sell(quantity);
            if (product.isLowStock()) {
                System.out.println("Low stock product: " + product.getName());
            }
        }
        else {
            System.out.println("Product not found");
        }
    }

    /**
     * Adds a certain amount of product to the inventory
     *
     * @param quantity the amount of product in stock
     * @param id the id of the product
     */
    public void restockProduct(int quantity, int id) {
        Product product = inventory.get(id);
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

    /**
     * Prints out all low stock items
     */
    public void showLowStockItems() {
        System.out.println("Low stock items:");
        for (Product product : inventory.values()) {
            if (product.isLowStock()) {
                System.out.println(product);
            }
        }
    }

    /**
     * Returns the entire inventory
     * @return all products
     */
    public Collection<Product> getAllProducts() {
        return inventory.values();
    }
}