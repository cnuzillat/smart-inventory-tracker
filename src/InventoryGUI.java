package src;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

/**
 * GUI for the inventory manager
 *
 * @author Chloe Nuzillat
 */
public class InventoryGUI extends Application {
    private InventoryManager manager = new InventoryManager();
    private TableView<Product> tableView;
    private VBox dashboard;
    private Label totalProductsLabel;
    private Label totalValueLabel;
    private Label lowStockLabel;
    private Label categoriesLabel;

    /**
     * Starts the JavaFX application
     *
     * @param primaryStage the primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Smart Inventory Tracker");

        ScrollPane scrollPane = createScrollableMainLayout();

        Scene scene = new Scene(scrollPane, 1200, 800);
        
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setMaximized(true);
        primaryStage.show();

        System.out.println("Inventory size: " + manager.getAllProducts().size());
        System.out.println("Inventory items: " + manager.getAllProducts());
    }

    /**
     * Creates a scrollable main layout for the application
     *
     * @return the scroll pane containing the main layout
     */
    private ScrollPane createScrollableMainLayout() {
        VBox mainLayout = createMainLayout();
        
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: #f8f9fa;");
        
        return scrollPane;
    }

    /**
     * Creates the main layout of the application
     *
     * @return the main VBox layout
     */
    private VBox createMainLayout() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setStyle("-fx-background-color: #f8f9fa;");

        HBox header = createHeader();

        VBox dashboard = createResponsiveDashboard();

        VBox contentArea = createResponsiveContentArea();
        
        mainLayout.getChildren().addAll(header, dashboard, contentArea);
        
        return mainLayout;
    }

    /**
     * Creates the header section of the application
     *
     * @return the header HBox
     */
    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 15, 0));
        
        Label title = new Label("Smart Inventory Tracker");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        header.getChildren().add(title);
        return header;
    }

    /**
     * Creates the responsive dashboard section
     *
     * @return the dashboard VBox
     */
    private VBox createResponsiveDashboard() {
        dashboard = new VBox(10);

        Label dashboardTitle = new Label("Dashboard Overview");
        dashboardTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #212529;");

        FlowPane dashboardGrid = new FlowPane(10, 10);
        dashboardGrid.setAlignment(Pos.CENTER_LEFT);
        
        List<Product> products = manager.getAllProducts();
        
        VBox totalProductsCard = createDashboardCard("Total Products", 
            String.valueOf(products.size()), "#007bff");
        totalProductsLabel = (Label) totalProductsCard.getChildren().get(0);
        
        VBox totalValueCard = createDashboardCard("Total Value", 
            "$" + String.format("%.2f", manager.getTotalInventoryValue()), "#28a745");
        totalValueLabel = (Label) totalValueCard.getChildren().get(0);
        
        VBox lowStockCard = createDashboardCard("Low Stock Items", 
            String.valueOf(products.stream().filter(Product::isLowStock).count()), "#ffc107");
        lowStockLabel = (Label) lowStockCard.getChildren().get(0);
        
        VBox categoriesCard = createDashboardCard("Categories", 
            String.valueOf(manager.getAllCategories().size()), "#6f42c1");
        categoriesLabel = (Label) categoriesCard.getChildren().get(0);
        
        dashboardGrid.getChildren().addAll(totalProductsCard, totalValueCard, lowStockCard, categoriesCard);
        
        dashboard.getChildren().addAll(dashboardTitle, dashboardGrid);
        return dashboard;
    }

    /**
     * Creates the responsive content area for the main application
     *
     * @return the content area VBox
     */
    private VBox createResponsiveContentArea() {
        VBox contentArea = new VBox(15);

        Label contentTitle = new Label("Inventory Management");
        contentTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #212529;");

        VBox responsiveLayout = new VBox(15);

        VBox leftPanel = createResponsiveLeftPanel();

        VBox rightPanel = createResponsiveRightPanel();

        responsiveLayout.getChildren().addAll(leftPanel, rightPanel);
        
        contentArea.getChildren().addAll(contentTitle, responsiveLayout);
        return contentArea;
    }

    /**
     * Creates the left panel for product management
     *
     * @return the left panel VBox
     */
    private VBox createResponsiveLeftPanel() {
        VBox leftPanel = new VBox(15);
        leftPanel.setStyle("-fx-background-color: white; -fx-background-radius: 8px; -fx-padding: 20px; -fx-effect: " +
                "dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        VBox addProductSection = createAddProductSection();

        VBox updateProductSection = createUpdateProductSection();

        VBox searchSection = createSearchSection();
        
        leftPanel.getChildren().addAll(addProductSection, updateProductSection, searchSection);
        return leftPanel;
    }

    /**
     * Creates the right panel for displaying the inventory table
     *
     * @return the right panel VBox
     */
    private VBox createResponsiveRightPanel() {
        VBox rightPanel = new VBox(15);
        rightPanel.setStyle("-fx-background-color: white; -fx-background-radius: 8px; -fx-padding: 20px; -fx-effect: " +
                "dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        
        Label title = new Label("Current Inventory");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #212529;");
        
        tableView = createTableView();
        
        rightPanel.getChildren().addAll(title, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        
        return rightPanel;
    }

    /**
     * Creates a dashboard card with specified label, value, and color
     *
     * @param label the label for the card
     * @param value the value to display
     * @param color the background color
     * @return the dashboard card VBox
     */
    private VBox createDashboardCard(String label, String value, String color) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-padding: 15px; -fx-effect: " +
                "dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        card.setAlignment(Pos.CENTER);
        card.setMinWidth(150);
        card.setMaxWidth(200);
        card.setPrefHeight(80);
        
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");
        
        Label titleLabel = new Label(label);
        titleLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #6c757d;");
        
        card.getChildren().addAll(valueLabel, titleLabel);
        return card;
    }

    /**
     * Creates the add product section
     *
     * @return the add product section VBox
     */
    private VBox createAddProductSection() {
        VBox section = new VBox(15);
        section.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 6px; -fx-padding: 15px;");
        
        Label title = new Label("Add New Product");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #212529;");
        
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        TextField nameField = createStyledTextField("Product Name");
        TextField qtyField = createStyledTextField("Quantity");
        TextField thresholdField = createStyledTextField("Low Stock Threshold");
        TextField idField = createStyledTextField("Product ID");
        TextField priceField = createStyledTextField("Price");
        TextField categoryField = createStyledTextField("Category");
        
        Button addButton = new Button("Add Product");
        addButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 6px; " +
                "-fx-padding: 10px 20px; -fx-font-weight: bold;");

        form.add(new Label("Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Quantity:"), 0, 1);
        form.add(qtyField, 1, 1);
        form.add(new Label("Threshold:"), 0, 2);
        form.add(thresholdField, 1, 2);
        form.add(new Label("ID:"), 0, 3);
        form.add(idField, 1, 3);
        form.add(new Label("Price:"), 0, 4);
        form.add(priceField, 1, 4);
        form.add(new Label("Category:"), 0, 5);
        form.add(categoryField, 1, 5);
        form.add(addButton, 1, 6);

        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                int threshold = Integer.parseInt(thresholdField.getText());
                int id = Integer.parseInt(idField.getText());
                double price = Double.parseDouble(priceField.getText());
                String category = categoryField.getText();
                
                manager.addProduct(name, qty, threshold, id);

                List<Product> products = manager.getAllProducts();
                Product product = products.stream()
                    .filter(p -> p.getId() == id)
                    .findFirst()
                    .orElse(null);
                
                if (product != null) {
                    product.setPrice(price);
                    product.setCategory(category);
                }

                nameField.clear();
                qtyField.clear();
                thresholdField.clear();
                idField.clear();
                priceField.clear();
                categoryField.clear();

                refreshTable();
                refreshDashboard();
                showAlert("Success", "Product added successfully!", Alert.AlertType.INFORMATION);
                
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter valid numbers.", Alert.AlertType.ERROR);
            }
            manager.saveInventory();
        });
        
        section.getChildren().addAll(title, form);
        return section;
    }

    /**
     * Creates the update product section
     *
     * @return the update product section VBox
     */
    private VBox createUpdateProductSection() {
        VBox section = new VBox(15);
        section.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 6px; -fx-padding: 15px;");
        
        Label title = new Label("Update Inventory");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #212529;");
        
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        
        TextField updateIdField = createStyledTextField("Product ID");
        TextField updateQtyField = createStyledTextField("Quantity");
        
        HBox buttonBox = new HBox(10);
        Button sellButton = new Button("Sell Product");
        Button restockButton = new Button("Restock Product");
        
        sellButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 6px; " +
                "-fx-padding: 10px 20px; -fx-font-weight: bold;");
        restockButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 6px; " +
                "-fx-padding: 10px 20px; -fx-font-weight: bold;");
        
        buttonBox.getChildren().addAll(sellButton, restockButton);
        
        form.add(new Label("Product ID:"), 0, 0);
        form.add(updateIdField, 1, 0);
        form.add(new Label("Quantity:"), 0, 1);
        form.add(updateQtyField, 1, 1);
        form.add(buttonBox, 1, 2);

        sellButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(updateIdField.getText());
                int qty = Integer.parseInt(updateQtyField.getText());
                manager.sellProduct(id, qty);
                refreshTable();
                refreshDashboard();
                updateIdField.clear();
                updateQtyField.clear();
                showAlert("Success", "Product sold successfully!", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter valid numbers.", Alert.AlertType.ERROR);
            }
            manager.saveInventory();
        });
        
        restockButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(updateIdField.getText());
                int qty = Integer.parseInt(updateQtyField.getText());
                manager.restockProduct(qty, id);
                refreshTable();
                refreshDashboard();
                updateIdField.clear();
                updateQtyField.clear();
                showAlert("Success", "Product restocked successfully!", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter valid numbers.", Alert.AlertType.ERROR);
            }
            manager.saveInventory();
        });
        
        section.getChildren().addAll(title, form);
        return section;
    }

    /**
     * Creates the search section
     *
     * @return the search section VBox
     */
    private VBox createSearchSection() {
        VBox section = new VBox(15);
        section.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 6px; -fx-padding: 15px;");
        
        Label title = new Label("Search & Actions");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #212529;");
        
        TextField searchField = createStyledTextField("Search products...");
        searchField.setPromptText("Enter product name to search...");
        
        ComboBox<String> categoryFilter = new ComboBox<>();
        categoryFilter.setPromptText("Filter by category");
        categoryFilter.getItems().addAll("All Categories");
        categoryFilter.getItems().addAll(manager.getAllCategories());

        VBox buttonBox = new VBox(8);
        Button exportButton = new Button("Export CSV");
        Button refreshButton = new Button("Refresh");
        Button deleteButton = new Button("Delete Selected");
        
        exportButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-background-radius: 6px; " +
                "-fx-padding: 10px 20px; -fx-font-weight: bold;");
        refreshButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-background-radius: 6px; " +
                "-fx-padding: 10px 20px; -fx-font-weight: bold;");
        deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 6px; " +
                "-fx-padding: 10px 20px; -fx-font-weight: bold;");
        
        buttonBox.getChildren().addAll(exportButton, refreshButton, deleteButton);

        searchField.textProperty().addListener((observable, oldValue,
                                                newValue) -> {
            List<Product> filteredProducts = manager.searchProducts(newValue);
            updateTableItems(filteredProducts);
        });

        categoryFilter.setOnAction(e -> {
            String selectedCategory = categoryFilter.getValue();
            if (selectedCategory != null && !selectedCategory.equals("All Categories")) {
                List<Product> filteredProducts = manager.getAllProducts().stream()
                    .filter(product -> selectedCategory.equals(product.getCategory()))
                    .collect(java.util.stream.Collectors.toList());
                updateTableItems(filteredProducts);
            } else {
                refreshTable();
            }
        });

        exportButton.setOnAction(e -> exportToCSV(getCurrentTableItems()));

        refreshButton.setOnAction(e -> {
            refreshTable();
            refreshDashboard();
        });

        deleteButton.setOnAction(e -> {
            Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                deleteProduct(selectedProduct.getId());
            } else {
                showAlert("No Selection", "Please select a product to delete.", Alert.AlertType.WARNING);
            }
        });
        
        section.getChildren().addAll(title, searchField, categoryFilter, buttonBox);
        return section;
    }

    /**
     * Creates the main table view for displaying products
     *
     * @return the configured TableView
     */
    private TableView<Product> createTableView() {
        TableView<Product> tableView = new TableView<>();
        tableView.setPrefHeight(400);
        tableView.setMinHeight(200);

        TableColumn<Product, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        idCol.setPrefWidth(60);
        idCol.setMinWidth(50);
        
        TableColumn<Product, String> nameCol = new TableColumn<>("Product Name");
        nameCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setPrefWidth(150);
        nameCol.setMinWidth(100);
        
        TableColumn<Product, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        qtyCol.setPrefWidth(80);
        qtyCol.setMinWidth(60);
        
        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        priceCol.setPrefWidth(80);
        priceCol.setMinWidth(60);
        
        TableColumn<Product, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCategory()));
        categoryCol.setPrefWidth(100);
        categoryCol.setMinWidth(80);
        
        TableColumn<Product, Double> valueCol = new TableColumn<>("Total Value");
        valueCol.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getTotalValue()).asObject());
        valueCol.setPrefWidth(100);
        valueCol.setMinWidth(80);
        
        TableColumn<Product, Integer> thresholdCol = new TableColumn<>("Threshold");
        thresholdCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuantityThreshold()).asObject());
        thresholdCol.setPrefWidth(80);
        thresholdCol.setMinWidth(60);

        tableView.getColumns().addAll(idCol, nameCol, qtyCol, priceCol, categoryCol, valueCol, thresholdCol);

        List<Product> products = manager.getAllProducts();
        System.out.println("Loading " + products.size() + " products into table");
        tableView.getItems().addAll(products);

        tableView.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (product == null || empty) {
                    setStyle("");
                }
                else if (product.isLowStock()) {
                    setStyle("-fx-background-color: #fff3cd;");
                }
                else {
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
        
        return tableView;
    }

    /**
     * Creates a styled text field with the given prompt
     *
     * @param prompt the placeholder text
     * @return the styled TextField
     */
    private TextField createStyledTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setPrefHeight(30);
        textField.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 4px; " +
                "-fx-padding: 6px 10px;");
        return textField;
    }

    /**
     * Shows detailed information about a product in a popup window
     *
     * @param product the product to show details for
     */
    private void showProductDetails(Product product) {
        Stage detailStage = new Stage();
        detailStage.initModality(Modality.APPLICATION_MODAL);
        detailStage.setTitle("Product Details - " + product.getName());
        
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setPrefWidth(400);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label(product.getName());
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Circle statusIndicator = new Circle(6);
        statusIndicator.setFill(product.isLowStock() ? Color.RED : Color.GREEN);
        
        header.getChildren().addAll(title, new Label("  "), statusIndicator);

        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(10);
        detailsGrid.setVgap(8);
        
        detailsGrid.add(new Label("Product ID:"), 0, 0);
        detailsGrid.add(new Label(String.valueOf(product.getId())), 1, 0);
        
        detailsGrid.add(new Label("Quantity:"), 0, 1);
        detailsGrid.add(new Label(String.valueOf(product.getQuantity())), 1, 1);
        
        detailsGrid.add(new Label("Price:"), 0, 2);
        detailsGrid.add(new Label("$" + String.format("%.2f", product.getPrice())), 1, 2);
        
        detailsGrid.add(new Label("Category:"), 0, 3);
        detailsGrid.add(new Label(product.getCategory() != null ? product.getCategory() : "N/A"), 1,
                3);
        
        detailsGrid.add(new Label("Total Value:"), 0, 4);
        detailsGrid.add(new Label("$" + String.format("%.2f", product.getTotalValue())), 1, 4);
        
        detailsGrid.add(new Label("Low Stock Threshold:"), 0, 5);
        detailsGrid.add(new Label(String.valueOf(product.getQuantityThreshold())), 1, 5);
        
        detailsGrid.add(new Label("Status:"), 0, 6);
        detailsGrid.add(new Label(product.isLowStock() ? "Low Stock" : "In Stock"), 1, 6);

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> detailStage.close());
        
        buttonBox.getChildren().add(closeButton);
        
        layout.getChildren().addAll(header, detailsGrid, buttonBox);
        
        Scene scene = new Scene(layout);
        detailStage.setScene(scene);
        detailStage.showAndWait();
    }

    /**
     * Shows an alert dialog with the specified title, message, and type
     *
     * @param title the alert title
     * @param message the alert message
     * @param type the alert type
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Exports the current product list to a CSV file
     *
     * @param products the list of products to export
     */
    private void exportToCSV(List<Product> products) {
        try (FileWriter writer = new FileWriter("inventory_export.csv")) {
            writer.write("ID,Name,Quantity,Price,Category,Threshold,Low Stock,Total Value\n");

            for (Product product : products) {
                writer.write(String.format("%d,%s,%d,%.2f,%s,%d,%s,%.2f\n",
                    product.getId(),
                    product.getName(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getCategory() != null ? product.getCategory() : "",
                    product.getQuantityThreshold(),
                    product.isLowStock() ? "Yes" : "No",
                    product.getTotalValue()
                ));
            }
            
            showAlert("Export Successful", "Inventory exported to inventory_export.csv",
                    Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Export Error", "Failed to export: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Deletes a product with the specified ID
     *
     * @param id the ID of the product to delete
     */
    private void deleteProduct(int id) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Product");
        confirmAlert.setContentText("Are you sure you want to delete this product?");
        
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (manager.deleteProduct(id)) {
                    refreshTable();
                    refreshDashboard();
                    showAlert("Success", "Product deleted successfully", Alert.AlertType.INFORMATION);
                }
                else {
                    showAlert("Error", "Product not found", Alert.AlertType.ERROR);
                }
            }
        });
    }

    /**
     * Refreshes the table view with current data
     */
    private void refreshTable() {
        if (tableView != null) {
            tableView.getItems().clear();
            List<Product> products = manager.getAllProducts();
            tableView.getItems().addAll(products);
        }
    }

    /**
     * Updates the table items with the provided product list
     *
     * @param products the list of products to display
     */
    private void updateTableItems(List<Product> products) {
        if (tableView != null) {
            tableView.getItems().clear();
            tableView.getItems().addAll(products);
        }
    }

    private List<Product> getCurrentTableItems() {
        if (tableView != null) {
            return new ArrayList<>(tableView.getItems());
        }
        return manager.getAllProducts();
    }

    private void refreshDashboard() {
        if (totalProductsLabel != null && totalValueLabel != null && 
            lowStockLabel != null && categoriesLabel != null) {
            
            List<Product> products = manager.getAllProducts();

            totalProductsLabel.setText(String.valueOf(products.size()));
            totalValueLabel.setText("$" + String.format("%.2f", manager.getTotalInventoryValue()));
            lowStockLabel.setText(String.valueOf(products.stream().filter(Product::isLowStock).count()));
            categoriesLabel.setText(String.valueOf(manager.getAllCategories().size()));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}