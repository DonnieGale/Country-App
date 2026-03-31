package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

        viewPager = view.findViewById(R.id.ViewPager);

        QuizPagerAdapter adapter = new QuizPagerAdapter(this);
        viewPager.setAdapter(adapter);
    }

        // Adapter class to manage the fragments for each swipe position
        private static class QuizPagerAdapter extends FragmentStateAdapter {
            public QuizPagerAdapter(@NonNull Fragment fragment) {
                super(fragment);
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                // Create a new instance of the fragment and pass the question index
                return quizFragment.newInstance(position);
            }

            @Override
            public int getItemCount() {
                return 6; // Total number of quiz questions
            }
        }

}
