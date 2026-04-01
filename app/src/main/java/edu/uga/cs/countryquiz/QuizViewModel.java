package edu.uga.cs.countryquiz;

import android.util.Log;

public class QuizViewModel extends androidx.lifecycle.ViewModel {
    // Array to store 6 answers (indices 0-5)
    private final String[] answers = new String[6];
    private final String[] correctAnswers = new String[6];

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
            if (answers[i].equals(correctAnswers[i])) {
                correct++;
            }
        }
        double score = Math.round((correct / 6 * 100) * 100)/100;
        Log.d("QuizViewModel", "Score: " + score + " Correct: " + correct);

        return score;
    }
}
