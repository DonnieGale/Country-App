package edu.uga.cs.countryquiz;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuizViewModel extends androidx.lifecycle.ViewModel {
    // Array to store 6 answers (indices 0-5)
    private final String[] answers = new String[6];
    private final String[] correctAnswers = new String[6];
    private List<Integer> quizIndices = new ArrayList<>();
    private List<Country> quizCountries = null;

    public void setAnswer(int index, String answer) {
        answers[index] = answer;
    }

    public void setCorrectAnswer(int index, String answer) {
        correctAnswers[index] = answer;
    }

    public String getAnswer(int index) {
        return answers[index];
    }

    public double getScore() {

        double correct = 0;
        for (int i = 0; i < answers.length; i++) {
            if (answers[i] != null && answers[i].equals(correctAnswers[i])) {
                correct++;
            }

        }
        double score = Math.round((correct / 6.0 * 100) * 100)/100.0;
        Log.d("QuizViewModel", "Score: " + score + " Correct: " + correct);

        return score;
    }

    public List<Country> getCountries(CountryQuizData countryQuizData) {
        if (quizCountries != null) {
            return quizCountries;
        }

        quizIndices = new ArrayList<>();
        List<Country> countries = new ArrayList<>();
        List<Country> allCountries = countryQuizData.retrieveAllCountries();
        int totalCountries = allCountries.size();


        while (quizIndices.size() < 18) {
            // Generate random index
            int randomIndex = java.util.concurrent.ThreadLocalRandom.current().nextInt(totalCountries);

            // Check if the list already contains this number to ensure uniqueness
            if (!quizIndices.contains(randomIndex)) {
                quizIndices.add(randomIndex);
                
                // Retrieve the specific country from the database using the random index
                Country country = allCountries.get(randomIndex);
                countries.add(country);

                // Only set the correct answer for the first 6 countries (the questions)
                if (quizIndices.size() <= 6) {
                    setCorrectAnswer(quizIndices.size() - 1, country.getCapitalName());
                }
            }
        }
        quizCountries = countries;
        Log.d("QuizViewModel", "Generated unique indices: " + quizIndices.toString());
        return countries;
    }
}
