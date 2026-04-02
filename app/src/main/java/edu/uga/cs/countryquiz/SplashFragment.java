package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashFragment extends Fragment {

    Button start;
    Button previous;

    TextView textView;

    public SplashFragment() {
        // Required empty public constructor
    }

    public static SplashFragment newInstance() {
        SplashFragment fragment = new SplashFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        start = view.findViewById( R.id.button );
        previous = view.findViewById( R.id.button2 );
        textView = view.findViewById( R.id.textView );

        textView.setText("Test your knowledge of world geography! You'll be given 6 countries and must identify the correct capital city from 3 choices.\n" +
                "How to play:\n" +
                "Swipe left to move between questions. Select your answer before swiping. Your final score will be shown after the last question.");




        start.setOnClickListener( v -> {
            Fragment quizSwipe = new QuizSwipe();
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction()
                    .replace( R.id.fragmentContainerView2, quizSwipe )
                    .addToBackStack(null)
                    .commit();
        });

        previous.setOnClickListener( v -> {
            Fragment prevQuiz = new PreviousQuizFragment();
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction()
                    .replace( R.id.fragmentContainerView2, prevQuiz )
                    .addToBackStack(null)
                    .commit();
        });
    }
}
