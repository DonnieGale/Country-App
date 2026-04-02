package edu.uga.cs.countryquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizViewHolder> {

    private List<Quiz> quizList;

    public QuizRecyclerAdapter(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        Quiz quiz = quizList.get(position);

        String formattedDate = formatDate(quiz.getQuizDate());
        holder.quizDate.setText("Date: " + formattedDate);

        // holder.quizDate.setText("Date: " + quiz.getQuizDate());
        holder.quizResult.setText("Score: " + quiz.getQuizResult() + "/6");
    }

    @Override
    public int getItemCount() {
        return quizList != null ? quizList.size() : 0;
    }

    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        public TextView quizDate;
        public TextView quizResult;

        public QuizViewHolder(View itemView) {
            super(itemView);
            quizDate = itemView.findViewById(R.id.quizDate);
            quizResult = itemView.findViewById(R.id.quizResult);
        }
    }


    public String formatDate(String oldDateString) {

        try {
            // Step 1: parse original format
            SimpleDateFormat oldFormat =
                    new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

            Date date = oldFormat.parse(oldDateString);

            // Step 2: format to desired output
            SimpleDateFormat newFormat =
                    new SimpleDateFormat("MMM dd, yyyy, hh:mm a", Locale.US);

            return newFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
            return oldDateString; // fallback
        }
    }
}
