/**
 * author: Carter & Ali
 * functionality: Showcase course schedule for registered classes
 */
package com.example.course_registration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.course_registration.model.Course;
import com.example.course_registration.model.course_schedule_course;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class StudentScheduleList extends AppCompatActivity {

    private static final String TAG = "StudentScheduleList";
    private RecyclerView courseRecyclerView;
    private FirebaseFirestore database;
    private FirestoreRecyclerAdapter databaseToRecycleView;
    private static ArrayList<String> courseStudentIn = new ArrayList<String>();


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule_list);

       // courseStudentIn = new ArrayList<String>();

        courseRecyclerView = findViewById(R.id.courseScheduleRecycler);
        database = FirebaseFirestore.getInstance();
        findStudentCourses(database);


        databaseToRecycleView = setUpAdapter(database);
        setUpRecyclerView(courseRecyclerView, databaseToRecycleView);
        System.out.println(courseStudentIn);
    }

    /**
     *
     * @param view
     * @param databaseToRecycleView
     */
    private void setUpRecyclerView(RecyclerView view, FirestoreRecyclerAdapter databaseToRecycleView) {

        RecyclerView.LayoutManager gridManager = new LinearLayoutManager(getApplicationContext());
        view.setLayoutManager(gridManager);
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(databaseToRecycleView);
    }

    private void addToArrayList(String s, ArrayList<String> target){
        courseStudentIn.add(s);
        System.out.println(courseStudentIn + " ");
        System.out.println(s);

    }

    private void findStudentCourses(FirebaseFirestore db){

        CollectionReference studentInClass = db.collection("StudentRegisteredInCourse");

        studentInClass
                .whereEqualTo("student", "bobsimpson")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Object item = document.get("course");
                                addToArrayList(item.toString(), courseStudentIn);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        System.out.println(courseStudentIn);
    }

    private int checkArrayContains(String toCheck){
        if(courseStudentIn.contains(toCheck)){
            System.out.println("contains" + toCheck);
            return 1;
        }
        return 0;
    }

    /**
     *
     * @param db
     * @return
     */
    private FirestoreRecyclerAdapter setUpAdapter(FirebaseFirestore db){
        final Query query = db.collection("Courses")
                .orderBy("start_time");
        FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>()
                .setQuery(query, Course.class)
                .build();


        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Course, ScheduleEntryViewHolder>(options){
            @Override

            public void onBindViewHolder(ScheduleEntryViewHolder holder, int position, final Course model){
                if(checkArrayContains(model.getCourse_code()) == 1) {


                    holder.courseName.setText(model.getCourse_name());
                    holder.courseType.setText(model.getProgram());
                    holder.courseTime.setText(model.getCourse_time());
                }
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

    //takes in arraylist of course times and returns an array of integers representing the positions
    //of those elements correctly ordered
    //not used in main body of code yet
    public int[] checkOrder(ArrayList<String> listTimes){
        System.out.println(listTimes);
        ArrayList<String> listClone = new ArrayList<String>();
        ArrayList<Integer> positionInList = new ArrayList<Integer>();
        for(int i = 0; i < listTimes.size(); i++){
            listClone.add(listTimes.get(i));
            positionInList.add(i);
        }

        int sizeof = listTimes.size();
        int indexinArray = 0;
        int currentMinIndex = 0;
        int toIndex[] = new int[sizeof];
        for(int j = 0; j < listTimes.size(); j++){
            for(int i = 0; i < listClone.size(); i++){
                if(i == 0){
                    currentMinIndex = 0;
                }
                else if(compareTimes(listClone.get(currentMinIndex), listClone.get(i)) == 0){
                    currentMinIndex = i;
                }
            }
            System.out.println(currentMinIndex);
            toIndex[indexinArray] = positionInList.get(currentMinIndex);
            indexinArray++;
            listClone.remove(currentMinIndex);
            positionInList.remove(currentMinIndex);

        }


        return toIndex;
    }
    //takes two strings representing time as parameters
    //time is formatted as X:YZ or WX:YZ and is 24-hour
    //returns an integer: 1 if time1 is earlier than time2, 0 otherwise
    public int compareTimes(String time1, String time2){
        if(time1.length() > time2.length()){
            return 0;
        }
        else if(time2.length() > time1.length()){
            return 1;
        }
        else{
            for(int i = 0; i < time1.length(); i++){
                if(time1.charAt(i) > time2.charAt(i)){
                    return 0;
                }
                else if(time2.charAt(i) > time1.charAt(i)){
                    return 1;
                }
            }
        }
        return 1;

    }
}
