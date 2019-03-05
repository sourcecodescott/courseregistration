package com.example.course_registration;

import android.app.AlertDialog;
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
import com.example.course_registration.model.Interval;
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

import java.util.Date;
import java.util.*;

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
    private String conflict;
    private int iscon = 0;


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


        Check_For_Conflicts();

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


    public void Check_For_Conflicts() {

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

                            if(studentisReg.equals(user))
                            {
                                Course cc = getCourseByName(courseisReg);

                                if(isTimeConflict(course.getStart_time(),course.getEnd_time(),course.getCourse_day(),cc.getStart_time(),cc.getEnd_time(),cc.getCourse_day()) == true)
                                {
                                    conflict += "Course Name: "+ course.getCourse_name()+" Start Time: "+course.getStart_time()+" End Time: "+course.getEnd_time()+" Days "+ course.getCourse_day();
                                    conflict += "\n";
                                    conflict += "Course Name: "+ cc.getCourse_name()+" Start Time: "+cc.getStart_time()+" End Time: "+cc.getEnd_time()+" Days "+ cc.getCourse_day();
                                    conflict += "--------------------------------------------------------------------------------------------------\n";
                                    iscon++;
                                }
                            }


                        }

                    }
                });

        if(iscon > 0)
        {

            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage(conflict);
            dlgAlert.setTitle("Schedule Conflict");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }


    }


    public Course getCourseByName(String cName)
    {
        Course c = new Course();
        return c;
    }

    static boolean isTimeConflict(String course_1_StartTime, String course_1_EndTime, String course_1_Days, String course_2_StartTime,String course_2_EndTime, String course_2_Days)
    {
        String interval1_time1 = course_1_StartTime;
        String interval1_time2 = course_1_EndTime;
        Interval time1 = new Interval(Double.parseDouble(interval1_time1.replace(':', '.')), Double.parseDouble(interval1_time2.replace(':', '.')));

        String interval2_time1 = course_2_StartTime;
        String interval2_time2 = course_2_EndTime;
        Interval time2 = new Interval(Double.parseDouble(interval2_time1.replace(':', '.')), Double.parseDouble(interval2_time2.replace(':', '.')));





        String s1=course_1_Days;
        String s2=course_2_Days;
        boolean m = false;
        for(int i=0;i<s1.length();i++){
            char c=s1.charAt(i);

            for(int j=0;j<s2.length();j++){

                char c2=s2.charAt(j);

                if(c == c2)
                {
                    if(time1.intersects(time2) == true)
                    {
                       // System.out.println("They Overlap");
                        m=true;
                    }

                }
            }
            if(m == true)
            {
                break;
            }
        }

        return m;
    }




}