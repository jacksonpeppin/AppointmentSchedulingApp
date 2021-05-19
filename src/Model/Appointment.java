package Model;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import DBAccess.DBUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.TimeConversion;

import javax.swing.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This class represents an appointment row from the appointments table
 */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int customerID;
    private int userID;
    private int contactID;
    private Contact contact;

    /**
     * construct appointment object
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     */
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Create a unique appointment ID when a new appointment object is created
     * @return appointmentID
     */
    public static int generateAppointmentID()
    {
        int max = 0;
        ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();
        for (Appointment a: appointments)
        {
            if (a.getAppointmentID() > max)
                max = a.getAppointmentID();
        }
        return max + 1;
    }

    /**
     * convert timestamp from the user's timezone to EST timezone, use lambda to reduce lines of code in this method
     * @param ts
     * @return
     */
    public static Timestamp toEST(Timestamp ts)
    {
        /*
        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime syszdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime estzdt = syszdt.withZoneSameInstant(ZoneId.of("US/Eastern"));
        LocalDateTime ldtIn = estzdt.toLocalDateTime();
        ts = Timestamp.valueOf(ldtIn);

         */

        ts = conversion.convertTime(ts, ZoneId.systemDefault().toString(), "US/Eastern");

        return ts;
    }
    /**
     * convert timestamp from the UTC timezone to user's timezone, use lambda to reduce lines of code in this method
     * @param ts
     * @return
     */
    public static Timestamp toSysDefaultTime(Timestamp ts)
    {
        /*
        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime utczdt = ldt.atZone(ZoneId.of("UTC"));
        ZonedDateTime syszdt = utczdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime ldtIn = syszdt.toLocalDateTime();
        ts = Timestamp.valueOf(ldtIn);
        */


        ts = conversion.convertTime(ts, "UTC", ZoneId.systemDefault().toString());

        return ts;
    }

    /**
     * convert timestamp from the user's timezone to UTC timezone, use lambda to reduce lines of code in this method
     * @param ts
     * @return
     */
    public static Timestamp toUTC(Timestamp ts)
    {
        /*
        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime syszdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utczdt = syszdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldtIn = utczdt.toLocalDateTime();
        ts = Timestamp.valueOf(ldtIn);

         */

        ts = conversion.convertTime(ts, ZoneId.systemDefault().toString(), "UTC");

        return ts;
    }

    /**
     * restrict the user from making an appointment that overlaps in start or end time  with another appointment
     * associated with the same customer
     * @return true if overlapping appointment found, false if not
     */
    private boolean overlappingAppointments()
    {
        boolean overlapping = false;
        ObservableList<Appointment> appointmentsList = DBAppointments.getAllAppointments();
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        for (Appointment a: appointmentsList)
        {
            if(customerID == a.getCustomerID() && appointmentID != a.appointmentID)
                customerAppointments.add(a);
        }
        for (Appointment a: customerAppointments)
        {
            if((start.after(a.getStart()) && start.before(a.getEnd())) || (end.after(a.getStart()) && end.before(a.getEnd())) || start.equals(a.getStart())
            || start.equals(a.getEnd()) || end.equals(a.getEnd()) || (start.before(a.getStart()) && end.after(a.getStart())))
                overlapping = true;
        }
        return overlapping;
    }

    /**
     * makes sure the appointment is a valid appointment object
     * @return true if valid, false if not
     */
    public boolean isValid()
    {
        boolean valid = true;
        LocalTime open = LocalTime.of(7, 59);
        LocalTime close = LocalTime.of(23, 1);
        LocalDateTime startDate = toEST(start).toLocalDateTime();
        LocalDateTime endDate = toEST(end).toLocalDateTime();
        LocalTime startTime = LocalTime.of(toEST(start).toLocalDateTime().getHour(), start.toLocalDateTime().getMinute());
        LocalTime endTime = LocalTime.of(toEST(end).toLocalDateTime().getHour(), end.toLocalDateTime().getHour());


        if (title.equals(""))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Error: Appointment must have a title.");
        }

        if (description.equals(""))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Error: Appointment must have a description.");
        }

        if (location.equals(""))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Error: Appointment must have a location.");
        }

        if (type.equals(""))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Error: Appointment must have a type.");
        }

        if (start.equals(""))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Error: Appointment must have a Start time.");
        }

        if (end.equals(""))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Error: Appointment must have an end time.");
        }

        if (!(startTime.isAfter(open) && startTime.isBefore(close)))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Error: Appointment must be start between 8:00 and 22:00 EST");
        }

        if (!(endTime.isAfter(open) && endTime.isBefore(close)))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Error: Appointment must be end between 8:00 and 22:00 EST");
        }

        if (startDate.isAfter(endDate))
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Error: Appointment start time must be before end time.");
        }

        if (overlappingAppointments())
        {
            valid = false;
            JOptionPane.showMessageDialog(null, "Error: This customer already has an appointment scheduled during these times.");
        }

        if (customerID >= 0)
        {
            ObservableList<Customer> clist = FXCollections.observableArrayList();
            boolean foundMatch = false;
            clist = DBCustomers.getAllCustomers();
            for (Customer c: clist)
            {
                if(c.getCustomerID() == customerID)
                {
                    foundMatch = true;
                }
            }
            if (!foundMatch)
            {
                valid = false;
                JOptionPane.showMessageDialog(null, "Error: No customer with that ID exists.");
            }

        }

        if (userID >= 0) {
            ObservableList<User> ulist = FXCollections.observableArrayList();
            boolean foundMatch = false;
            ulist = DBUsers.getAllUsers();
            for (User u : ulist) {
                if (u.getUserID() == userID) {
                    foundMatch = true;
                }
            }
            if (!foundMatch) {
                valid = false;
                JOptionPane.showMessageDialog(null, "Error: No user with that ID exists.");
            }
        }
        return valid;
    }

    /**
     * A lot of code is needed to convert time from upt to three different time zones, so this lambda expression reduces
     * the lines of code in this class and makes the code more readable.
     */
    static TimeConversion conversion = (Timestamp ts, String inputTimeZone, String outputTimeZone) -> {
        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime inputZDT = ldt.atZone(ZoneId.of(inputTimeZone));
        ZonedDateTime outputZDT = inputZDT.withZoneSameInstant(ZoneId.of(outputTimeZone));
        LocalDateTime ldtIn = outputZDT.toLocalDateTime();
        ts = Timestamp.valueOf(ldtIn);

        return ts;
    };
}
