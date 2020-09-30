package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.Part;
import model.Product;

// Summary: This class acts as the controller for the add product fxml file.
public class AddProduct
{
    private int id;
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private Product newProduct;

    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField productInventoryTextField;
    @FXML
    private TextField productPriceTextField;
    @FXML
    private TextField productMinTextField;
    @FXML
    private TextField productMaxTextField;
    @FXML
    private TableView<Part> allPartsTable;
    @FXML
    private TableColumn<Part, Integer> allPartsTableIdColumn;
    @FXML
    private TableColumn<Part, String> allPartsTableNameColumn;
    @FXML
    private TableColumn<Part, Integer> allPartsTableInventoryColumn;
    @FXML
    private TableColumn<Part, String> allPartsTablePPUColumn;
    @FXML
    private TextField searchAllPartsTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> assocPartsTableIdColumn;
    @FXML
    private TableColumn<Part, String> assocPartsTableNameColumn;
    @FXML
    private TableColumn<Part, Integer> assocPartsTableInventoryColumn;
    @FXML
    private TableColumn<Part, String> assocPartsTablePPUColumn;

    // Summary: This method is called when the user clicks the add button below the table of all available parts
    @FXML
    void addSelectedPartToProduct()
    {
        // Get the part selected in the table of all parts
        Part selectedPart = this.allPartsTable.getSelectionModel().getSelectedItem();
        // If the table of associated parts does not contain the selected part, then add it to the list of associated
        // parts
        if(!this.associatedParts.contains(selectedPart))
        {
            this.associatedParts.add(selectedPart);
        }
    }

    // Summary: This method is called when the user clicks the delete button below the table of all associated parts
    @FXML
    void deleteSelectedAssociatedPart()
    {
        // Get the part selected in the table of associated parts
        Part selectedAssociatedPart = this.associatedPartsTable.getSelectionModel().getSelectedItem();
        // If the user accepts a confirmation dialog to delete, then add the part back to the list of all parts (if it
        // does not already exist there) and remove the part from the list of associated parts
        if(ErrorDialogs.deleteDialog())
        {
            this.associatedParts.remove(selectedAssociatedPart);
            if(!this.allParts.contains(selectedAssociatedPart)) this.allParts.add(selectedAssociatedPart);
        }
    }

    // Summary: This method is called when the user clicks the cancel button
    @FXML
    void cancelNewProduct()
    {
        // Fire a close window request if the user accepts a cancel confirmation dialog
        if (ErrorDialogs.cancelDialog())
        {
            Window window = cancelButton.getScene().getWindow();
            window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }

    // Summary: This method is called when the user clicks the save button
    @FXML
    void saveNewProduct()
    {
        try
        {
            // Check to make sure all entries have valid numerical values
            validateEntryData();
            // Collect the data stored in each text entry field and create a new product instance
            collectEntryData();
            // Finally, fire a close window request
            Window window = saveButton.getScene().getWindow();
            window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
        } catch (NumberFormatException e)
        {
            // If any boxes are blank that require numeric values, a NumberFormatException is thrown. This results in an
            // invalid entry dialog box popping up.
            ErrorDialogs.invalidEntryDialog();
        } catch(Exception ignored)
        {
            // All other exceptions, thrown by the validateEntryData() method, are ignored, as this method handles
            // the creation of dialog boxes. This block instead serves to prevent further code execution
        }
    }

    // Summary: This method is called when the user clicks the search button above the table of all parts.
    @FXML
    void searchAllParts()
    {
        String searchText = this.searchAllPartsTextField.getText();
        ObservableList<Part> matches = FXCollections.observableArrayList();
        boolean isSearchForId = true;

        // Check if the search text is empty. If it is empty, we should display all products
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
                for(Part product : this.allParts)
                {
                    // Take the product ID, convert it to a string, and see if the integer we are searching for is equal
                    // to any of the digits in each product ID
                    if(Integer.toString(product.getId()).contains(searchText))
                    {
                        matches.add(product);
                    }
                }
            }
            // Otherwise, search through all the product names to see if our search text is contained in any of the product
            // names
            else
            {
                for(Part product : this.allParts)
                {
                    if(product.getName().contains(searchText))
                    {
                        matches.add(product);
                    }
                }
            }
            // Finally, display all of our matches on the products table
            this.allPartsTable.setItems(matches);
        } else
        {
            this.allPartsTable.setItems(this.allParts);
        }
    }

    // Summary: This method is called by the main screen controller to populate the table views with their respective
    //      data.
    void populateData(int id, ObservableList<Part> allParts)
    {
        // Populate the table of all parts with the provided list of all parts
        this.allParts.addAll(allParts);
        this.allPartsTableIdColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getId()));
        this.allPartsTableNameColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getName()));
        this.allPartsTableInventoryColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getStock()));
        this.allPartsTablePPUColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>("$" + param.getValue().getPrice()));
        this.allPartsTable.setItems(this.allParts);

        // Populate the list of associated parts with our newly created list of associated parts
        this.assocPartsTableIdColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getId()));
        this.assocPartsTableNameColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getName()));
        this.assocPartsTableInventoryColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getStock()));
        this.assocPartsTablePPUColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>("$" + param.getValue().getPrice()));
        this.associatedPartsTable.setItems(this.associatedParts);

        // Set the ID number of the product created by this menu to an autogenerated ID provided by the inventory
        this.id = id;
    }

    // Summary: This method collects the data entered in the text entry fields and creates a new product instance to add
    //      to the inventory.
    void collectEntryData() throws NumberFormatException
    {
        // Create a new product with the values contained in the text entry fields
        newProduct = new Product(id,
                this.productNameTextField.getText(),
                Integer.parseInt(this.productInventoryTextField.getText()),
                Integer.parseInt(this.productMinTextField.getText()),
                Integer.parseInt(this.productMaxTextField.getText()),
                Double.parseDouble(this.productPriceTextField.getText()));
        // Add each part in the associated parts list to the product
        for(Part part : associatedParts)
        {
            newProduct.addAssociatedPart(part);
        }
    }

    // Summary: This method checks the values entered in the text entry fields and ensures they have valid and logical
    //      entries
    void validateEntryData() throws Exception
    {
        // If the inventory stock entry field has no entered text, give it a default value of 0
        if(this.productInventoryTextField.equals(""))
        {
            this.productInventoryTextField.setText("0");
        }

        // Add up the sum of the price of all parts belonging to this product. If the entered price does not exceed or
        // at least equal the total price of the associated parts, then the price is incorrect
        boolean isPriceCorrect = false;
        double priceSum = 0.0D;
        for(Part part : associatedParts)
        {
            priceSum += part.getPrice();
        }
        isPriceCorrect = Double.parseDouble(this.productPriceTextField.getText()) >= priceSum;

        if(Integer.parseInt(this.productMinTextField.getText()) > Integer.parseInt(this.productMaxTextField.getText()))
        {
            // If the min stock value is higher than the max stock value, create an error dialog and throw an exception
            ErrorDialogs.invalidValuesDialog("Please enter a min value lower than the max value.");
            throw new Exception();
        }
        else if(Integer.parseInt(this.productMaxTextField.getText()) < Integer.parseInt(this.productMinTextField.getText()))
        {
            // If the max stock value is lower than the min stock value, create an error dialog and throw an exception
            ErrorDialogs.invalidValuesDialog("Please enter a max value lower than the min value.");
            throw new Exception();
        }
        else if(Integer.parseInt(this.productInventoryTextField.getText()) > Integer.parseInt(this.productMaxTextField.getText())
                || Integer.parseInt(this.productInventoryTextField.getText()) < Integer.parseInt(this.productMinTextField.getText()))
        {
            // If the inventory stock value is not between the min and max stock values, then create an error dialog and
            // throw an exception
            ErrorDialogs.invalidValuesDialog("Please enter an inventory value between the min and max values.");
            throw new Exception();
        } else if(!isPriceCorrect)
        {
            // If the price is incorrect, create an error dialog and throw an exception
            ErrorDialogs.invalidValuesDialog("Please enter a price greater than or equal to\nthe sum of the part prices.");
            throw new Exception();
        } else if(this.productNameTextField.equals(""))
        {
            // Finally, if the product has no name, create an error dialog and throw an exception
            ErrorDialogs.invalidValuesDialog("Please enter a valid name.");
            throw new Exception();
        }
    }

    // Getters and setters
    Product getNewProduct()
    {
        return this.newProduct;
    }
}
