package main;

import controller.MainScreen;
import controller.MenuContainer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Create the main screen menu from its fxml file
        MenuContainer<MainScreen> mainScreenMenu = new MenuContainer<>("../view/MainScreen.fxml");
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
