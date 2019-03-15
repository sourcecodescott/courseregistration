package com.example.course_registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.course_registration.model.Course;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * @author Dan and Pascha
 * Coruse list information to display course information and a button to display course details
 */
public class view_course_list extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button addContactButton;
    private FirebaseFirestore database;
    private FirestoreRecyclerAdapter adapter;

    /**
     * return instance with course information
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_list);
        recyclerView = findViewById(R.id.contactlist);
        database = FirebaseFirestore.getInstance();
        adapter = setUpAdapter(database);
        setUpRecyclerView(recyclerView,adapter);

    }

    /**
     * Paramaters for Recyclerview
     * @param rv
     * @param adapter
     */
    private void setUpRecyclerView(RecyclerView rv, FirestoreRecyclerAdapter adapter) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }

    /**
     * Settng up the reference ot the firstore database to created the viewholder.
     * @param db
     * @return
     */
    private FirestoreRecyclerAdapter setUpAdapter(FirebaseFirestore db) {
        final Query query = db.collection("Courses").orderBy("course_code").limit(50);
        FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>()
                .setQuery(query,Course.class)
                .build();


        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Course,CourseViewHolder>(options) {
            /**
             * Creating the courseviewholder to dispaly all courses 
             * @param holder
             * @param position
             * @param model
             */
            @Override
            public void onBindViewHolder(CourseViewHolder holder, int position, final Course model) {
                holder.name.setText(model.getCourse_code());
                holder.email.setText(model.getCourse_name());
                holder.detailsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(view_course_list.this,ViewCourseDetail.class);
                        intent.putExtra("contact",model);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public CourseViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.contact_entry,group,false);
                return new CourseViewHolder(view);
            }
        };
        return adapter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }





}
