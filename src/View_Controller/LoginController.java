package View_Controller;

import DBAccess.DBAppointments;
import DBAccess.DBUsers;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;

public class LoginController {

    public TextField usernameTextField;
    public TextField passwordTextField;
    public Label invalidLoginLabel;

    public void loginButtonPushed(ActionEvent actionEvent) {
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
            }
            if (!validLogin)
            {
                invalidLoginLabel.setText("Invalid username or password.");
            }


        }



    }
}

