package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link quizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class quizFragment extends Fragment {

    private static final String TAG = "quizFragment";
    private List<Country> countryList;


    public quizFragment() {
        // Required empty public constructor
    }

    public static quizFragment newInstance(int position) {
        quizFragment fragment = new quizFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_quiz2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        int position = getArguments() != null ? getArguments().getInt("position") : 0;

        TextView questionTextView = v.findViewById(R.id.textView3);
        RadioButton rb1 = v.findViewById(R.id.radioButton2);
        RadioButton rb2 = v.findViewById(R.id.radioButton3);
        RadioButton rb3 = v.findViewById(R.id.radioButton4);

        CountryQuizData countryQuizData = new CountryQuizData(getContext());
        countryQuizData.open();

        countryList = countryQuizData.retrieveAllCountries();
        countryQuizData.close();

        Country country = countryList.get(position % countryList.size());


        questionTextView.setText("Which continent is " + country.getCountryName() + " in?");


    }

}


