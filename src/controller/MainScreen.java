package controller;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.*;

import java.io.IOException;

// Summary: This class acts as the controller for the main screen fxml file.
public class MainScreen
{
    private Inventory inventory = new Inventory();

    @FXML
    private TextField partsSearchTextField;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML
    private TableColumn<Part, String> partPPUColumn;
    @FXML
    private TextField productsSearchTextField;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML
    private TableColumn<Product, String> productPPUColumn;

    // Summary: This method is called when the user clicks the delete button underneath the parts table.
    @FXML
    void deleteSelectedPart()
    {
        // Pull up a confirmation dialog when the user wants to delete the selected part
        if (ErrorDialogs.deleteDialog())
        {
            // Get the part selected in the table view, and delete it from the inventory
            Part selectedPart = this.partsTable.getSelectionModel().getSelectedItem();
            this.inventory.deletePart(selectedPart);
        }
    }

    // Summary: This method is called when the user clicks the delete button underneath the products table.
    @FXML
    void deleteSelectedProduct()
    {
        // Pull up a confirmation dialog when the user wants to delete the selected product
        if (ErrorDialogs.deleteDialog())
        {
            // Get the product selected in the table view, and delete it from the inventory
            Product selectedProduct = this.productsTable.getSelectionModel().getSelectedItem();
            this.inventory.deleteProduct(selectedProduct);
        }
    }

    // Summary: This method is called when the user clicks the exit button on the main screen.
    @FXML
    void exitApplication()
    {
        // Call the JavaFX application exit function
        Platform.exit();
    }

    // Summary: This method is called when the user clicks the add button underneath the parts table.
    @FXML
    void openAddPart() throws IOException
    {
        // Create a new menu using the add part fxml file
        MenuContainer<AddPart> addPartMenu = new MenuContainer<>("../view/AddPart.fxml");
        // Populate the part ID field in the add part controller
        addPartMenu.getController().populateData(this.inventory.generatePartId());
        // Create the close request handler to pull the new part from the add part menu controller
        addPartMenu.setCloseAction(event ->
        {
            // Get the new part the user created in the add part menu
            final Part newPart = addPartMenu.getController().getNewPart();
            // Close the add part menu
            addPartMenu.getStage().close();
            // If the new part is null, then the user exited by canceling or clicking close. Otherwise, if the new part
            // is instantiated, add it to our inventory
            if (newPart != null) this.inventory.addPart(newPart);
        });
    }

    // Summary: This method is called when the user clicks the modify button underneath the parts table.
    @FXML
    void modifySelectedPart() throws IOException
    {
        // Get the selected part from the part table view
        Part selectedPart = this.partsTable.getSelectionModel().getSelectedItem();
        // If there is a part selected (the part is not null) then open the modify part menu
        if (selectedPart != null)
        {
            // Create a new menu using the modify part fxml file
            MenuContainer<ModifyPart> modifyPartMenu = new MenuContainer<>("../view/ModifyPart.fxml");
            // Get the index of the selected part so we know where to replace the selected part with the modified part
            int selectedPartIndex = inventory.getAllParts().indexOf(selectedPart);
            // Populate the text entry fields with the instance variable data from the selected part
            modifyPartMenu.getController().populateData(selectedPart);
            // Create the close request handler to pull the modified part from the modify part menu once closed
            modifyPartMenu.setCloseAction(event ->
            {
                // Get the modified part from the menu
                final Part modifiedPart = modifyPartMenu.getController().getModifiedPart();
                // If the modified part is null, then the user exited by canceling or clicking close. Otherwise, if the
                // modified part is instantiated, update the selected part in the inventory with the modified version
                // created in the menu
                if (modifiedPart != null) this.inventory.updatePart(selectedPartIndex, modifiedPart);
                // Close the modify part menu
                modifyPartMenu.getStage().close();
            });
        }
    }

    // Summary: This method is called when the user clicks the add button below the products table.
    @FXML
    void openAddProduct() throws IOException
    {
        // Create a new menu using the add product fxml file
        MenuContainer<AddProduct> addProductMenu = new MenuContainer<>("../view/AddProduct.fxml");
        // Populate the product ID field and the table view of all parts in the inventory in the add product controller
        addProductMenu.getController().populateData(this.inventory.generateProductId(), this.inventory.getAllParts());
        // Create the close request handler to pull the new product from the add product menu
        addProductMenu.setCloseAction(event ->
        {
            // Get the new product the user created in the add product menu
            final Product newProduct = addProductMenu.getController().getNewProduct();
            // Close the add product menu
            addProductMenu.getStage().close();
            // If the new product is null, then the user exited by canceling or clicking close. Otherwise, if the new
            // product is instantiated, add it to our inventory
            if (newProduct != null) this.inventory.addProduct(newProduct);
        });
    }

    // Summary: This method is called when the user clicks the modify button below the products table.
    @FXML
    void modifySelectedProduct() throws IOException
    {
        // Get the selected product from the product table view
        Product selectedProduct = this.productsTable.getSelectionModel().getSelectedItem();
        // If there is a product selected (the product is not null) then open the modify part menu
        if (selectedProduct != null)
        {
            // Create a new menu using the modify product fxml file
            MenuContainer<ModifyProduct> modifyProductMenu = new MenuContainer<>("../view/ModifyProduct.fxml");
            // Get the index of the selected product so we know where to replace the selected product with the modified
            // product
            int selectedProductIndex = inventory.getAllProducts().indexOf(selectedProduct);
            // Populate the text entry fields with the instance variable data from the selected part, and the table view
            // of all parts available in the inventory
            modifyProductMenu.getController().populateData(selectedProduct, this.inventory.getAllParts());
            // Create the close request handler to pull the modified product from the modify product menu once closed
            modifyProductMenu.getStage().setOnCloseRequest(event ->
            {
                // Get the modified product from the menu
                final Product modifiedProduct = modifyProductMenu.getController().getModifiedProduct();
                // If the modified product is null, then the user exited by canceling or clicking close. Otherwise, if
                // the modified product is instantiated, update the selected product in the inventory with the modified
                // version created in the menu
                if (modifiedProduct != null) this.inventory.updateProduct(selectedProductIndex, modifiedProduct);
                // Close the modify product menu
                modifyProductMenu.getStage().close();
            });
        }
    }

    // Summary: This method is called when the user clicks the search button above the parts table
    @FXML
    void partsSearch()
    {
        String searchText = this.partsSearchTextField.getText();
        ObservableList<Part> matches = FXCollections.observableArrayList();
        boolean isSearchForId = true;

        // Check if the search text is empty. If it is empty, we should display all parts. Otherwise, use the string to
        // search the parts table.
        if (!searchText.equals(""))
        {
            // Check if the search text is an integer. If so, we need to search for the part ID. Otherwise, we are
            // searching for the part name.
            try
            {
                Integer.parseInt(searchText);
            } catch (NumberFormatException e)
            {
                isSearchForId = false;
            }

            // If the search text is an integer, than we need to search through the part ID's
            if (isSearchForId)
            {
                for (Part part : this.inventory.getAllParts())
                {
                    // Take the part ID, convert it to a string, and see if the integer we are searching for is equal
                    // to any of the digits in each part ID
                    if (Integer.toString(part.getId()).contains(searchText))
                    {
                        matches.add(part);
                    }
                }
            }
            // Otherwise, search through all the part names to see if our search text is contained in any of the part
            // names
            else
            {
                for (Part part : this.inventory.getAllParts())
                {
                    if (part.getName().contains(searchText))
                    {
                        matches.add(part);
                    }
                }
            }
            // Finally, display all of our matches on the parts table
            this.partsTable.setItems(matches);
        } else
        {
            this.partsTable.setItems(this.inventory.getAllParts());
        }
    }

    // Summary: This method is called when the user clicks the search button above the products table
    @FXML
    void productSearch()
    {
        String searchText = this.productsSearchTextField.getText();
        ObservableList<Product> matches = FXCollections.observableArrayList();
        boolean isSearchForId = true;

        // Check if the search text is empty. If it is empty, we should display all products. Otherwise, use the string
        // to search the product table
        if (!searchText.equals(""))
        {
            // Check if the search text is an integer. If so, we need to search for the product ID. Otherwise, we are
            // searching for the product name.
            try
            {
                Integer.parseInt(searchText);
            } catch (NumberFormatException e)
            {
                isSearchForId = false;
            }

            // If the search text is an integer, than we need to search through the product ID's
            if (isSearchForId)
            {
                for (Product product : this.inventory.getAllProducts())
                {
                    // Take the product ID, convert it to a string, and see if the integer we are searching for is equal
                    // to any of the digits in each product ID
                    if (Integer.toString(product.getId()).contains(searchText))
                    {
                        matches.add(product);
                    }
                }
            }
            // Otherwise, search through all the product names to see if our search text is contained in any of the
            // product names
            else
            {
                for (Product product : this.inventory.getAllProducts())
                {
                    if (product.getName().contains(searchText))
                    {
                        matches.add(product);
                    }
                }
            }
            // Finally, display all of our matches on the products table
            this.productsTable.setItems(matches);
        } else
        {
            this.productsTable.setItems(this.inventory.getAllProducts());
        }
    }

    @FXML
    void initialize()
    {
        // SAMPLE DATA
        Part samplePart1 = new InHouse(1, "Big Screw", 2.50D, 100, 0, 255, 1034);
        Part samplePart2 = new InHouse(2, "Nut", 0.50D, 50, 0, 255, 1034);
        Part samplePart3 = new Outsourced(22, "Bolt", 2.00D, 65, 0, 255, "Bolt Co.");
        Part samplePart4 = new Outsourced(4, "Small Screw", 1.50D, 250, 0, 255, "Smallest Screws Inc.");
        inventory.addPart(samplePart1);
        inventory.addPart(samplePart2);
        inventory.addPart(samplePart3);
        inventory.addPart(samplePart4);

        Product sampleProduct1 = new Product(1, "Box o' Screws", 100, 0, 255, 6.00D);
        sampleProduct1.addAssociatedPart(samplePart1);
        sampleProduct1.addAssociatedPart(samplePart2);
        sampleProduct1.addAssociatedPart(samplePart3);
        sampleProduct1.addAssociatedPart(samplePart4);
        inventory.addProduct(sampleProduct1);
        // SAMPLE DATA

        // Specify how the product and part table views should populate columns with instance variables from both their
        // respective product and part instance variables
        this.partIdColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getId()));
        this.partNameColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getName()));
        this.partInventoryLevelColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getStock()));
        this.partPPUColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>("$" + param.getValue().getPrice()));
        this.partsTable.setItems(inventory.getAllParts());

        this.productIdColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getId()));
        this.productNameColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getName()));
        this.productInventoryLevelColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getStock()));
        this.productPPUColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>("$" + param.getValue().getPrice()));
        this.productsTable.setItems(inventory.getAllProducts());
    }
}
