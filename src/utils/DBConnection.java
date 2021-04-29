package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ07nOi";

    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    // Driver and Connection Interface Reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn  = null;

    private static final String username = "U07nOi";
    private static final String password = "53689080219";

    public static Connection startConnection()
    {
        try
        {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection success.");
        }
        catch (ClassNotFoundException e)
        {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getConnection()
    {
        return conn;
    }

    public static void closeConnection()
    {
        try
        {
            conn.close();
            System.out.println("Connection closed.");
        }
        catch (SQLException e)
        {
            //do nothing
        }

    }

}
