package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link quizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class quizFragment extends Fragment {

    public quizFragment() {
        // Required empty public constructor
    }


    public static quizFragment newInstance() {
        quizFragment fragment = new quizFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );




        //start.setOnClickListener( v -> {
        //    Fragment quiz = new quiz();
        //    FragmentManager manager = getActivity().getSupportFragmentManager();
        //    manager.beginTransaction().replace( R.id.main, quiz ).commit();
        //});
    }
}