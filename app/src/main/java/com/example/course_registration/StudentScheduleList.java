/**
 * author: Carter & Ali
 * functionality: Showcase course schedule for registered classes
 */
package com.example.course_registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.course_registration.model.Course;
import com.example.course_registration.model.course_schedule_course;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class StudentScheduleList extends AppCompatActivity {

    private RecyclerView courseRecyclerView;
    private FirebaseFirestore database;
    private FirestoreRecyclerAdapter databaseToRecycleView;
    private ArrayList<String> courseStudentIn;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule_list);

        courseStudentIn = new ArrayList<String>();
        courseRecyclerView = findViewById(R.id.courseScheduleRecycler);
        database = FirebaseFirestore.getInstance();

        databaseToRecycleView = setUpAdapter(database);
        setUpRecyclerView(courseRecyclerView, databaseToRecycleView);
    }

    /**
     *
     * @param view
     * @param databaseToRecycleView
     */
    private void setUpRecyclerView(RecyclerView view, FirestoreRecyclerAdapter databaseToRecycleView) {
        RecyclerView.LayoutManager gridManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        view.setLayoutManager(gridManager);
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(databaseToRecycleView);
    }


    /**
     *
     * @param db
     * @return
     */
    private FirestoreRecyclerAdapter setUpAdapter(FirebaseFirestore db){
        final Query query = db.collection("Courses").orderBy("start_time");
        FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>()
                .setQuery(query, Course.class)
                .build();


        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Course, ScheduleEntryViewHolder>(options){
            @Override

            public void onBindViewHolder(ScheduleEntryViewHolder holder, int position, final Course model){

                holder.courseName.setText(model.getCourse_name());
                holder.courseType.setText(model.getProgram());
                holder.courseTime.setText(model.getCourse_time());

            }

            /**
             *
             * @param group
             * @param i
             * @return
             */
            @Override
            public ScheduleEntryViewHolder onCreateViewHolder(ViewGroup group, int i){
                View view = LayoutInflater.from(group.getContext()).inflate(R.layout.course_schedule_entry, group, false);
                return new ScheduleEntryViewHolder(view);
            }
        };
        return adapter;

    }


    @Override

    protected void onStart() {
        super.onStart();
        databaseToRecycleView.startListening();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();

        databaseToRecycleView.stopListening();
    }

}
