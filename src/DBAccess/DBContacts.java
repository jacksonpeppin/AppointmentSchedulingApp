package DBAccess;

import Model.Contact;
import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBContacts {

    public static ObservableList<Contact> getAllContacts()
    {
        ObservableList<Contact> clist = FXCollections.observableArrayList();
        {
            try {
                String sql = "SELECT * FROM contacts";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int contactID = rs.getInt("Contact_ID");
                    String contactName = rs.getString("Contact_Name");
                    String contactEmail = rs.getString("Email");
                    Contact C = new Contact(contactID, contactName, contactEmail);
                    clist.add(C);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return clist;
        }
    }

    public static void checkDateConversion()
    {
        System.out.println("CREATE DATE TEST");
        String sql = "select Create_Date from countries";
        try
        {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }

        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }
}
