package View_Controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import Model.Appointment;
import Model.Contact;
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
import javafx.stage.Stage;
import sample.ChangeWindow;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * add or modify appointments with this class
 */
public class AppointmentModificationController implements Initializable {

    @FXML
    private TextField appointmentIDTextField;

    @FXML
    private TextField customerIDTextField;

    @FXML
    private TextField userIDTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<LocalTime> endTimeComboBox;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private Label titleLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField startTextField;

    @FXML
    private TextField endTextField;

    private boolean addAppointment;

    private Appointment selectedAppointment;

    private Timestamp startTS;
    private Timestamp endTS;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int customerID;
    private int userID;


    /**
     * Change the titleLabel based on whether or not the user is adding or modifying a customer and populate the form
     * if a customer is being modified. populate combobox with all the contacts from the contacts database
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int appointmentID = 0;

        ObservableList<Contact> contacts = DBContacts.getAllContacts();
        contactComboBox.setItems(contacts);
        ObservableList<LocalTime> times = populateTimeComboBox();
        startTimeComboBox.setItems(times);
        endTimeComboBox.setItems(times);

        addAppointment = MainFormController.getAddAppointment();
        if (addAppointment) {
            titleLabel.setText("Add Appointment");
            appointmentID = Appointment.generateAppointmentID();
            appointmentIDTextField.setText(String.valueOf(appointmentID));
        } else {
            selectedAppointment = MainFormController.getSelectedAppointment();
            startTS = selectedAppointment.getStart();
            endTS = selectedAppointment.getEnd();
            startDate = startTS.toLocalDateTime().toLocalDate();
            endDate = endTS.toLocalDateTime().toLocalDate();
            startTime = startTS.toLocalDateTime().toLocalTime();
            startTimeComboBox.getSelectionModel().select(startTime);
            endTime = endTS.toLocalDateTime().toLocalTime();
            endTimeComboBox.getSelectionModel().select(endTime);
            Contact c = DBContacts.getContact(selectedAppointment.getContactID());

            titleLabel.setText("Modify Appointment");
            appointmentIDTextField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
            customerIDTextField.setText(String.valueOf(selectedAppointment.getCustomerID()));
            userIDTextField.setText(String.valueOf(selectedAppointment.getUserID()));
            titleTextField.setText(selectedAppointment.getTitle());
            descriptionTextField.setText(selectedAppointment.getDescription());
            locationTextField.setText(selectedAppointment.getLocation());
            typeTextField.setText(selectedAppointment.getType());
            startDatePicker.setValue(startDate);
            endDatePicker.setValue(endDate);
            //startTextField.setText(String.valueOf(selectedAppointment.getStart()));
            //endTextField.setText(String.valueOf(selectedAppointment.getEnd()));
            contactComboBox.getSelectionModel().select(c);

        }
    }


    /**
     * go back to the main form, use lambda to reduce lines of code in this method
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void exitButtonPressed(ActionEvent actionEvent) throws IOException {
        change.changeWindow("MainForm.fxml", actionEvent);

    }

    /**
     * check if the appointment is valid. if valid, add the appointment and return to the main form. if not valid, show
     * the appropriate error messages and do nothing else, use lambda to reduce lines of code in this method
     * @param event
     * @throws IOException
     */
    @FXML
    void saveButtonPressed(ActionEvent event) throws IOException {
        try
        {
            customerID = Integer.parseInt(customerIDTextField.getText());
            userID = Integer.parseInt(userIDTextField.getText());
        }
        catch (NumberFormatException numberFormatException)
        {
            JOptionPane.showMessageDialog(null, "Error: A customer ID and User ID must be entered.");
        }

        if (contactComboBox.getSelectionModel().getSelectedItem() != null && customerID >= 0 && userID >= 0)
        {
            startDate = startDatePicker.getValue();
            endDate = endDatePicker.getValue();
            startTime = startTimeComboBox.getSelectionModel().getSelectedItem();
            endTime = endTimeComboBox.getSelectionModel().getSelectedItem();
            LocalDateTime startLDT = LocalDateTime.of(startDate, startTime);
            startTS = Timestamp.valueOf(startLDT);
            LocalDateTime endLDT = LocalDateTime.of(endDate, endTime);
            endTS = Timestamp.valueOf(endLDT);

            Appointment a = new Appointment(Integer.parseInt(appointmentIDTextField.getText()), titleTextField.getText(),
                    descriptionTextField.getText(), locationTextField.getText(), typeTextField.getText(), startTS,
                    endTS, customerID, userID,
                    contactComboBox.getSelectionModel().getSelectedItem().getContactID());
            if (a.isValid())
            {
                if (addAppointment)
                {
                    DBAppointments.addAppointment(a);
                }
                else
                {
                    DBAppointments.updateAppointment(a);
                }
                change.changeWindow("MainForm.fxml", event);
            }
        }



    }

    /**
     * populate combobox with every possible time from 00:00 to 23:45 in 15 minute increments
     * @return list of all LocalTimes
     */
    private ObservableList<LocalTime> populateTimeComboBox()
    {
        ObservableList<LocalTime> times = FXCollections.observableArrayList();
        for (int i = 0; i < 24; i++)
        {
            for ( int j = 0; j < 4; j++)
            {
                LocalTime lt = LocalTime.of(i, j * 15);
                times.add(lt);
            }
        }
        return times;
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
