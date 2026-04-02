package edu.uga.cs.countryquiz;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QuizViewModel extends androidx.lifecycle.ViewModel {
    // Array to store 6 answers (indices 0-5)
    private final String[] answers = new String[6];
    private final String[] correctAnswers = new String[6];
    private List<Integer> quizIndices = new ArrayList<>();
    private List<Country> quizCountries = null;

    private int quizSessionId = 0;

    public void resetQuiz() {
        quizSessionId++;

        for (int i = 0; i < answers.length; i++) {
            answers[i] = null;
            correctAnswers[i] = null;
        }
        if (quizIndices != null) {
            quizIndices.clear();
        }
        quizCountries = null;
        Log.d("QuizViewModel", "Quiz reset, session ID: " + quizSessionId);
    }

    public int getQuizSessionId() {
        return quizSessionId;
    }

    //TODO: Update answer after each swipe?
    public void setAnswer(int index, String answer) {
        if (index >= 0 && index < answers.length) {
            answers[index] = answer;
        }
    }

    public void setCorrectAnswer(int index, String answer) {
        if (index >= 0 && index < correctAnswers.length) {
            correctAnswers[index] = answer;
        }
    }

    public String getAnswer(int index) {
        if (index >= 0 && index < answers.length) {
            return answers[index];
        }
        return null;
    }

    public int getScore() {
        int correct = 0;
        for (int i = 0; i < 6; i++) {
            if (answers[i] != null && answers[i].equals(correctAnswers[i])) {
                correct++;

            }
        }
        return correct;
    }

    public List<Country> getCountries() {
        if (quizCountries != null) {
            return quizCountries;
        }

        quizIndices = new ArrayList<>();
        List<Country> countries = new ArrayList<>();
        List<Country> allCountries = CountryRepository.getInstance().getAllCountries();
        int totalCountries = allCountries.size();

        if (totalCountries == 0) return countries;

        while (quizIndices.size() < 18 && quizIndices.size() < totalCountries) {
            // Generate random index
            int randomIndex = java.util.concurrent.ThreadLocalRandom.current().nextInt(totalCountries);

            // Check if the list already contains this number
            if (!quizIndices.contains(randomIndex)) {
                quizIndices.add(randomIndex);
                
                // Retrieve the specific country from the database using the random index
                Country country = allCountries.get(randomIndex);
                countries.add(country);


                Log.d("QuizViewModel", "Correct Answer for index " + quizIndices.size() + ": " + country.getCapitalName());
                setCorrectAnswer(quizIndices.size() - 1, country.getCapitalName());

            }
        }
        quizCountries = countries;
        Log.d("QuizViewModel", "Generated unique indices: " + quizIndices.toString());
        return countries;
    }
}
