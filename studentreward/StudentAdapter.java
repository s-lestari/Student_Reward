package com.example.studentreward;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private Context context;
    private List<Student> studentList;

    public StudentAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);

        holder.userIDTextView.setText(String.valueOf(student.getUserId()));
        holder.studentNameTextView.setText(student.getUsername());
        holder.pointsTextView.setText(String.valueOf(student.getPoints()));

        holder.plusButton.setOnClickListener(v -> {
            int currentPoints = student.getPoints();
            student.setPoints(currentPoints + 1);  // Increment points
            holder.pointsTextView.setText(String.valueOf(student.getPoints()));
        });

        holder.minusButton.setOnClickListener(v -> {
            int currentPoints = student.getPoints();
            if (currentPoints > 0) {
                student.setPoints(currentPoints - 1);  // Decrement points
                holder.pointsTextView.setText(String.valueOf(student.getPoints()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView userIDTextView, studentNameTextView, pointsTextView;
        ImageButton plusButton, minusButton;

        public StudentViewHolder(View itemView) {
            super(itemView);

            userIDTextView = itemView.findViewById(R.id.tvUserID);
            studentNameTextView = itemView.findViewById(R.id.tvStudentName);
            pointsTextView = itemView.findViewById(R.id.tvPoints);
            plusButton = itemView.findViewById(R.id.plusButton);
            minusButton = itemView.findViewById(R.id.minusButton);
        }
    }
}
