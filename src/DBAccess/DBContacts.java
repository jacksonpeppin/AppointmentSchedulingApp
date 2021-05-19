package DBAccess;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this class allows the user to interact with the contacts table
 */
public class DBContacts {

    /**
     * returns an observable list of every contact in the contacts table with SQL
     * @return observable list of all contacts
     */
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

    /**
     * allows the user to retrieve a single contact from the contacts table
     * @param contactID
     * @return
     */
    public static Contact getContact(int contactID)
    {
        Contact contact = new Contact(0, "", "");
        try
        {
            String sql ="SELECT * FROM contacts WHERE Contact_ID = " + contactID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                contact.setContactID(contactID);
                contact.setContactName(contactName);
                contact.setContactEmail(contactEmail);
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return contact;
    }

}
