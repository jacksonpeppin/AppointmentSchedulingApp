package Model;

import DBAccess.DBCustomers;
import javafx.collections.ObservableList;

import javax.swing.*;

/**
 * customer object represents a row in the customers table
 */
public class Customer {
    private Integer customerID;
    private String customerName;
    private String address;
    private String zipcode;
    private String phoneNumber;
    private Integer divisionID;

    /**
     * constructor for customer object
     * @param customerID
     * @param customerName
     * @param address
     * @param zipcode
     * @param phoneNumber
     * @param divisionID
     */
    public Customer(Integer customerID, String customerName, String address, String zipcode, String phoneNumber, Integer divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Integer divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * generate a unique id for the customer object when a new customer is created
     * @return customerID
     */
    public static int generateCustomerID()
    {
        int max = 0;
        ObservableList<Customer> customers = DBCustomers.getAllCustomers();
        for (Customer c: customers)
        {
            if (c.getCustomerID() > max)
                max = c.getCustomerID();
        }
        return max + 1;
    }

    /**
     * checks if valid customer object
     * @return true if valid, false if not
     */
    public boolean isValid()
    {
        boolean valid = true;
        if (customerName.equals(""))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Error: Customer must have a name.");
        }
        else if (address.equals(""))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Error: Customer must have an address.");
        }
        else if(zipcode.equals(""))
        {
            valid= false;
            JOptionPane.showMessageDialog(null, "Error: Customer must have a Postal Code.");
        }
        else if(phoneNumber.equals(""))
        {
            valid= false;
            JOptionPane.showMessageDialog(null, "Error: Customer must have a phone number.");
        }
        return valid;
    }

}
