package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreviousQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviousQuizFragment extends Fragment {

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
        super.onViewCreated( view, savedInstanceState );




    }
}