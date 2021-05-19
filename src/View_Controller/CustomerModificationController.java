package View_Controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import Model.Country;
import Model.Customer;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ChangeWindow;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * add or modify customers with this class
 */
public class CustomerModificationController implements Initializable {

    @FXML
    private Label titleLabel;

    @FXML
    private TextField customerIDTextField;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField customerAddressTextField;

    @FXML
    private TextField customerPostalCodeTextField;

    @FXML
    private TextField customerPhoneTextField;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private ComboBox<Division> stateComboBox;

    private Customer selectedCustomer;

    boolean addCustomer = MainFormController.getAddCustomer();

    /**
     * populate countryComboBox with Country objects and populate fields with the selected customer if being modified
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        int customerID = 0;

        ObservableList<Country> countries = DBCountries.getAllCountries();
        countryComboBox.setItems(countries);


        if(addCustomer)
        {
            titleLabel.setText("Add Customer");
            customerID = Customer.generateCustomerID();
            customerIDTextField.setText(String.valueOf(customerID));
        }

        else
        {
            selectedCustomer = MainFormController.getSelectedCustomer();
            Division d = DBDivisions.getDivision(selectedCustomer.getDivisionID());
            Country c = DBCountries.getCountry(d.getCountryID());

            titleLabel.setText("Modify Customer");
            customerIDTextField.setText(selectedCustomer.getCustomerID().toString());
            customerNameTextField.setText(selectedCustomer.getCustomerName());
            customerAddressTextField.setText(selectedCustomer.getAddress());
            customerPostalCodeTextField.setText(selectedCustomer.getZipcode());
            customerPhoneTextField.setText(selectedCustomer.getPhoneNumber());
            stateComboBox.getSelectionModel().select(d);
            countryComboBox.getSelectionModel().select(c);


        }

    }

    /**
     * updates the stateComboBox based on what country is selected from the countryComboBox
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void countrySelected(ActionEvent actionEvent) throws IOException, SQLException {
        stateComboBox.setItems(null);
        ObservableList<Division> divisions = DBDivisions.getAllDivisions();
        ObservableList<Division> matchingDivisions = FXCollections.observableArrayList();
        Country c = countryComboBox.getSelectionModel().getSelectedItem();
        for (Division d: divisions)
        {
            if (c.getCountryID() == d.getCountryID())
            {
                matchingDivisions.add(d);
            }
        }
        stateComboBox.setItems(matchingDivisions);





    }


    /**
     * add customer to the customers table if adding customer, update customer in the customers table if modifying, use
     * lambda to reduce lines of code in this method
     * @param actionEvent
     * @throws IOException
     */
    public void saveButtonPressed(ActionEvent actionEvent) throws IOException {
        //make sure country and firstleveldiv is selected
        if(countryComboBox.getSelectionModel().getSelectedItem() != null && stateComboBox.getSelectionModel().getSelectedItem() != null)
        {
            Customer c = new Customer(Integer.parseInt(customerIDTextField.getText()), customerNameTextField.getText(),
                    customerAddressTextField.getText(), customerPostalCodeTextField.getText(), customerPhoneTextField.getText(),
                    stateComboBox.getSelectionModel().getSelectedItem().getDivisionID());
            if (c.isValid())
            {
                if (addCustomer)
                {
                    DBCustomers.addCustomer(c);
                }
                else
                {
                    DBCustomers.updateCustomer(c);
                }
                change.changeWindow("MainForm.fxml", actionEvent);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Error: Please select a country and State/Province.");
        }
    }

    /**
     * return to the main form, use lambda to reduce lines of code in this method
     * @param actionEvent
     * @throws IOException
     */
    public void exitButtonPressed(ActionEvent actionEvent) throws IOException {
        change.changeWindow("MainForm.fxml", actionEvent);
    }

    ChangeWindow change = (String s, ActionEvent e) ->
    {
        Parent addProductParent = FXMLLoader.load(getClass().getResource(s));
        Scene addProductScene = new Scene(addProductParent);
        //This line gets the stage information
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

        window.setScene(addProductScene);
        window.show();
    };

}