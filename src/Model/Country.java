package Model;

public class Country {
    private int countryID;
    private String countryName;

    /**
     * country object represents a row in the countries table
     * @param id
     * @param name
     */
    public Country(int id, String name)
    {
        this.countryID = id;
        this.countryName = name;
    }
    public int getCountryID() {return countryID;}

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String toString() {return countryName;}
}
