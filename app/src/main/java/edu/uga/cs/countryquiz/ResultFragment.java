package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

/**
 * Fragment that displays the final result of a quiz session.
 * It calculates the score, displays it to the user, and saves the quiz result to the database.
 */
public class ResultFragment extends Fragment {

    private static final String TAG = "ResultFragment";

    private CountryQuizData countryQuizData = null;

    /**
     * Required empty public constructor.
     */
    public ResultFragment() {}

    /**
     * Static factory method to create a new instance of this fragment.
     * @return A new instance of ResultFragment.
     */
    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        return fragment;
    }

    /**
     * Called when the fragment is being created.
     * Initializes database access.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        countryQuizData = new CountryQuizData(getActivity());
    }

    /**
     * Inflates the layout for this fragment.
     * @param inflater The LayoutInflater object that can be used to inflate views.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    /**
     * Called after the view has been created.
     * Calculates the score from the ViewModel, updates the UI, and saves the result to the database.
     *
     * @param view The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        QuizViewModel viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        
        Button home = view.findViewById(R.id.button4);
        Button previousQuiz = view.findViewById(R.id.button3);

        home.setOnClickListener( v -> {
            viewModel.resetQuiz();
            
            Fragment splash = new SplashFragment();
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction()
                    .replace( R.id.fragmentContainerView2, splash )
                    .commit();
        });

        previousQuiz.setOnClickListener( v -> {
            viewModel.resetQuiz();
            
            Fragment previousQuizFragment = new PreviousQuizFragment();
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction()
                    .replace( R.id.fragmentContainerView2, previousQuizFragment )
                    .addToBackStack(null)
                    .commit();
        });


        int numOfCorrect = viewModel.getScore();
        double scorePercentage = Math.round((numOfCorrect / 6.0 * 100) * 100)/100.0;
        Log.d(TAG, "Score: " + scorePercentage + " Correct: " + numOfCorrect);

        TextView score = view.findViewById(R.id.score);
        String resultTextDisplay = String.valueOf(scorePercentage) + "%   " + "(" + String.valueOf(numOfCorrect) + "/" + 6 + " correct)";
        score.setText(resultTextDisplay);

        // Add new quiz to the database
        Quiz quiz = new Quiz();
        quiz.setQuizDate(new Date().toString());   // store as String
        quiz.setQuizResult(numOfCorrect);


        new QuizDBWriter().execute(quiz);

    }

    /**
     * AsyncTask to write quiz to DB
     */
    private class QuizDBWriter extends AsyncTask<Quiz, Quiz> {

        @Override
        protected Quiz doInBackground(Quiz... quizzes) {

            countryQuizData.open();
            countryQuizData.storeQuiz(quizzes[0]);
            countryQuizData.close();
            return quizzes[0];
        }

        /**
         * Logs completion of the save operation.
         * @param quiz The saved Quiz.
         */
        @Override
        protected void onPostExecute(Quiz quiz) {
            Log.d(TAG, "Quiz saved: " + quiz.toString());
        }
    }
}
