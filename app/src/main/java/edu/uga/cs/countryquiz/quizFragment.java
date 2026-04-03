package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link quizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class quizFragment extends Fragment {

    private static final String TAG = "quizFragment";


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


        QuizViewModel viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);

        TextView questionTextView = v.findViewById(R.id.textView3);
        RadioGroup radioGroup = v.findViewById(R.id.RadioGroup1);
        RadioButton rb1 = v.findViewById(R.id.radioButton2);
        RadioButton rb2 = v.findViewById(R.id.radioButton3);
        RadioButton rb3 = v.findViewById(R.id.radioButton4);

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



        // Get a list of 6 random countries from the CountryRepository instance
        List<Country> CountryList = viewModel.getCountries(); // NEW METHOD TO RETRIEVE COUNTRY LIST
        
        if (CountryList == null || CountryList.isEmpty() || position >= CountryList.size()) {
            Log.e(TAG, "Country list is empty or position out of bounds");
            return;
        }

        Country country = CountryList.get(position);

        questionTextView.setText("What is the capital of the country " + country.getCountryName() + "?");

        String correct = country.getCapitalName();
        viewModel.setCorrectAnswer(position, correct);
        String incorrect1 = CountryList.get(5+position).getCapitalName();
        String incorrect2 = CountryList.get(11+position).getCapitalName();
        int randomQuestion = java.util.concurrent.ThreadLocalRandom.current().nextInt(3);

        if (randomQuestion == 0) {
        rb1.setText("A: " + correct);
        rb2.setText("B: " + incorrect1);
        rb3.setText("C: " + incorrect2);
        }
        else if (randomQuestion == 1) {
            rb1.setText("A: " + incorrect1);
            rb2.setText("B: " + correct);
            rb3.setText("C: " + incorrect2);
        } else if (randomQuestion == 2) {
            rb1.setText("A: " + incorrect1);
            rb2.setText("B: " + incorrect2);
            rb3.setText("C: " + correct);
        }

        //restore saved answer
        String savedAnswer = viewModel.getAnswer(position);
        if (savedAnswer != null) {
            if (rb1.getText().equals(savedAnswer)) rb1.setChecked(true);
            else if (rb2.getText().equals(savedAnswer)) rb2.setChecked(true);
            else if (rb3.getText().equals(savedAnswer)) rb3.setChecked(true);
        }
        //save answer on click
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selected = v.findViewById(checkedId);
            String fullText = selected.getText().toString();
            String answer = fullText.split(": ")[1];
            if (selected != null) {
                viewModel.setAnswer(position, answer);
                Log.d(TAG, "Saved answer for position " + position + ": " + answer);
            }
        });

    }

}
