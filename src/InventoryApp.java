package src;

/**
 * Starts the program
 *
 * @author Chloe Nuzillat
 */
public class InventoryApp {

    /**
     * The main method
     *
     * @param args given arguments
     */
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        manager.addProduct("Apples", 10, 5);
        manager.addProduct("Bananas", 20, 10);

        manager.showInventory();
        manager.sellProduct("Apples", 6);
        manager.restockProduct("Apples", 10);
        manager.showInventory();
    }
}
