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

import com.example.course_registration.model.course_schedule_course;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class StudentScheduleList extends AppCompatActivity {

    private RecyclerView courseRecyclerView;
    private FirebaseFirestore database;
    private FirestoreRecyclerAdapter databaseToRecycleView;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule_list);

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
        FirestoreRecyclerOptions<course_schedule_course> options = new FirestoreRecyclerOptions.Builder<course_schedule_course>()
                .setQuery(query, course_schedule_course.class)
                .build();


        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<course_schedule_course, ScheduleEntryViewHolder>(options){
            @Override

            public void onBindViewHolder(ScheduleEntryViewHolder holder, int position, final course_schedule_course model){

                holder.courseName.setText(model.getCourseName());
                holder.courseType.setText(model.getCourseProgram());
                holder.courseLocation.setText(model.getCourseLocation());
                holder.courseDays.setText(model.getCourseDay());
                holder.courseTime.setText(model.getCourseTime());

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
