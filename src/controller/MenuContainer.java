package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;

// Summary: This class serves a storage structure for new windows/menus loaded using fxml files. It stores their
//      respective controller (of generic type T), stage, FXMLLoader, and loaded parent node.
public class MenuContainer<T>
{
    private FXMLLoader loader = new FXMLLoader();
    private Stage menuStage = new Stage();
    private Parent menuParent;
    private T menuController;

    public MenuContainer(String FXMLPath) throws IOException
    {
        // Load the resource with the given path and set the fxml loader to the location storing that path
        loader.setLocation(getClass().getResource(FXMLPath));
        // Load the parent node of the scene
        menuParent = loader.load();
        // Create a reference to the scene's controller instance
        menuController = loader.getController();
        // Finally, set the JavaFX stage to a scene created with the loaded fxml file and show it on the screen.
        menuStage.setScene(new Scene(menuParent));
        menuStage.show();
    }

    // Summary: This function takes an EventHandler instance that handles a window close request. This EventHandler
    //      typically handles the passing of the new or modified part or controller back to the main screen of the
    //      program.
    public void setCloseAction(EventHandler<WindowEvent> onCloseAction)
    {
        this.menuStage.setOnCloseRequest(onCloseAction);
    }

    // Getters and setters
    public T getController() { return this.menuController; }

    public Stage getStage() { return this.menuStage; }
}