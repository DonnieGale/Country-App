package edu.uga.cs.countryquiz;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Fragment that displays the splash screen of the application.
 * Provides navigation to start a new quiz or view previous results.
 */
public class SplashFragment extends Fragment {

    Button start;
    Button previous;

    TextView textView;

    /**
     * Required empty public constructor.
     */
    public SplashFragment() {}

    /**
     * Static factory method to create a new instance of this fragment.
     * @return A new instance of SplashFragment.
     */
    public static SplashFragment newInstance() {
        SplashFragment fragment = new SplashFragment();
        return fragment;
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
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    /**
     * Called after the view has been created.
     * Sets up UI components, background images, and click listeners.
     *
     * @param view The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        ConstraintLayout layout = view.findViewById( R.id.frameLayout2 );
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layout.setBackgroundResource(R.drawable.earthland);
        } else {
            layout.setBackgroundResource(R.drawable.earth);
        }
        start = view.findViewById( R.id.button );
        previous = view.findViewById( R.id.button2 );

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
