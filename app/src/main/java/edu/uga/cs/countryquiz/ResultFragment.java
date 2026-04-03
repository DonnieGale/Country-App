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
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {

    private static final String TAG = "ResultFragment";

    private CountryQuizData countryQuizData = null;

    public ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        countryQuizData = new CountryQuizData(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

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

            // DEBUG: inspect the database in under 60 seconds
           // try {
            //    Thread.sleep(60000);
           // } catch (InterruptedException e) {
            //    e.printStackTrace();
           // }

            countryQuizData.close();
            return quizzes[0];
        }

        @Override
        protected void onPostExecute(Quiz quiz) {
            Log.d(TAG, "Quiz saved: " + quiz.toString());
        }
    }
}
