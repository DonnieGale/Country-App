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

/**
 * RecyclerView adapter for displaying a list of previous quiz results.
 */
public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizViewHolder> {

    private List<Quiz> quizList;

    /**
     * Constructor for the QuizRecyclerAdapter.
     * @param quizList The list of previously completed quizzes retrieved from the database.
     */
    public QuizRecyclerAdapter(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder.
     *
     * @param parent The ViewGroup where the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new QuizViewHolder that holds the view for a single quiz item.
     */
    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item, parent, false);
        return new QuizViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder represents the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        Quiz quiz = quizList.get(position);

        String formattedDate = formatDate(quiz.getQuizDate());
        holder.quizDate.setText("Date: " + formattedDate);

        holder.quizResult.setText("Score: " + quiz.getQuizResult() + "/6");
    }

    /**
     * Returns the total number of items in the data set.
     * @return The size of the quiz list, or 0 if list is null.
     */
    @Override
    public int getItemCount() {
        return quizList != null ? quizList.size() : 0;
    }

    /**
     * ViewHolder class holds references to the UI components of a single quiz item.
     */
    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        public TextView quizDate;
        public TextView quizResult;

        /**
         * Constructor for the ViewHolder.
         * @param itemView The view of a single quiz item.
         */
        public QuizViewHolder(View itemView) {
            super(itemView);
            quizDate = itemView.findViewById(R.id.quizDate);
            quizResult = itemView.findViewById(R.id.quizResult);
        }
    }


    /**
     * Helper method to convert date.
     *
     * @param oldDateString The original date string.
     * @return A formatted date string.
     */
    public String formatDate(String oldDateString) {

        try {
            // parse original format
            SimpleDateFormat oldFormat =
                    new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

            Date date = oldFormat.parse(oldDateString);

            // format to output
            SimpleDateFormat newFormat =
                    new SimpleDateFormat("MMM dd, yyyy, hh:mm a", Locale.US);

            return newFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
            return oldDateString; // fallback
        }
    }
}
