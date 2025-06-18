package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InventoryGUI extends Application {
    InventoryManager manager = new InventoryManager();

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
            } catch (NumberFormatException ex) {
                inventoryArea.setText("Please enter valid numbers.");
            }
        });
        Label updateLabel = new Label("Update Existing Product:");
        Label updateNameLabel = new Label("Product Name:");
        TextField updateNameField = new TextField();
        Label updateQtyLabel = new Label("Quantity:");
        TextField updateQtyField = new TextField();
        Button sellButton = new Button("Sell Product");
        sellButton.setOnAction(e -> {
            String name = updateNameField.getText();
            try {
                int qty = Integer.parseInt(updateQtyField.getText());
                manager.sellProduct(name, qty);
                inventoryArea.setText(getInventoryText());
            } catch (NumberFormatException ex) {
                inventoryArea.setText("Please enter a valid quantity to sell.");
            }
        });
        Button restockButton = new Button("Restock Product");
        restockButton.setOnAction(e -> {
            String name = updateNameField.getText();
            try {
                int qty = Integer.parseInt(updateQtyField.getText());
                manager.restockProduct(name, qty);
                inventoryArea.setText(getInventoryText());
            } catch (NumberFormatException ex) {
                inventoryArea.setText("Please enter a valid quantity to restock.");
            }
        });
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(
                nameLabel, nameField,
                qtyLabel, qtyField,
                thresholdLabel, thresholdField,
                idLabel, idField,
                addButton, inventoryArea,
                updateLabel, updateNameLabel,
                updateNameField, updateQtyLabel,
                updateQtyField, sellButton,
                restockButton
        );
        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getInventoryText() {
        StringBuilder sb = new StringBuilder();
        for (Product p : manager.getAllProducts()) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}