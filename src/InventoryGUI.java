package src;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

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
        nameCol.setPrefWidth(100);

        TableColumn<Product, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
        qtyCol.setPrefWidth(100);

        TableColumn<Product, Integer> thresholdCol = new TableColumn<>("Threshold");
        thresholdCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantityThreshold()).asObject());
        thresholdCol.setPrefWidth(100);

        TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        idCol.setPrefWidth(100);

        tableView.getColumns().addAll(nameCol, qtyCol, thresholdCol, idCol);
        tableView.getItems().addAll(manager.getAllProducts());

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

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
        Button restockButton = new Button("Restock Product");

        sellButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(updateIDField.getText());
                int qty = Integer.parseInt(updateQtyField.getText());
                manager.sellProduct(id, qty);
                tableView.getItems().setAll(manager.getAllProducts());
                updateIDField.clear();
                updateQtyField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid quantity to sell.", Alert.AlertType.ERROR);
            }
            manager.saveInventory();
        });

        restockButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(updateIDField.getText());
                int qty = Integer.parseInt(updateQtyField.getText());
                manager.restockProduct(id, qty);
                tableView.getItems().setAll(manager.getAllProducts());
                updateIDField.clear();
                updateQtyField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid quantity to restock.", Alert.AlertType.ERROR);
            }
            manager.saveInventory();
        });

        Label searchLabel = new Label("Search Products:");
        TextField searchField = new TextField();
        searchField.setPromptText("Enter product name to search...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Product> filteredProducts = manager.searchProducts(newValue);
            tableView.getItems().setAll(filteredProducts);
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

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    showProductDetails(selectedProduct);
                }
            }
        });

        Button exportButton = new Button("Export to CSV");
        exportButton.setOnAction(e -> exportToCSV(tableView.getItems()));

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                deleteProduct(selectedProduct.getId(), tableView);
            } else {
                showAlert("No Selection", "Please select a product to delete.", Alert.AlertType.WARNING);
            }
        });

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

        HBox searchBox = new HBox(10);
        searchBox.getChildren().addAll(searchLabel, searchField);
        
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(exportButton, deleteButton);

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(
            addSectionTitle, 
            form, 
            tableSectionTitle, 
            searchBox,
            tableView, 
            buttonBox,
            updateSectionTitle,
            updateSection
        );

        Scene scene = new Scene(layout, 600, 700);
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
     * Shows detailed information about a product
     */
    private void showProductDetails(Product product) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Product Details");
        alert.setHeaderText(product.getName());
        alert.setContentText(
            "ID: " + product.getId() + "\n" +
            "Name: " + product.getName() + "\n" +
            "Quantity: " + product.getQuantity() + "\n" +
            "Low Stock Threshold: " + product.getQuantityThreshold() + "\n" +
            "Low Stock: " + (product.isLowStock() ? "Yes" : "No")
        );
        alert.showAndWait();
    }

    /**
     * Exports inventory to CSV file
     */
    private void exportToCSV(List<Product> products) {
        try (FileWriter writer = new FileWriter("inventory_export.csv")) {
            writer.write("ID,Name,Quantity,Threshold,Low Stock\n");

            for (Product product : products) {
                writer.write(String.format("%d,%s,%d,%d,%s\n",
                    product.getId(),
                    product.getName(),
                    product.getQuantity(),
                    product.getQuantityThreshold(),
                    product.isLowStock() ? "Yes" : "No"
                ));
            }
            
            showAlert("Export Successful", "Inventory exported to inventory_export.csv",
                    Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Export Error", "Failed to export: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Deletes a product from inventory
     */
    private void deleteProduct(int id, TableView<Product> tableView) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Product");
        confirmAlert.setContentText("Are you sure you want to delete this product?");
        
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (manager.deleteProduct(id)) {
                    tableView.getItems().setAll(manager.getAllProducts());
                    showAlert("Success", "Product deleted successfully", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Product not found", Alert.AlertType.ERROR);
                }
            }
        });
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