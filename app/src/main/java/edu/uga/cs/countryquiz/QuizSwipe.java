package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment that contains a ViewPager2 to allow users to swipe through quiz questions.
 */
public class QuizSwipe extends Fragment {

    private ViewPager2 viewPager;

    /**
     * Required empty public constructor.
     */
    public QuizSwipe() {}

    /**
     * Static factory method to create a new instance of this fragment.
     * @return A new instance of QuizSwipe.
     */
    public static QuizSwipe newInstance() {
        QuizSwipe fragment = new QuizSwipe();
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
        return inflater.inflate(R.layout.fragment_quiz_swipe, container, false);
    }

    /**
     * Called after the view has been created.
     * Initializes the ViewPager2 and attaches the QuizPagerAdapter.
     *
     * @param view The View returned by onCreateView.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        QuizPagerAdapter quizPagerAdapter = new QuizPagerAdapter(this);

        viewPager = view.findViewById(R.id.ViewPager);
        viewPager.setAdapter(quizPagerAdapter);

    }

    /**
     * Adapter class for the ViewPager2 to manage the order of quiz fragments.
     */
    public class QuizPagerAdapter extends FragmentStateAdapter {
        /**
         * Constructor for QuizPagerAdapter.
         * @param fragment The parent fragment.
         */
        public QuizPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        /**
         * Creates a new fragment for the given position.
         * @param position The position in the ViewPager2.
         * @return A quizFragment or a ResultFragment for position 6.
         */
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if(position < 6) {return quizFragment.newInstance(position);}
            else {
                return ResultFragment.newInstance();
            }
        }

        /**
         * Returns the total number of items in the adapter.
         * @return 7 (6 questions + 1 result screen).
         */
        @Override
        public int getItemCount() {
            return 7;
        }

        /**
         * Returns an ID for the item at the given position.
         * @param position The position in the adapter.
         * @return A unique long ID.
         */
        @Override
        public long getItemId(int position) {
            QuizViewModel viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
            return (long) viewModel.getQuizSessionId() * 7 + position;
        }

        /**
         * Determines if an item is in the adapter based on its ID.
         * @param itemId The unique ID of the item.
         * @return true if the item exists.
         */
        @Override
        public boolean containsItem(long itemId) {
            return super.containsItem(itemId);
        }
    }

    /**
     * Restarts the quiz by resetting the ViewModel state and returning to the splash screen.
     */
    public void restart() {

        QuizViewModel viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        viewModel.resetQuiz();
        viewModel.getCountries();


        if (viewPager != null) {
            viewPager.setCurrentItem(0,false);
            if (viewPager.getAdapter() != null) {
                viewPager.getAdapter().notifyDataSetChanged();
            }
        }
    }

}
