/**
 * author: Carter & Ali
 * functionality: Showcase course schedule for registered classes
 */
package com.example.course_registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.course_registration.model.Course;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * @author Pascha and Samath
 * Schedule list to display information of students schedule.
 */

public class StudentScheduleList extends AppCompatActivity {

    private int counter = 1;
    private RecyclerView recyclerView;
    private FirebaseFirestore database;
    private FirestoreRecyclerAdapter adapter;




    private CollectionReference reg_courses;


    private FirebaseFirestore db ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule_list);
        setTitle("Your Current Schedule");

        db = FirebaseFirestore.getInstance();
        reg_courses = db.collection("StudentRegisteredInCourse");

        database = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.contactlist);

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

        Globals sharedData = Globals.getInstance();

        final Query query = db.collection("Courses").whereEqualTo("hiddentoken",sharedData.getHiddentoken());
        FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>()
                .setQuery(query,Course.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Course,ScheduleViewHolder>(options) {

            /**
             * Creating the courseviewholder to dispaly all courses
             * @param holder
             * @param position
             * @param model
             */
            @Override
            public void onBindViewHolder(ScheduleViewHolder holder, int position, final Course model) {
                holder.coursename.setText("Course Code: " +model.getCourse_code());
                holder.courseinfo.setText("Name: "+model.getCourse_name());
                holder.startTime.setText("Start Time: "+model.getStart_time());
                holder.endTime.setText("End Time: "+model.getEnd_time());
                holder.days.setText("Days: "+model.getCourse_day());
                holder.btndrop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteHelper(model.getCourse_code());

                    }
                });
                

            }



            public void deleteHelper(final String cCode) {

                Globals sharedData = Globals.getInstance();
                final String  user = sharedData.getUsername();
                final String  ccc = cCode;


                CollectionReference checkregistration = reg_courses;

                checkregistration.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    StudentRegisteredInCourse isreg = documentSnapshot.toObject(StudentRegisteredInCourse.class);

                                    String courseisReg = isreg.getCourse();
                                    String studentisReg = isreg.getStudent();

                                    if(courseisReg.equals(ccc) && studentisReg.equals(user))
                                    {
                                        FirebaseFirestore myydb ;
                                        myydb = FirebaseFirestore.getInstance();
                                        myydb.collection("StudentRegisteredInCourse").document(isreg.getId()).delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        FirebaseFirestore mydb = FirebaseFirestore.getInstance();
                                                        DocumentReference mycourse = mydb.collection("Courses").document(cCode);
                                                        mycourse.update("hiddentoken","");
                                                    }
                                                });
                                    }


                                }

                            }
                        });
            }



            /**
             * Return course viewholder to display the different course information
             * @param group
             * @param i
             * @return
             */
            @Override
            public ScheduleViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.view_schedule_helper,group,false);
                return new ScheduleViewHolder(view);
            }
        };
        return adapter;
    }



    /**
     * Iniitates activity and starts listening when activity starts
     */
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    /**
     * Resumes activity
     */
    @Override
    protected void onResume() {
        counter = 1;
        super.onResume();
    }

    /**
     * End activity and stop listenign to user input
     */
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
