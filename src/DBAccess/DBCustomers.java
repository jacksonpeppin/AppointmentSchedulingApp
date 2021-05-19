package DBAccess;

import Model.Customer;
import View_Controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * This class allows the user to retrieve information from the customers table with SQL
 */
public class DBCustomers {

    /**
     * Retrieve an observablelist of every Customer object from the customers table with SQL
     * @return
     */
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

    /**
     * Update the information of an existing customer column within the customers table with it's modified values
     * @param c - customer to be updated
     */
    public static void updateCustomer(Customer c)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        try
        {
            String sql = "UPDATE customers " + "SET Customer_Name = '" + c.getCustomerName() + "', " +
                    "Address = '" + c.getAddress() + "', " +

                    "Postal_Code = '" + c.getPhoneNumber() + "', " +
                    "Phone = '" + c.getPhoneNumber() + "', " +
                    "Last_Update = '" + ts + "', " +
                    "Last_Updated_By = '" + LoginController.getUser().getUserName() + "', " +
                    "Division_ID = " + c.getDivisionID() + " " +
                    "WHERE Customer_ID = " + c.getCustomerID();

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * insert a new Customer into the customers table
     * @param c - Customer to be inserted
     */
    public static void addCustomer(Customer c)
    {
        try {
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) " +
                    "VALUES (" + c.getCustomerID() + ", '" +
                    c.getCustomerName() + "', '" +
                    c.getAddress() + "', '" +
                    c.getZipcode() + "', '" +
                    c.getPhoneNumber() + "', '" +
                    LoginController.getUser().getUserName() + "', '" +
                    LoginController.getUser().getUserName() + "', " +
                    c.getDivisionID() + ")";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate(sql);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    /**
     * delete a customer from the customers table
     * @param c - customer to be deleted
     */
    public static void deleteCustomer(Customer c)
    {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = " + c.getCustomerID();
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate(sql);

        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

}
