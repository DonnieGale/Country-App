package edu.uga.cs.countryquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link quizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class quizFragment extends Fragment {

    int questionIndex;
    RadioGroup radioGroup;

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
        if (getArguments() != null) {
            questionIndex = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz2, container, false);
        radioGroup = v.findViewById(R.id.RadioGroup1);

        // 1. Change text based on questionIndex
        updateQuestionUI(v, questionIndex);

        // 2. Save answer when a radio button is clicked
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selected = v.findViewById(checkedId);
            String answer = selected.getText().toString();
            saveAnswerToActivity(answer);
        });

        return v;
    }

    private void updateQuestionUI(View v, int index) {
        // Logic to fetch question 'index' from your DB or List
        // Example: textView.setText(quizList.get(index).getQuestion());
    }

    private void saveAnswerToActivity(String answer) {
        // Use a ViewModel or an Interface to save the answer
        // back to the parent Activity/Fragment so it persists
        // when the user finishes the quiz.
    }
}