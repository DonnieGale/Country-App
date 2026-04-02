package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreviousQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviousQuizFragment extends Fragment {

    private static final String TAG = "PreviousQuizFragment";

    private CountryQuizData countryQuizData = null;
    private List<Quiz> quizList;
    private QuizRecyclerAdapter adapter;
    private RecyclerView recyclerView;


    public PreviousQuizFragment() {
        // Required empty public constructor
    }


    public static PreviousQuizFragment newInstance() {
        PreviousQuizFragment fragment = new PreviousQuizFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous_quiz, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize list
        quizList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new QuizRecyclerAdapter(quizList);
        recyclerView.setAdapter(adapter);

        // Initialize DB
        countryQuizData = new CountryQuizData(getActivity());
        countryQuizData.open();

        // Execute DB read in background
        new QuizDBReader().execute();
    }


    /**
     * AsyncTask to read quizzes from DB
     */
    private class QuizDBReader extends AsyncTask<Void, List<Quiz>> {

        @Override
        protected List<Quiz> doInBackground(Void... voids) {

            List<Quiz> quizzes = countryQuizData.retrieveAllQuizzes();

            Log.d(TAG, "QuizDBReader: quizzes retrieved: " + quizzes.size());

            return quizzes;
        }

        @Override
        protected void onPostExecute(List<Quiz> quizzes) {

            Log.d(TAG, "QuizDBReader: quizzes size: " + quizzes.size());

            quizList.clear();
            quizList.addAll(quizzes);
            adapter.notifyDataSetChanged();

            // LATER: maybe for recyclerview
            for (Quiz q : quizList) {
                Log.d(TAG, "Quiz: " + q.toString());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (countryQuizData != null && !countryQuizData.isDBOpen()) {
            countryQuizData.open();
            Log.d(TAG, "onResume: opening DB");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (countryQuizData != null) {
            countryQuizData.close();
            Log.d(TAG, "onPause: closing DB");
        }
    }


}
