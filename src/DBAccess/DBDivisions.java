package DBAccess;

import Model.Country;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBDivisions {

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
}
