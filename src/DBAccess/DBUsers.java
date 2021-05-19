package DBAccess;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class allows the user to retrieve information from the users table with SQL
 */
public class DBUsers {

    /**
     * retrieve an observablelist of every User from the users table
     * @return ObservableList<User>
     */
    public static ObservableList<User> getAllUsers()
    {
        ObservableList<User> userList = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                User u = new User(userID, username, password);
                userList.add(u);
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return userList;
    }
}
