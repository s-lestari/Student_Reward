package com.example.studentreward;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PointFragment extends Fragment {

    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_point, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize student list (mock data for now)
        studentList = new ArrayList<>();
        studentList.add(new Student("sanya", 80, 1));
        studentList.add(new Student("syifa", 100, 2));

        // Set adapter for RecyclerView
        studentAdapter = new StudentAdapter(getContext(), studentList);
        recyclerView.setAdapter(studentAdapter);

        // Optional: Handle save button click (you can implement update points to DB here)
        Button saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            // Logic to save data (e.g., send data to server)
        });

        return view;
    }
}
