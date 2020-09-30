package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ErrorDialogs
{
    // Summary: This function creates a confirmation dialog for canceling new parts or products, and returns a boolean
    //      corresponding to whether the user has confirmed the cancellation or not.
    public static boolean cancelDialog()
    {
        Alert cancelConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        cancelConfirmation.setTitle("Confirm Cancel");
        cancelConfirmation.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = cancelConfirmation.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    // Summary: This function creates a confirmation dialog for deleting parts or products, and returns a boolean
    //      corresponding to whether the user has confirmed the deletion or not.
    public static boolean deleteDialog()
    {
        Alert cancelConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        cancelConfirmation.setTitle("Confirm Delete");
        cancelConfirmation.setContentText("Are you sure you want to delete this item?");
        Optional<ButtonType> result = cancelConfirmation.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    // Summary: This function creates a error dialog when the user has entered an invalid entry for a new or modified
    //      part or product.
    public static void invalidEntryDialog()
    {
        Alert invalidTextEntry = new Alert(Alert.AlertType.ERROR);
        invalidTextEntry.setTitle("Invalid Entries");
        invalidTextEntry.setContentText("Please enter a valid entry for each text field.");
        invalidTextEntry.showAndWait();
    }

    // Summary: This functions creates an error dialog when the user has entered an illogical value for a new or modified
    //      part or product. This error dialog displays with a given error text based on the context of the entry.
    public static void invalidValuesDialog(String errorText)
    {
        Alert invalidValues = new Alert(Alert.AlertType.ERROR);
        invalidValues.setTitle("Invalid Values");
        invalidValues.setContentText(errorText);
        invalidValues.showAndWait();
    }
}
