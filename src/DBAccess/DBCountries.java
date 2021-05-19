package DBAccess;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class allows the user to retrieve information from the countries table with SQL
 */
public class DBCountries {

    /**
     * This class returns an observablelist of every country in the countries table with SQL
     * @return observablelist of all countries
     */
    public static ObservableList<Country> getAllCountries()
    {
        ObservableList<Country> clist = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country C = new Country(countryID, countryName);
                clist.add(C);
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return clist;
    }

    /**
     * Return a single country object from the countries table with SQL
     * @param countryID
     * @return Country
     */
    public static Country getCountry(int countryID) {

        try {


            String sql = "SELECT * FROM countries WHERE Country_ID = " + countryID;
            String countryName = "";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                countryName = rs.getString("Country");
            }
            Country c = new Country(countryID, countryName);
            return c;
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return null;
    }


}
