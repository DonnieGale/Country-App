package edu.uga.cs.countryquiz;

/**
 * Represents a country with its capital, continent, and abbreviation.
 */
public class Country {

    private long id;
    private String countryName;
    private String capitalName;
    private String continentName;
    private String abbreviation;

    public Country() {
        this.id = -1;
        this.countryName = null;
        this.capitalName = null;
        this.continentName = null;
        this.abbreviation = null;
    }

    public Country(String countryName, String capitalName, String continentName, String abbreviation) {
        this.id = -1;
        this.countryName = countryName;
        this.capitalName = capitalName;
        this.continentName = continentName;
        this.abbreviation = abbreviation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String toString() {
        return id + ": " + countryName + " " + capitalName + " " + continentName + " " + abbreviation;
    }
}
