package edu.uga.cs.countryquiz;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for the Country Quiz application.
 * Manages selected answers, correct answers,
 * and the list of countries in the current session.
 */
public class QuizViewModel extends androidx.lifecycle.ViewModel {
    // Array to store 6 answers
    private final String[] answers = new String[6];
    private final String[] correctAnswers = new String[6];
    private List<Integer> quizIndices = new ArrayList<>();
    private List<Country> quizCountries = null;

    private int quizSessionId = 0;

    /**
     * Resets the quiz state for a new session.
     * Clears all answers, country lists, and increments the session ID.
     */
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

    /**
     * Gets the current quiz session ID.
     * @return The session ID.
     */
    public int getQuizSessionId() {
        return quizSessionId;
    }

    /**
     * Sets the user's answer for a specific question.
     * @param index The question index.
     * @param answer The answer string selected by the user.
     */
    public void setAnswer(int index, String answer) {
        if (index >= 0 && index < answers.length) {
            answers[index] = answer;
        }
    }

    /**
     * Sets the correct answer for a specific question.
     * @param index The question index.
     * @param answer The correct capital city name.
     */
    public void setCorrectAnswer(int index, String answer) {
        if (index >= 0 && index < correctAnswers.length) {
            correctAnswers[index] = answer;
        }
    }

    /**
     * Retrieves the user's answer for a specific question.
     * @param index The question index (0-5).
     * @return The answer string, or null if not answered.
     */
    public String getAnswer(int index) {
        if (index >= 0 && index < answers.length) {
            return answers[index];
        }
        return null;
    }

    /**
     * Calculates the total score for the current quiz.
     * @return The number of correct answers (0-6).
     */
    public int getScore() {
        int correct = 0;
        for (int i = 0; i < 6; i++) {
            if (answers[i] != null && answers[i].equals(correctAnswers[i])) {
                correct++;

            }
        }
        return correct;
    }

    /**
     * Retrieves or generates a list of 18 countries (6 for questions, 12 for incorrect answers).
     * @return A list of Country objects.
     */
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
                
                // Retrieve the specific country from the database
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
