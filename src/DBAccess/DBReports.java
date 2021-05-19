package DBAccess;

import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * This class allows the user to generate reports from the database in the gui
 */
public class DBReports {

    /**
     * Create a String that is a report of the number of appointments in a month
     * @return String s
     */
    public static String appointmentMonthCountReport()
    {
        StringBuilder sb = new StringBuilder();
        String s = "";
        try {
            String sql = "SELECT DATE_FORMAT(t.Start, \"%Y-%m\") AS \"_Month\", COUNT(*)\n" +
                    "FROM appointments as t\n" +
                    "GROUP BY _Month;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                String month = rs.getString("_Month");
                Integer count = rs.getInt("Count(*)");
                sb.append(month + ": " + count + "\n---------------------------\n");
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Create a String that is a report of the number of appointments by appointment type
     * @return String s
     */
    public static String appointmentTypeCountReport()
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            String sql = "SELECT Type, count(*)\n" +
                    "FROM appointments\n" +
                    "GROUP BY Type;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                String type = rs.getString("Type");
                Integer count = rs.getInt("count(*)");
                sb.append(type + ": " + count + "\n---------------------------\n");
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return sb.toString();
    }

    public static String customerRegionReport()
    {
        StringBuilder sb = new StringBuilder();
        String countryName = "";
        try
        {
            String sql = "SELECT countries.Country, first_level_divisions.Division, COUNT(customers.Customer_ID)\n" +
                    "FROM customers\n" +
                    "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID\n" +
                    "JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID\n" +
                    "GROUP BY countries.Country, first_level_divisions.Division\n" +
                    "ORDER BY countries.Country";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {

                String country  = rs.getString("Country");
                String division = rs.getString("Division");
                Integer count = rs.getInt("COUNT(customers.Customer_ID)");


                if (!countryName.equals(country))
                {
                    sb.append("---------------------------\n" + country + "\n");
                    countryName = country;
                }
                sb.append(division + ": " + count + "\n");
            }


        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        sb.append("---------------------------");
        return sb.toString();
    }


    /**
     * create a string that has a schedule for every contact and the appointments associated with that contact
     * @return String s
     */
    public static String contactScheduleReport()
    {
        StringBuilder sb = new StringBuilder();
        String contactName = "";
        try
        {
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Type, appointments.Description, appointments.Start, appointments.End, appointments.Customer_ID, contacts.Contact_Name\n" +
                    "FROM appointments\n" +
                    "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                    "ORDER BY Contact_Name, Start;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {

                Integer appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                String start = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp("Start"));
                String end = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp("End"));
                Integer customerID = rs.getInt("Customer_ID");
                String name = rs.getString("Contact_Name");

                if (!contactName.equals(name))
                {
                    sb.append("---------------------------\n" + name + "\n");
                    contactName = name;
                }
                sb.append("Appointment ID: " + appointmentID + " Title: " + title + " Type: " + type + " Description: " + description
                + " Start: " + start + " End: " + end + " Customer ID: " + customerID + "\n");
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        sb.append("---------------------------");
        return sb.toString();
    }
}
