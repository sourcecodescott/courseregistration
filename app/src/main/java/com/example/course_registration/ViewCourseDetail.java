package com.example.course_registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.course_registration.model.Course;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.*;

public class ViewCourseDetail extends AppCompatActivity {

    private FirebaseFirestore db;

    private TextView name;
    private TextView course_day;
    private TextView program;
    private TextView course_description;
    private TextView course_time;
    private TextView course_code;
    private TextView course_enrolled;

    private Button btnregisterbutton;

    private Intent intent;
    private Course course;

    private String courseID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_view_course_detail);


        name = findViewById(R.id.txtCourseName);
        course_day = findViewById(R.id.txtcourse_day);
        course_code = findViewById(R.id.txtCourseCode);
        program = findViewById(R.id.txtprogram);
        course_description = findViewById(R.id.txtcourse_description);
        course_enrolled= findViewById(R.id.txtenrolled);
        course_time = findViewById(R.id.txtcourse_time);
        btnregisterbutton = findViewById(R.id.btnResgister);





        intent = getIntent();

        RealFirestoreInstance rfi = new RealFirestoreInstance(db);

        course = (Course)intent.getSerializableExtra("contact");

        courseID = course.getCourse_code();
        name.setText("Name: "+course.getCourse_name());
        course_day.setText("Day: "+course.getCourse_day());
        course_code.setText("Course Code: "+course.getCourse_code());
        program.setText("Program: "+course.getProgram());
        course_description.setText("Description: "+course.getCourse_description());
        course_time.setText("Time: "+course.getCourse_time());

        CallBack ss = new CallBack() {
            public void callback(Object counted) {
                course_enrolled= findViewById(R.id.txtenrolled);
                course_enrolled.setText("Enrolled: "+((int)counted));
            };
        };
        course_enrolled.setText("Enrolled: "+this.check_number_of_students_in_course(courseID, rfi, ss));

        checkifregistered_helper();

        checkiffull_helper(courseID);

    }

    public void register(View v) {

        Globals sharedData = Globals.getInstance();
        saveCourse(courseID, sharedData.getUsername());





    }


    public void saveCourse(String course1, String student) {

        DocumentReference noteRef = noteRef = db.collection("StudentRegisteredInCourse").document();

        StudentRegisteredInCourse ccc = new StudentRegisteredInCourse(course1,student);
        final String regcourse = course.getCourse_code();

        noteRef.set(ccc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ViewCourseDetail.this, "You Successfully registered for " + regcourse, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }


    public void checkiffull_helper(final String course_id) {

        final RealFirestoreInstance firebase_instance = new RealFirestoreInstance(db);

        CallBack ss = new CallBack() {
            public void callback(Object s1) {
                final int number_of_students = (Integer) s1;

                CallBack ss2 = new CallBack() {
                    public void callback(Object s2) {

                        Long max_students_in_course = (Long) s2;

                        if (!test_whether_you_can_register(number_of_students, max_students_in_course)) {

                            btnregisterbutton.setText("Class is full. ");
                            btnregisterbutton.setEnabled(false);

                        }
                    };
                };

                firebase_instance.get_record_attribute("Courses", course_id, "max_students", ss2);




            };
        };


        RealFirestoreInstance rfi = new RealFirestoreInstance(db);

        Globals sharedData = Globals.getInstance();
        final String  user = sharedData.getUsername();
        final String  ccc = course.getCourse_code();

        check_number_of_students_in_course(ccc, rfi, ss);
    }

    public void checkifregistered_helper() {

        Globals sharedData = Globals.getInstance();
        final String  user = sharedData.getUsername();
        final String  ccc = course.getCourse_code();

        CollectionReference checkregistration = db.collection("StudentRegisteredInCourse");

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
                                btnregisterbutton.setText("You are registered for this course.");
                                btnregisterbutton.setEnabled(false);
                            }


                        }

                    }
                });
    }

    public boolean test_whether_you_can_register(Integer number_of_students, Long max_students){
        return number_of_students<max_students;
    }

    public int check_number_of_students_in_course(String course_id, FirestoreInstance firebase_instance, CallBack ss){

        // Refactored
        firebase_instance.count_rows_by_field("StudentRegisteredInCourse", "course", course_id, ss);
        return 1;
    }


}