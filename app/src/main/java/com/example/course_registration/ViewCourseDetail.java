package com.example.course_registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.course_registration.model.Course;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import com.example.course_registration.MockFirestoreInstance;

public class ViewCourseDetail extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference coursebookRef = db.collection("Courses");


    private TextView name;
    private TextView course_day;
    private TextView program;
    private TextView course_description;
    private TextView course_time;
    private TextView course_code;

    private Button btnregisterbutton;

    private Intent intent;
    private Course course;

    private String courseID;


    private DocumentReference noteRef = noteRef = db.collection("StudentRegisteredInCourse").document();

    private CollectionReference checkregistration = db.collection("StudentRegisteredInCourse");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_detail);


        name = findViewById(R.id.txtCourseName);
        course_day = findViewById(R.id.txtcourse_day);
        course_code = findViewById(R.id.txtCourseCode);
        program = findViewById(R.id.txtprogram);
        course_description = findViewById(R.id.txtcourse_description);
        course_time = findViewById(R.id.txtcourse_time);
        btnregisterbutton = findViewById(R.id.btnResgister);






        intent = getIntent();



        course = (Course)intent.getSerializableExtra("contact");

        courseID = course.getCourse_code();
        name.setText("Name: "+course.getCourse_name());
        course_day.setText("Day: "+course.getCourse_day());
        course_code.setText("Course Code: "+course.getCourse_code());
        program.setText("Program: "+course.getProgram());
        course_description.setText("Description: "+course.getCourse_description());
        course_time.setText("Time: "+course.getCourse_time());

        checkifregistered();

    }

    public void register(View v) {

        Globals sharedData = Globals.getInstance();
        saveCourse(courseID, sharedData.getUsername());





    }


    public void saveCourse(String course1, String student) {


        StudentRegisteredInCourse ccc = new StudentRegisteredInCourse(course1,student);
        final String regcourse = course.getCourse_code();

        noteRef.set(ccc)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ViewCourseDetail.this, "You Successfully registered for "+regcourse, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }


    public void checkifregistered() {

        Globals sharedData = Globals.getInstance();
        final String  user = sharedData.getUsername();
        final String  ccc = course.getCourse_code();

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


    public static boolean check_if_we_can_register_in_course(String student_id, String course_id, MockFirestoreInstance firebase_instance){

        int current_number_of_students = firebase_instance.count_rows_by_field("StudentRegisteredInCourse", "course", course_id);
        int  max_students = Integer.parseInt(firebase_instance.get_record_attribute("Courses", course_id, "max_students"));
        if (current_number_of_students >= max_students){
            return false; }
        else{
            return true;
        }
    }

    public static int check_number_of_students_in_course(String course_id, MockFirestoreInstance firebase_instance){
        int current_number_of_students = firebase_instance.count_rows_by_field("StudentRegisteredInCourse", "course", course_id);
        return current_number_of_students;
    }


}