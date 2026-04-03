package edu.uga.cs.countryquiz;

import java.util.ArrayList;
import java.util.List;


// This class stores all 197 countries so that the list of countries can be accessed without
// accessing the database multiple times
public class CountryRepository {

    private static CountryRepository instance = null;
    private List<Country> allCountries;

    private CountryRepository() {
        allCountries = new ArrayList<>();
    }

    public static CountryRepository getInstance() {
        if (instance == null) {
            instance = new CountryRepository();
        }
        return instance;
    }

    public List<Country> getAllCountries() {
        return allCountries;
    }

    public void setCountries(List<Country> countries) {
        this.allCountries = countries;
    }
}
