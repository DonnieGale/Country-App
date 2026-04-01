package edu.uga.cs.countryquiz;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * Returns a shuffled list of all continent options (1 correct, 2 wrong).
     */
    public List<String> getContinentOptions() {
        List<String> options = new ArrayList<>();
        options.add(correctContinent);
        if (wrongContinents != null) {
            options.addAll(wrongContinents);
        }
        Collections.shuffle(options);
        return options;
    }

    @Override
    public String toString() {
        return "Question: " + countryName;
    }
}
