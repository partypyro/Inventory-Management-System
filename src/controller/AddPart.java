package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.InHouse;
import model.Outsourced;
import model.Part;

// Summary: This class acts as the controller for the add part fxml file.
public class AddPart
{
    private int id;
    private Part newPart = null;

    @FXML
    private RadioButton partSourceInHouseRadio;
    @FXML
    private ToggleGroup partSource;
    @FXML
    private TextField partMaxTextField;
    @FXML
    private TextField partMinTextField;
    @FXML
    private TextField partPriceTextField;
    @FXML
    private Text partSourceTextFieldLabel;
    @FXML
    private TextField partNameTextField;
    @FXML
    private TextField partInventoryTextField;
    @FXML
    private TextField partSourceTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    // Summary: This method is called when the user clicks the cancel button.
    @FXML
    void cancelNewPart()
    {
        if (ErrorDialogs.cancelDialog())
        {
            // If the user accepts the confirmation, a window close request is fired, which closes the window and
            // returns a null part back to the main screen
            Window window = cancelButton.getScene().getWindow();
            window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }

    // Summary: This method is called when the user clicks the save button.
    @FXML
    void saveNewPart()
    {
        try
        {
            // Check to make sure all entries have valid numerical values
            validateTextEntryData();
            // Collect the data stored in each text entry field and create a new part instance
            collectTextEntryData();
            // Finally, fire a close window request
            Window window = saveButton.getScene().getWindow();
            window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
        } catch (NumberFormatException e)
        {
            // If any boxes are blank that require numeric values, a NumberFormatException is thrown. This results in an
            // invalid entry dialog box popping up.
            ErrorDialogs.invalidEntryDialog();
        } catch (Exception ignored)
        {
            // All other exceptions, thrown by the validateTextEntryData() method, are ignored, as this method handles
            // the creation of dialog boxes. This block instead serves to prevent further code execution
        }
    }

    // Summary: This method is run when the user clicks on the toggle group for selecting the part source.
    @FXML
    void switchPartSource()
    {
        // Check which part source toggle is selected and change the text entry field text and text entry labels
        // accordingly
        if (partSource.getSelectedToggle().equals(partSourceInHouseRadio))
        {
            this.partSourceTextFieldLabel.setText("Machine ID");
            this.partSourceTextField.setPromptText("Machine ID");
        } else
        {
            this.partSourceTextFieldLabel.setText("Company Name");
            this.partSourceTextField.setPromptText("Company Name");
        }
    }

    // Summary: This method collects the data entered in the text entry fields and creates a new part to add to the
    //      inventory.
    void collectTextEntryData() throws NumberFormatException
    {
        // Check which part source toggle is selected and create a new part instance matching the selected type of part
        if (partSource.getSelectedToggle().equals(partSourceInHouseRadio))
        {
            newPart = new InHouse(id,
                    this.partNameTextField.getText(),
                    Double.parseDouble(this.partPriceTextField.getText()),
                    Integer.parseInt(this.partInventoryTextField.getText()),
                    Integer.parseInt(this.partMinTextField.getText()),
                    Integer.parseInt(this.partMaxTextField.getText()),
                    Integer.parseInt(this.partSourceTextField.getText()));
        } else
        {
            newPart = new Outsourced(id,
                    this.partNameTextField.getText(),
                    Double.parseDouble(this.partPriceTextField.getText()),
                    Integer.parseInt(this.partInventoryTextField.getText()),
                    Integer.parseInt(this.partMinTextField.getText()),
                    Integer.parseInt(this.partMaxTextField.getText()),
                    this.partSourceTextField.getText());
        }
    }

    // Summary: This method checks the values entered in the text entry fields and ensures they have valid and logical
    //      entries.
    void validateTextEntryData() throws Exception
    {
        if (Integer.parseInt(this.partMinTextField.getText()) > Integer.parseInt(this.partMaxTextField.getText()))
        {
            // If the min stock value is higher than the max stock value, create an error dialog and throw an exception
            ErrorDialogs.invalidValuesDialog("Please enter a min value lower than the max value.");
            throw new Exception();
        } else if (Integer.parseInt(this.partMaxTextField.getText()) < Integer.parseInt(this.partMinTextField.getText()))
        {
            // If the max stock value is lower than the min stock value, create an error dialog and throw an exception
            ErrorDialogs.invalidValuesDialog("Please enter a max value lower than the min value.");
            throw new Exception();
        } else if (Integer.parseInt(this.partInventoryTextField.getText()) > Integer.parseInt(this.partMaxTextField.getText())
                || Integer.parseInt(this.partInventoryTextField.getText()) < Integer.parseInt(this.partMinTextField.getText()))
        {
            // If the inventory stock value is not between the min and max stock values, then create an error dialog and
            // throw an exception
            ErrorDialogs.invalidValuesDialog("Please enter an inventory value between the min and max values.");
            throw new Exception();
        }
    }

    // Summary: This method is called by the main screen controller to assign the new part created in this menu a unique
    //      part ID number.
    void populateData(int id)
    {
        this.id = id;
    }

    // Getters and setters
    Part getNewPart()
    {
        return this.newPart;
    }
}
