package com.arpan.alosproject.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arpan.alosproject.R;
import com.arpan.alosproject.adapters.CourseListAdapter;
import com.arpan.alosproject.model.firebase.AllCourse;
import com.arpan.alosproject.model.firebase.Root;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Courses extends Fragment {

    private RecyclerView recyclerView;
    private CourseListAdapter courseListAdapter;
    private List<AllCourse> allCourses;
    private Root root;

    private ProgressBar progressBar;

    // Firebase Database
    DatabaseReference db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onStart() {
        super.onStart();

        allCourses = new ArrayList<>();

        courseListAdapter = new CourseListAdapter(getContext(), allCourses);
        recyclerView.setAdapter(courseListAdapter);

        progressBar.setVisibility(View.VISIBLE);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                root = snapshot.getValue(Root.class);
                allCourses = root.getAllCourses();
                courseListAdapter.updateValue(allCourses);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        recyclerView = view.findViewById(R.id.course_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        progressBar = view.findViewById(R.id.progress_bar);

        return view;
    }

}