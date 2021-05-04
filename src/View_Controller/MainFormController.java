package View_Controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import Model.Country;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerPostalCodeCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneNumberCol;

    @FXML
    private TableColumn<Customer, Integer> customerDivisionIDCol;

    @FXML
    private Button customerAddButton;

    @FXML
    private Button customerModifyButton;

    @FXML
    private Button customerDeleteButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ObservableList<Customer> customersList = DBCustomers.getAllCustomers();
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("zipcode"));
        customerPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        customerDivisionIDCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionID"));
        customerTableView.setItems(customersList);

        System.out.println(customersList);



    }



}
