package View_Controller;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.ChangeWindow;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ResourceBundle;

/**
 * Main form of the appointment scheduling application
 */
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

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionCol;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentContactIDCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointment, Timestamp> appointmentStartCol;

    @FXML
    private TableColumn<Appointment, Timestamp> appointmentEndCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerCol;

    @FXML
    private RadioButton allRadioButton;

    @FXML
    private RadioButton weekRadioButton;

    @FXML
    private RadioButton monthRadioButton;

    @FXML
    private Button appointmentAddButton;

    @FXML
    private Button appointmentModifyButton;

    @FXML
    private Button appointmentDeleteButton;

    @FXML
    private Button appointmentsSummaryButton;

    @FXML
    private Label upcomingAppointmentsLabel;

    private ObservableList<Appointment> appointmentList;
    private static boolean addCustomer = false;
    private static boolean addAppointment = false;
    private static Customer selectedCustomer;
    private static Appointment selectedAppointment;

    /**
     * populate tableviews and check if there's an upcoming appointment, updating the ui accordingly
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        addCustomer = false;

        ObservableList<Customer> customersList = DBCustomers.getAllCustomers();
        appointmentList = DBAppointments.getAllAppointments();

        //customer table
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("zipcode"));
        customerPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        customerDivisionIDCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionID"));
        customerTableView.setItems(customersList);

        //appointment table
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        appointmentContactIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
        appointmentCustomerCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        appointmentTableView.setItems(appointmentList);

        //upcoming appointment alert
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fifteenFromNow =LocalDateTime.now().plusMinutes(15);
        String upcomingNotification = "";
        boolean upcomingApt = false;

        for (Appointment a: appointmentList)
        {
            LocalDateTime aptLDT = a.getStart().toLocalDateTime();
            if(aptLDT.isAfter(now) && aptLDT.isBefore(fifteenFromNow))
            {
                upcomingApt = true;
                upcomingNotification = upcomingNotification + "ID: " + a.getAppointmentID() + " Start: " + a.getStart();

            }

        }
        if(upcomingApt)
        {
            upcomingAppointmentsLabel.setText(upcomingNotification);
        }
        else
        {
            upcomingNotification = "No appointments in the next fifteen minutes.";
            upcomingAppointmentsLabel.setText(upcomingNotification);
        }




    }

    /**
     * Display Appointments in the tableview based on which radiobutton is selected: show all, this week and this month
     * @param actionEvent
     * @throws IOException
     */
    public void radioButtonPressed(ActionEvent actionEvent) throws IOException
    {

        LocalDateTime now = LocalDateTime.now();
        int currentWeek = now.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();
        ObservableList<Appointment> appointmentsThisWeek = FXCollections.observableArrayList();
        ObservableList<Appointment> appointmentsThisMonth = FXCollections.observableArrayList();

        //sort by week
        if(weekRadioButton.isSelected()) {
            for (Appointment a : appointmentList) {
                LocalDateTime ldt = a.getStart().toLocalDateTime();
                int week = ldt.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
                int year = ldt.getYear();
                if (week == currentWeek && year == currentYear)
                    appointmentsThisWeek.add(a);
            }
            appointmentTableView.setItems(appointmentsThisWeek);
        }

        //sort by month
        if(monthRadioButton.isSelected()) {
            for (Appointment a : appointmentList) {
                LocalDateTime ldt = a.getStart().toLocalDateTime();
                int month = ldt.getMonthValue();
                int year = ldt.getYear();
                if (month == currentMonth && year == currentYear)
                    appointmentsThisMonth.add(a);
            }
            appointmentTableView.setItems(appointmentsThisMonth);
        }

        //show all
        if(allRadioButton.isSelected())
        {
            appointmentTableView.setItems(appointmentList);
        }
    }

    /**
     * Show the customermodication form, initialized to add a customer, use lambda to reduce lines of code in this method
     * @param e
     * @throws IOException
     */
    public void addCustomerButtonPressed(ActionEvent e) throws IOException {

        addCustomer = true;
        //change scenes
        change.changeWindow("CustomerModification.FXML", e);
    }

    /**
     * show the customermodification form, initialized to modify a customer, use lambda to reduce lines of code in this method
     * @param e
     * @throws IOException
     */
    public void modifyCustomerButtonPressed(ActionEvent e) throws IOException
    {
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null)
        {
            JOptionPane.showMessageDialog(null, "Error: Please select a customer to modify");
        }
        else
        {
            addCustomer = false;
            //change scenes
            change.changeWindow("CustomerModification.FXML", e);
        }
    }

    /**
     * Delete selected customer in the customerTableView from the customers table with SQL if the user responds yes to the prompt
     * @param e
     * @throws IOException
     */
    public void deleteCustomerButtonPressed(ActionEvent e) throws IOException
    {

        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null)
        {
            JOptionPane.showMessageDialog(null, "Error: Please select a customer to delete.");
        }
        else {
            boolean validDelete = true;
            for (Appointment a : appointmentList) {
                if (a.getCustomerID() == selectedCustomer.getCustomerID()) {
                    validDelete = false;
                    JOptionPane.showMessageDialog(null, "Error: Can not delete Customer with scheduled appointment(s).");
                }
            }
            if (validDelete) {
                int input = JOptionPane.showConfirmDialog(null, "Delete selected customer?");
                //user selects yes
                if (input == 0) {
                    DBCustomers.deleteCustomer(selectedCustomer);
                    //update tableView
                    ObservableList<Customer> customersList = DBCustomers.getAllCustomers();
                    customerTableView.setItems(customersList);

                }
            }
        }
    }

    /**
     * show the appointmentmodification form, initialized to add an appointment, use lambda to reduce lines of code in this method
     * @param e
     * @throws IOException
     */
    public void addAppointmentButtonPressed(ActionEvent e) throws IOException
    {
        addAppointment = true;
        //change scenes
        change.changeWindow("AppointmentModification.FXML", e);

    }

    /**
     * show the appointmentmodification form, initialized to modify an appointment, use lambda to reduce lines of code in this method
     * @param e
     * @throws IOException
     */
    public void modifyAppointmentButtonPushed(ActionEvent e) throws IOException
    {
        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null)
        {
            JOptionPane.showMessageDialog(null, "Error: Please select an appointment to modify");
        }
        else
        {
            addAppointment = false;
            //change scenes
            change.changeWindow("AppointmentModification.FXML", e);
        }

    }

    /**
     * delete the selected appointment from the appointmentTableView form the appointments table with SQL if the user responds
     * yes to the prompt
     * @param e
     * @throws IOException
     */
    public void deleteAppointmentButtonPushed(ActionEvent e) throws IOException {
        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null)
        {
            JOptionPane.showMessageDialog(null, "Error: Please select an appointment to delete.");
        }
        else {
            int input = JOptionPane.showConfirmDialog(null, "Delete selected appointment?");
            //user selects yes
            if (input == 0) {
                DBAppointments.deleteAppointment(selectedAppointment);
                //update tableView
                appointmentList = DBAppointments.getAllAppointments();
                appointmentTableView.setItems(appointmentList);
            }
        }
    }

    /**
     * display the reports form
     * @param e
     * @throws IOException
     */
    public void generateReportsButtonPushed(ActionEvent e) throws IOException
    {
        change.changeWindow("Reports.FXML", e);
    }

    /**
     * return to the login form
     * @param e
     * @throws IOException
     */
    public void logoutButtonPushed(ActionEvent e) throws IOException{
        change.changeWindow("Login.FXML", e);
    }

    /**
     *
     * @return true if CustomerModification is to be initialized to add aCustomer, false if modifying a customer
     */
    public static boolean getAddCustomer() { return addCustomer; }

    /**
     *
     * @return true if AppointmentModification is to be intialized to add an Appointment, false if modifying an appointment
     */
    public static boolean getAddAppointment() {return addAppointment; }

    /**
     *
     * @return selected customer from customerTableView
     */
    public static Customer getSelectedCustomer() {return selectedCustomer;}

    /**
     *
     * @return selected appointment from appointmentTableView
     */
    public static Appointment getSelectedAppointment() {return selectedAppointment;}


    /**
     * Theres a lot of changing forms going on from this class, so this lambda expression increases the readability of
     * the code and reduces the lines of code in this class.
     */
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
