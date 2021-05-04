package DBAccess;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomers {
    public static ObservableList<Customer> getAllCustomers()
    {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Integer customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                Integer divisionID = rs.getInt("Division_ID");
                Customer customer = new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, divisionID);
                customerList.add(customer);
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return customerList;
    }
}
