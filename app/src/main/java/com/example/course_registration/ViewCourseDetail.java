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


/**
 * @authors Nicholas Brisson & Mat Kallada
 * This class will query the firebase database and
 * display all of the details about a particular course
 */
public class ViewCourseDetail extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference coursebookRef = db.collection("Courses");
    //private FirebaseFirestore db;


    private TextView name;
    private TextView course_day;
    private TextView program;
    private TextView course_description;
    private TextView course_time;
    private TextView course_code;
    private boolean regStatus = false;
    private String regesteredID;
    private TextView course_enrolled;
    private Button btnregisterbutton;

    private Intent intent;
    private Course course;

    private String courseID;
    private DocumentReference noteRef = db.collection("StudentRegisteredInCourse").document();

    private CollectionReference checkregistration = db.collection("StudentRegisteredInCourse");

    /**
     * This method will run once the activity is activated
     * @param savedInstanceState saves the instance data (but will most likely be null)
     *                           used to satisfy the method parameter requirement
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_detail);



        name = findViewById(R.id.txtCourseName);
        course_day = findViewById(R.id.txtcourse_day);
        course_code = findViewById(R.id.txtCourseCode);
        program = findViewById(R.id.txtprogram);
        course_description = findViewById(R.id.txtcourse_description);
        course_enrolled= findViewById(R.id.txtenrolled);
        course_time = findViewById(R.id.txtcourse_time);
        btnregisterbutton = findViewById(R.id.btnRegister);





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

    /**
     * This method activates the registration process once the user clicks on
     * the register button. It will reference the saveCourse method below.
     * @param v
     */
    public void register(View v) {

        Globals sharedData = Globals.getInstance();
        saveCourse(courseID, sharedData.getUsername());





    }

    /**
     * This class will allow the student to register for the course they
     * are viewing.
     * @param course1 is the course code identifier
     * @param student is the username of the logged in student
     */
    public void saveCourse(String course1, String student) {

        if(regStatus) {
            final String regcourse = course.getCourse_code();
            db.collection("StudentRegisteredInCourse").document(regesteredID).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ViewCourseDetail.this, "You have successfully dropped " + regcourse, Toast.LENGTH_SHORT).show();
                    finish();
                    }
            });
        }
        else {
            StudentRegisteredInCourse ccc = new StudentRegisteredInCourse(course1, student);
            final String regcourse = course.getCourse_code();
            ccc.setId(noteRef.getId());
            noteRef.set(ccc)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ViewCourseDetail.this, "You Successfully registered for " + regcourse, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }
    }

    /**
     *
     * @param course_id passed to this method to check the amount of students
     *registered in the course with that course ID
     *
     * This method checks if a course still has space in it and if it doesn't,
     * it will display on the UI that the class is full and the button to register
     * is grayed out. Otherwise, the user will be able to register.
     */

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

    /**
     * Checks if a student is registered for the course they are viewing or not
     * prints that they are registered for the course if they or it activates the
     * register button if they aren't
     */
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
                               btnregisterbutton.setText("Drop Course.");
                               regesteredID = isreg.getId();
                               regStatus = true;
                            }


                        }

                    }
                });
    }

    /**
     * This method checks if a course if full or not
     * @param number_of_students the amount of students currently enrolled
     * @param max_students the maximum allowable number of students in the course
     * @return true if the class is not full and false if it is
     */
    public boolean test_whether_you_can_register(Integer number_of_students, Long max_students){
        return number_of_students<max_students;
    }

    /**
     * This method checks the current amount of enrolled students in a course
     * @param course_id the course code identifier
     * @param firebase_instance the instance data of the firebase
     * @param ss callback so that we are able to return the number of students live
     * @return
     */
    public int check_number_of_students_in_course(String course_id, FirestoreInstance firebase_instance, CallBack ss){

        // Refactored
        firebase_instance.count_rows_by_field("StudentRegisteredInCourse", "course", course_id, ss);
        return 1;
    }


}