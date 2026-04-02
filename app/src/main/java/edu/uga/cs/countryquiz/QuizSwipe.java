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
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizSwipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizSwipe extends Fragment {

    private ViewPager2 viewPager;


    public QuizSwipe() {
        // Required empty public constructor
    }

    public static QuizSwipe newInstance() {
        QuizSwipe fragment = new QuizSwipe();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_swipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        QuizPagerAdapter quizPagerAdapter = new QuizPagerAdapter(this);

        viewPager = view.findViewById(R.id.ViewPager);
        viewPager.setAdapter(quizPagerAdapter);

    }

    public class QuizPagerAdapter extends FragmentStateAdapter {
        public QuizPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if(position < 6) {return quizFragment.newInstance(position);}
            else {
                return ResultFragment.newInstance();
            }
        }

        @Override
        public int getItemCount() {
            return 7;
        }

        @Override
        public long getItemId(int position) {
            QuizViewModel viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
            return (long) viewModel.getQuizSessionId() * 7 + position;
        }

        @Override
        public boolean containsItem(long itemId) {
            return super.containsItem(itemId);
        }
    }

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
