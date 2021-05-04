package DBAccess;

import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class DBAppointments {
    public static ObservableList<Appointment> getAllAppointments()
    {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                Timestamp appointmentStart = rs.getTimestamp("Start");
                Timestamp appointmentEnd = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription,
                        appointmentLocation, appointmentType, appointmentStart, appointmentEnd, customerID, userID, contactID);
                appointmentList.add(appointment);
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return appointmentList;
    }

}
