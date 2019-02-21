package com.example.course_registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

public class CourseDisplay extends AppCompatActivity {
    private FirebaseFirestore db;

    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_display);
        db = FirebaseFirestore.getInstance();
        private CollectionReference
        detail.setOn
        Query query = db.collection("Courses");


    }
}
