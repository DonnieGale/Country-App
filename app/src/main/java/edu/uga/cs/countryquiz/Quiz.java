package edu.uga.cs.countryquiz;

import java.util.List;

/**
 * Represents a quiz consisting of multiple questions.
 */
public class Quiz {

    private long id;
    private List<Question> questions;

    private String quizDate;
    private int quizResult;
    private int questionsAnswered;

    public Quiz() {
        this.id = -1;
        this.questions = null;
        this.quizDate = null;
        this.quizResult = 0;
        this.questionsAnswered = 0;
    }

    public Quiz(String quizDate, int quizResult) {
        this.id = -1;
        this.quizDate = quizDate;
        this.quizResult = quizResult;
        this.questionsAnswered = 0;
        this.questions = null;
    }

    public Quiz(List<Question> questions, String quizDate) {
        this.id = -1;
        this.questions = questions;
        this.quizDate = quizDate;
        this.quizResult = 0;
        this.questionsAnswered = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getQuizDate() {
        return quizDate;
    }

    public int getQuizResult() {
        return quizResult;
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public void setQuizResult(int quizResult) {
        this.quizResult = quizResult;
    }

    public void setQuestionsAnswered(int questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }

    public String toString() {
        return id + ": Quiz on " + quizDate + " Result: " + quizResult +
                " Answered: " + questionsAnswered;
    }
}
