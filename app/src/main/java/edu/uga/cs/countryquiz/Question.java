package edu.uga.cs.countryquiz;

import java.util.List;

/**
 * Represents a single quiz question.
 */
public class Question {

    private String countryName;

    private String correctCapital;
    private String correctContinent;

    private List<String> wrongCapitals;
    private List<String> wrongContinents;

    public Question() {
        this.countryName = null;
        this.correctCapital = null;
        this.correctContinent = null;
        this.wrongCapitals = null;
        this.wrongContinents = null;
    }

    public Question(String countryName, String correctCapital, String correctContinent,
                    List<String> wrongCapitals, List<String> wrongContinents) {

        this.countryName = countryName;
        this.correctCapital = correctCapital;
        this.correctContinent = correctContinent;
        this.wrongCapitals = wrongCapitals;
        this.wrongContinents = wrongContinents;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCorrectCapital() {
        return correctCapital;
    }

    public String getCorrectContinent() {
        return correctContinent;
    }

    public List<String> getWrongCapitals() {
        return wrongCapitals;
    }

    public List<String> getWrongContinents() {
        return wrongContinents;
    }

    public String toString() {
        return "Question: " + countryName;
    }
}
