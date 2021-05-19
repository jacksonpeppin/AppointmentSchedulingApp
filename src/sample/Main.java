package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;

/**
 * @author Jackson Peppin
 *
 * This class starts an application that allows a user to manage customers and appointments for a business
 */
public class Main extends Application {

    /**
     * This method loads the main stage
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View_Controller/Login.fxml"));
        primaryStage.setTitle("Appointment Scheduling");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.show();
    }

    /** This is the main method. This is the first method that gets called when you run your java program. */
    public static void main(String[] args)
    {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
