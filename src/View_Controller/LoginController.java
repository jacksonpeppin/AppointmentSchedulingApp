package View_Controller;

import DBAccess.DBAppointments;
import DBAccess.DBUsers;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class LoginController {

    public TextField usernameTextField;
    public TextField passwordTextField;

    public void loginButtonPushed(ActionEvent actionEvent) {
        System.out.println("button pressed");
        ObservableList<User> userList = DBUsers.getAllUsers();
        for (User u: userList)
        {
            if (u.isValidLogin(usernameTextField.getText(), passwordTextField.getText()))
            {
                System.out.println("valid login");
            }


        }



    }
}

