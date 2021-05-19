package DBAccess;

import Model.Appointment;
import View_Controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * this class allows the user to retrieve date from the appointments table in the database.
 */
public class DBAppointments {
    /**
     * retrieve all appointments as an observable list
     * @return List of all appointments in the appointments table
     */
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
                appointmentStart = Appointment.toSysDefaultTime(appointmentStart);
                Timestamp appointmentEnd = rs.getTimestamp("End");
                appointmentEnd = Appointment.toSysDefaultTime(appointmentEnd);
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

    /**
     * Update an existing appointment in the appointments table with SQL
     * @param a
     */
    public static void updateAppointment(Appointment a)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        ts = Appointment.toUTC(ts);
        Timestamp start = a.getStart();
        start = Appointment.toUTC(start);
        a.setStart(start);
        Timestamp end = a.getEnd();
        end = Appointment.toUTC(end);
        a.setEnd(end);

        try {


            String sql = "UPDATE appointments " +
                    "SET Title = '" + a.getTitle() + "', " +
                    "Description = '" + a.getDescription() + "', " +
                    "Location = '" + a.getLocation() + "', " +
                    "Type = '" + a.getType() + "', " +
                    "Start = '" + a.getStart() + "', " +
                    "End = '" + a.getEnd() + "', " +
                    "Last_Update = '" + ts + "', " +
                    "Last_Updated_By = '" + LoginController.getUser().getUserName() + "', " +
                    "Customer_ID = " + a.getCustomerID() + ", " +
                    "User_ID = " + a.getUserID() + ", " +
                    "Contact_ID =" + a.getContactID() + " " +
                    "WHERE Appointment_ID = " + a.getAppointmentID();
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate(sql);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }


    }

    /**
     * insert a new appointment in to the appointments table with SQL
     * @param a
     */
    public static void addAppointment(Appointment a)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        ts = Appointment.toUTC(ts);
        Timestamp start = a.getStart();
        start = Appointment.toUTC(start);
        a.setStart(start);
        Timestamp end = a.getEnd();
        end = Appointment.toUTC(end);
        a.setEnd(end);
        try
        {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By" +
                    ", Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (" + a.getAppointmentID() +
                    ", '" + a.getTitle() +
                    "', '" + a.getDescription() +
                    "', '" + a.getLocation() +
                    "', '" + a.getType() +
                    "', '" + start +
                    "', '" + end +
                    "', '" + ts +
                    "', '" + LoginController.getUser().getUserName() +
                    "', '" + ts +
                    "', '" + LoginController.getUser().getUserName() +
                    "', " + a.getCustomerID() +
                    ", " + a.getUserID() +
                    ", " + a.getContactID() + ")";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate(sql);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }


    }

    /**
     * delete an appointments from the appointments table with SQL
     * @param a
     */
    public static void deleteAppointment(Appointment a) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + a.getAppointmentID();
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate(sql);

        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    /**
     * all appointment's timestamp values are stored in the database in UTC, so this method makes sure to convert the
     * appointment to UTC before it is inserted in to the table
     * @param ts
     * @return ts converted to UTC
     */

}
