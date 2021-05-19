package DBAccess;

import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class allows the user to retrieve information from the first_level_divisions table with SQL
 */
public class DBDivisions {

    /**
     * retrieve an observablelist of every Division from the first_level_divisions table with SQL
     * @return ObservableList<Division> divisionList
     */
    public static ObservableList<Division> getAllDivisions()
    {
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                Division d = new Division(divisionID, divisionName, countryID);
                divisionList.add(d);
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return divisionList;
    }

    /**
     * Retrieve a single Division object from the first_level_divisions table
     * @param divisionID - id of Division to be retrieved
     * @return Division d
     */
    public static Division getDivision(int divisionID) {

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = " + divisionID;
            String divisionName = "";
            int countryID = 0;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                divisionName = rs.getString("Division");
                countryID = rs.getInt("Country_ID");
            }
            Division d = new Division(divisionID, divisionName, countryID);
            return d;
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return null;
    }
}
