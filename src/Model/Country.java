package Model;

public class Country {
    private int countryID;
    private String countryName;

    public Country(int id, String name)
    {
        this.countryID = id;
        this.countryName = name;
    }
    public int getCountryID() {return countryID;}

    public String getCountryName() {return countryName;}
}
