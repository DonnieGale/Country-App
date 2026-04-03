package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * A Fragment that displays a list of previously completed quizzes.
 */
public class PreviousQuizFragment extends Fragment {

    private static final String TAG = "PreviousQuizFragment";
    private CountryQuizData countryQuizData = null;
    private List<Quiz> quizList;
    private QuizRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    /**
     * Required empty public constructor.
     */
    public PreviousQuizFragment() {}


    /**
     * Static factory method to create a new instance of this fragment.
     * @return A new instance of PreviousQuizFragment.
     */
    public static PreviousQuizFragment newInstance() {
        PreviousQuizFragment fragment = new PreviousQuizFragment();
        return fragment;
    }

    /**
     * Inflates the layout for this fragment.
     * @param inflater The LayoutInflater object that can be used to inflate views.
     * @param container Parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous_quiz, container, false);
    }

    /**
     * Initializes the RecyclerView, database connection, and reads.
     * Also handles the back button press to return to the splash screen.
     *
     * @param view The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize list
        quizList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new QuizRecyclerAdapter(quizList);
        recyclerView.setAdapter(adapter);

        Button homeButton = view.findViewById(R.id.button7);

        homeButton.setOnClickListener(v -> {
            QuizViewModel viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
            viewModel.resetQuiz();
            ((MainActivity) requireActivity()).showSplashScreen();
        });
        // Initialize DB
        countryQuizData = new CountryQuizData(getActivity());

        // Execute DB read in background
        new QuizDBReader().execute();

        // Handle Back Button
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Log.d(TAG, "Back Button Pressed");
                        QuizViewModel viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
                        viewModel.resetQuiz();
                        ((MainActivity) requireActivity()).showSplashScreen();
                    }
                });
    }


    /**
     * AsyncTask to read quizzes from DB
     */
    private class QuizDBReader extends AsyncTask<Void, List<Quiz>> {

        /**
         * Retrieves all quizzes from the database.
         * @return A list of Quiz objects.
         */
        @Override
        protected List<Quiz> doInBackground(Void... voids) {

            countryQuizData.open();
            List<Quiz> quizzes = countryQuizData.retrieveAllQuizzes();

            Log.d(TAG, "QuizDBReader: quizzes retrieved: " + quizzes.size());

            return quizzes;
        }

        /**
         * Updates the UI with the retrieved list of quizzes.
         * @param quizzes The list of quizzes retrieved from the database.
         */
        @Override
        protected void onPostExecute(List<Quiz> quizzes) {

            Log.d(TAG, "QuizDBReader: quizzes size: " + quizzes.size());

            quizList.clear();
            quizList.addAll(quizzes);
            adapter.notifyDataSetChanged();

            // Log quiz list
            for (Quiz q : quizList) {
                Log.d(TAG, "Quiz: " + q.toString());
            }

            countryQuizData.close();

        }
    }

    /**
     * Reopens the database when the fragment is resumed.
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, String.valueOf(countryQuizData != null));
    }

    /**
     * Closes the database connection when the fragment is paused.
     */
    @Override
    public void onPause() {
        super.onPause();
    }


}
