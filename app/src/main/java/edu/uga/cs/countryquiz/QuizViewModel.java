package edu.uga.cs.countryquiz;

public class QuizViewModel extends androidx.lifecycle.ViewModel {
    // Array to store 6 answers (indices 0-5)
    private final String[] answers = new String[6];

    public void setAnswer(int index, String answer) {
        answers[index] = answer;
    }

    public String getAnswer(int index) {
        return answers[index];
    }
}
