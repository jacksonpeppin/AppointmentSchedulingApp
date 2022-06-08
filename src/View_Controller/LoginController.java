package View_Controller;

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
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * allows the user to access the application if a valid username and password are provided
 */
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

    private static User user;

    /**
     * Display form in french if user's default system language is french, english if not
     * Display the user's timezone in the ui
     * @param url
     * @param resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {

        userLanguage = Locale.getDefault().getLanguage();
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

    /**
     * Take user to the main form of the application if correct username and password are provided, or display an error message if not
     * @param actionEvent
     * @throws IOException
     */
    public void loginButtonPushed(ActionEvent actionEvent) throws IOException {
        ObservableList<User> userList = DBUsers.getAllUsers();
        boolean validLogin = false;
        invalidLoginLabel.setText("");

        for (User u: userList)
        {
            if (u.isValidLogin(usernameTextField.getText(), passwordTextField.getText()))
            {
                user = u;
                writeToFile("Successful");
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
                writeToFile("Unsuccessful");
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

    public static User getUser()
    {
        return user;
    }

    /**
     * Update a text file with attempted login details
     * @param success
     */
    private void writeToFile(String success)
    {
        try
        {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            String str = "\nAttempted Username: " + usernameTextField.getText() + "\nLogin: " + success
             + "\nTime: " + ts + "\n------------------------------------------";
            BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true));
            writer.append(str);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

