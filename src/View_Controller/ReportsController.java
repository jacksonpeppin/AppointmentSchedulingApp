package View_Controller;

import DBAccess.DBReports;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class shows important information regarding Appointments, Customers and Contacts
 */
public class ReportsController implements Initializable {

    @FXML
    private TextArea appointmentsMonthCountTextArea;

    @FXML
    private TextArea appointmentsTypeCountTextArea;

    @FXML
    private TextArea contactScheduleTextArea;

    @FXML
    private TextArea customerCountTextArea;

    @FXML
    private Button exitButton;

    /**
     * intialize the form with the report data
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appointmentsMonthCountTextArea.setText(DBReports.appointmentMonthCountReport());
        appointmentsTypeCountTextArea.setText(DBReports.appointmentTypeCountReport());
        contactScheduleTextArea.setText(DBReports.contactScheduleReport());
        customerCountTextArea.setText(DBReports.customerRegionReport());
    }

    /**
     * return to main form
     * @param event
     * @throws IOException
     */
    @FXML
    void exitButtonPushed(ActionEvent event) throws IOException {
        //change scenes
        Parent addProductParent = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        //This line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(addProductScene);
        window.show();
    }
}
