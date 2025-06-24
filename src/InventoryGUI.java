package src;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
        Button addButton = new Button("Add Product");

        Label addSectionTitle = new Label("Add New Product");
        addSectionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label updateSectionTitle = new Label("Update Inventory");
        updateSectionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label tableSectionTitle = new Label("Current Inventory");
        tableSectionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView<Product> tableView = new TableView<>();
        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Product, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());

        TableColumn<Product, Integer> thresholdCol = new TableColumn<>("Threshold");
        thresholdCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantityThreshold()).asObject());

        TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());

        tableView.getColumns().addAll(nameCol, qtyCol, thresholdCol, idCol);
        tableView.getItems().addAll(manager.getAllProducts());

        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                int threshold = Integer.parseInt(thresholdField.getText());
                int id = Integer.parseInt(idField.getText());
                manager.addProduct(name, qty, threshold, id);
                tableView.getItems().setAll(manager.getAllProducts());
                nameField.clear();
                qtyField.clear();
                thresholdField.clear();
                idField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter valid numbers.", Alert.AlertType.ERROR);
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
                tableView.getItems().setAll(manager.getAllProducts());
                updateIDField.clear();
                updateQtyField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid quantity to sell.",
                        Alert.AlertType.ERROR);
            }
            manager.saveInventory();
        });
        Button restockButton = new Button("Restock Product");
        restockButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(updateIDField.getText());
                int qty = Integer.parseInt(updateQtyField.getText());
                manager.restockProduct(id, qty);
                tableView.getItems().setAll(manager.getAllProducts());
                updateIDField.clear();
                updateQtyField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid quantity to restock.",
                        Alert.AlertType.ERROR);
            }
            manager.saveInventory();
        });

        tableView.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (product == null || empty) {
                    setStyle("");
                } else if (product.isLowStock()) {
                    setStyle("-fx-background-color: #ffe0e0;");
                } else {
                    setStyle("");
                }
            }
        });

        GridPane updateSection = new GridPane();
        updateSection.setHgap(10);
        updateSection.setVgap(10);

        updateSection.add(updateLabel, 0, 0);
        updateSection.add(updateIDLabel, 0, 1);
        updateSection.add(updateIDField, 1, 1);
        updateSection.add(updateQtyLabel, 0, 2);
        updateSection.add(updateQtyField, 1, 2);
        updateSection.add(sellButton, 0, 3);
        updateSection.add(restockButton, 1, 3);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);
        form.add(qtyLabel, 0, 1);
        form.add(qtyField, 1, 1);
        form.add(thresholdLabel, 0, 2);
        form.add(thresholdField, 1, 2);
        form.add(idLabel, 0, 3);
        form.add(idField, 1, 3);
        form.add(addButton, 1, 4);

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(addSectionTitle, form, tableSectionTitle, tableView, updateSectionTitle,
                updateSection);

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

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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