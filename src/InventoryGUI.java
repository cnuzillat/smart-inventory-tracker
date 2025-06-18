package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * GUI for the inventory manager
 *
 * @author Chloe Nuzillat
 */
public class InventoryGUI extends Application {
    InventoryManager manager = new InventoryManager();

    /**
     * Starts the GUI by creating an area to add new products and edit existing ones
     *
     * @param primaryStage the stage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory Manager");
        Label nameLabel = new Label("Product Name:");
        TextField nameField = new TextField();
        Label qtyLabel = new Label("Quantity:");
        TextField qtyField = new TextField();
        Label thresholdLabel = new Label("Low Stock Threshold:");
        TextField thresholdField = new TextField();
        Label idLabel = new Label("Product ID:");
        TextField idField = new TextField();
        TextArea inventoryArea = new TextArea();
        inventoryArea.setEditable(false);
        inventoryArea.setText(getInventoryText());
        Button addButton = new Button("Add Product");
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                int threshold = Integer.parseInt(thresholdField.getText());
                int id = Integer.parseInt(idField.getText());
                manager.addProduct(name, qty, threshold, id);
                inventoryArea.setText(getInventoryText());
                nameField.clear();
                qtyField.clear();
                thresholdField.clear();
                idField.clear();
            } catch (NumberFormatException ex) {
                inventoryArea.setText("Please enter valid numbers.");
            }
            manager.saveInventory();
        });
        Label updateLabel = new Label("Update Existing Product:");
        Label updateIDLabel = new Label("Product ID:");
        TextField updateIDField = new TextField();
        Label updateQtyLabel = new Label("Quantity:");
        TextField updateQtyField = new TextField();
        Button sellButton = new Button("Sell Product");
        sellButton.setOnAction(e -> {
            int id = Integer.parseInt(updateIDField.getText());
            try {
                int qty = Integer.parseInt(updateQtyField.getText());
                manager.sellProduct(id, qty);
                inventoryArea.setText(getInventoryText());
                updateIDField.clear();
                updateQtyField.clear();
            } catch (NumberFormatException ex) {
                inventoryArea.setText("Please enter a valid quantity to sell.");
            }
            manager.saveInventory();
        });
        Button restockButton = new Button("Restock Product");
        restockButton.setOnAction(e -> {
            int id = Integer.parseInt(updateIDField.getText());
            try {
                int qty = Integer.parseInt(updateQtyField.getText());
                manager.restockProduct(id, qty);
                inventoryArea.setText(getInventoryText());
                updateIDField.clear();
                updateQtyField.clear();
            } catch (NumberFormatException ex) {
                inventoryArea.setText("Please enter a valid quantity to restock.");
            }
            manager.saveInventory();
        });
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(
                nameLabel, nameField,
                qtyLabel, qtyField,
                thresholdLabel, thresholdField,
                idLabel, idField,
                addButton, inventoryArea,
                updateLabel, updateIDLabel,
                updateIDField, updateQtyLabel,
                updateQtyField, sellButton,
                restockButton
        );
        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
            manager.saveInventory();
        });
        primaryStage.show();
    }

    /**
     * Helper method for the start method that return the entire inventory in a String
     *
     * @return the inventory
     */
    private String getInventoryText() {
        StringBuilder sb = new StringBuilder();
        for (Product p : manager.getAllProducts()) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }

    /**
     * The main method
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}