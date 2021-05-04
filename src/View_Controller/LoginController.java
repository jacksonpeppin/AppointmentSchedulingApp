package View_Controller;

import DBAccess.DBAppointments;
import DBAccess.DBUsers;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;



public class LoginController implements Initializable {
    @FXML
    private TextField usernameTextField;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label userLocationLabel;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label titleLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label invalidLoginLabel;

    private String userLanguage;
    private ZoneId userZoneID;


    @Override
    public void initialize(URL url, ResourceBundle resources) {

        userLanguage = System.getProperty("user.language");
        userZoneID = ZoneId.systemDefault();

        if (userLanguage.equals("fr"))
        {
            titleLabel.setText("Application de Planification");

            usernameLabel.setText("Nom d'utilisateur:");

            passwordLabel.setText("Mot de passe:");

            locationLabel.setText("Emplacement:");

            loginButton.setText("Identification");
        }
        userLocationLabel.setText(userZoneID.getDisplayName(TextStyle.FULL, Locale.getDefault()));

    }

    public void loginButtonPushed(ActionEvent actionEvent) throws IOException {
        System.out.println("button pressed");
        ObservableList<User> userList = DBUsers.getAllUsers();
        boolean validLogin = false;
        invalidLoginLabel.setText("");

        for (User u: userList)
        {
            if (u.isValidLogin(usernameTextField.getText(), passwordTextField.getText()))
            {
                System.out.println("valid login");
                validLogin = true;
                //change scenes
                Parent addProductParent = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                Scene addProductScene = new Scene(addProductParent);
                //This line gets the stage information
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                window.setScene(addProductScene);
                window.show();

            }
            if (!validLogin)
            {
                if (userLanguage.equals("fr"))
                {
                    invalidLoginLabel.setText("Nom d'utilisateur ou mot de passe invalide.");
                }
                else
                {
                    invalidLoginLabel.setText("Invalid username or password.");
                }
            }


        }



    }
}

