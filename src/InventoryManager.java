package src;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * Manages the inventory of products
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
     * @param id the unique identifier for the product
     */
    public void addProduct(String name, int quantity, int quantityThreshold, int id) {
        inventory.put(id, new Product(name, quantity, quantityThreshold, id));
    }

    /**
     * Removes a certain amount of product from the inventory
     *
     * @param id the id of the product
     * @param quantity the amount of product to sell
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
     * @param quantity the amount of product to restock
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
     * Shows all products that are low on stock
     */
    public void showLowStockItems() {
        for (Product product : inventory.values()) {
            if (product.isLowStock()) {
                System.out.println("Low stock: " + product.getName() + " - Quantity: " + product.getQuantity());
            }
        }
    }

    /**
     * Gets all products in the inventory
     *
     * @return a list of all products
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(inventory.values());
    }

    /**
     * Searches for products by name
     *
     * @param searchTerm the term to search for
     * @return a list of products matching the search term
     */
    public List<Product> searchProducts(String searchTerm) {
        return inventory.values().stream()
                .filter(product -> product.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Gets all products in a specific category
     *
     * @param category the category to filter by
     * @return a list of products in the specified category
     */
    public List<Product> getProductsByCategory(String category) {
        return inventory.values().stream()
                .filter(product -> category.equals(product.getCategory()))
                .collect(Collectors.toList());
    }

    /**
     * Deletes a product from the inventory
     *
     * @param id the id of the product to delete
     * @return true if the product was deleted, false if not found
     */
    public boolean deleteProduct(int id) {
        Product removed = inventory.remove(id);
        return removed != null;
    }

    /**
     * Checks if a product exists in the inventory
     *
     * @param id the id of the product to check
     * @return true if the product exists, false otherwise
     */
    public boolean productExists(int id) {
        return inventory.containsKey(id);
    }

    /**
     * Gets all unique categories in the inventory
     *
     * @return a list of all categories
     */
    public List<String> getAllCategories() {
        return inventory.values().stream()
                .map(Product::getCategory)
                .filter(category -> category != null && !category.trim().isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total value of all products in the inventory
     *
     * @return the total inventory value
     */
    public double getTotalInventoryValue() {
        return inventory.values().stream()
                .mapToDouble(Product::getTotalValue)
                .sum();
    }
}