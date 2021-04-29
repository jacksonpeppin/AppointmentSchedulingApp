package View_Controller;

import DBAccess.DBCountries;
import Model.Country;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableView dataTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    public void showMe (ActionEvent actionEvent)
    {

        ObservableList<Country> countryList = DBCountries.getAllCountries();
        for (Country c : countryList)
        {
            System.out.println("Country ID : " + c.getCountryID() + " Name : " + c.getCountryName());
        }
     }


}
