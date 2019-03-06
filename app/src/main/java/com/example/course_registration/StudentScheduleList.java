package com.example.course_registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentScheduleList extends AppCompatActivity {

    private RecyclerView courseRecyclerView;
    private FirebaseFirestore database;
    private FirestoreRecyclerAdapter databaseToRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule_list);

        courseRecyclerView = findViewById(R.id.courseScheduleRecycler);
        database = FirebaseFirestore.getInstance();

        databaseToRecycleView = setUpAdapter(database);
        setUpRecyclerView(courseRecyclerView, databaseToRecycleView);
    }

    private void setUpRecyclerView(RecyclerView view, FirestoreRecyclerAdapter databaseToRecycleView) {
        RecyclerView.LayoutManager gridManager = new StaggeredGridLayoutManager(getApplicationContext());


    }
}
