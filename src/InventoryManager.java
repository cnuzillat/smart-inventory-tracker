package src;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

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
     * @return all products as a List
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(inventory.values());
    }

    /**
     * Searches for products by name
     * @param searchTerm the search term
     * @return list of matching products
     */
    public List<Product> searchProducts(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return new ArrayList<>(inventory.values());
        }
        
        return inventory.values().stream()
            .filter(p -> p.getName().toLowerCase().contains(searchTerm.toLowerCase().trim()))
            .collect(Collectors.toList());
    }

    public List<Product> getProductsByCategory(String category) {
        return inventory.values().stream()
            .filter(p -> category.equals(p.getCategory()))
            .collect(Collectors.toList());
    }

    /**
     * Deletes a product from inventory
     * @param id the product ID to delete
     * @return true if product was deleted, false if not found
     */
    public boolean deleteProduct(int id) {
        Product removed = inventory.remove(id);
        if (removed != null) {
            saveInventory();
            return true;
        }
        return false;
    }

    public boolean productExists(int id) {
        return inventory.containsKey(id);
    }

    public List<String> getAllCategories() {
        return inventory.values().stream()
            .map(Product::getCategory)
            .distinct()
            .collect(Collectors.toList());
    }

    public double getTotalInventoryValue() {
        return inventory.values().stream()
            .mapToDouble(Product::getTotalValue)
            .sum();
    }
}