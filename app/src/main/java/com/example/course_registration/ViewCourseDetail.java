package com.example.course_registration;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.course_registration.model.Course;
import com.example.course_registration.model.Interval;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * @authors Nicholas Brisson & Mat Kallada
 * This class will query the firebase database and
 * display all of the details about a particular course
 */
public class ViewCourseDetail extends AppCompatActivity {

    private FirebaseFirestore db ;
    private CollectionReference coursebookRef;


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
    private DocumentReference noteRef;

    private String conflict = "";
    private int iscon = 0;
    int thiscounter = 0;
    boolean isconflictexist = false;
    boolean iscoursefull = false;

    private CollectionReference checkregistration;

    /**
     * This method will run once the activity is activated
     * @param savedInstanceState saves the instance data (but will most likely be null)
     *                           used to satisfy the method parameter requirement
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        coursebookRef = db.collection("Courses");

        noteRef = db.collection("StudentRegisteredInCourse").document();
        checkregistration = db.collection("StudentRegisteredInCourse");

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
        btnregisterbutton.setTag("default");

        setTitle(course.getCourse_name());

        CallBack ss = new CallBack() {
            public void callback(Object counted) {
                course_enrolled= findViewById(R.id.txtenrolled);
                /*if((int)counted > course.getMax_students()) {
                    counted = course.getMax_students();
                }*/
                course_enrolled.setText("Enrolled: "+((int)counted));
                /*if(counted == course.getMax_students()) {
                    iscoursefull = true;
                }*/
            };
        };
        this.check_number_of_students_in_course(courseID, rfi, ss);
        checkifregistered_helper();
        checkiffull_helper(courseID);
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

                    FirebaseFirestore mydb = FirebaseFirestore.getInstance();
                    DocumentReference mycourse = mydb.collection("Courses").document(regcourse);
                     mycourse.update("hiddentoken","");

                    Toast.makeText(ViewCourseDetail.this, "You have successfully dropped " + regcourse, Toast.LENGTH_SHORT).show();
                    finish();
                    }
            });
        }

        else {
            String regType = "Full";
            if(iscoursefull) {
                regType = "Wait";
            }
            StudentRegisteredInCourse ccc = new StudentRegisteredInCourse(course1, student);
            final String regcourse = course.getCourse_code();
            ccc.setRegType(regType);
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

                            if (btnregisterbutton.getTag() == "drop"){
                                // Do nothing if the user is in the course!
                            }else{
                                iscoursefull = true;
                                btnregisterbutton.setText("Register for waitlist. ");
                                //btnregisterbutton.setEnabled(false);
                            }

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
                               btnregisterbutton.setTag("drop");
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
     * This method checks the current amount of full enrolled students in a course
     * @param course_id the course code identifier
     * @param firebase_instance the instance data of the firebase
     * @param ss callback so that we are able to return the number of students live
     * @return
     */
    public int check_number_of_students_in_course(String course_id, FirestoreInstance firebase_instance, CallBack ss){

        // Refactored
        firebase_instance.count_rows_by_field("StudentRegisteredInCourse", "course", course_id, "regType",  ss);
        return 1;
    }



    /**
     * @authors Samath Scott & Dan Parsons
     * This function will display a dialog box if a schedule conflict exist
     * @param message is the text that will be displayed
     */
    public void showMessage(String message)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Unable to Add Course. There is a Scheduling Conflict with the following...");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    /**
     * @authors Samath Scott & Dan Parsons
     * This function will calculate if there is a conflict between the current course and
     * every other course the student already register for.
     * @param course_1_Days days that the course is offered
     * @param course_1_StartTime The time the course starts
     * @param course_1_EndTime The time the course ends
     */
    public boolean isTimeConflict(String course_1_StartTime, String course_1_EndTime, String course_1_Days, String course_2_StartTime,String course_2_EndTime, String course_2_Days)
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


    /**
     * @authors Samath Scott & Dan Parsons
     * Get a particular course by name
     * @param cName the name of the course you want to retrieve
     */
    public void getCourse(String cName) {

        DocumentReference mycourse = db.collection("Courses").document(cName);

        mycourse.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(ViewCourseDetail.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                    return;
                }
                thiscounter++;

                if (documentSnapshot.exists()) {
                    Course cour = documentSnapshot.toObject(Course.class);
                    SendCourse(cour);
                }

                if (thiscounter == iscon)
                {

                    if(isconflictexist == true)
                    {
                        showMessage(conflict);
                    }
                    else
                    {
                        Globals sharedData = Globals.getInstance();
                        saveCourse(courseID, sharedData.getUsername());
                        btnregisterbutton.setTag("drop");
                    }


                    iscon = 0;
                    conflict = "";
                    thiscounter = 0;
                    isconflictexist = false;

                }

            }

        });
    }


    /**
     * @authors Samath Scott & Dan Parsons
     * This is the main function that is responsible for checking for schedule conflicts.
     */

    boolean is_any_course_choosen_yet = false;
    public void Check_For_Conflicts() {

        Globals sharedData = Globals.getInstance();
        final String  user = sharedData.getUsername();




        checkregistration.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            StudentRegisteredInCourse isreg = documentSnapshot.toObject(StudentRegisteredInCourse.class);

                            //thread.start();
                            String courseisReg = isreg.getCourse();
                            String studentisReg = isreg.getStudent();

                            if(studentisReg.equals(user))
                            {
                                is_any_course_choosen_yet = true;
                                getCourse(courseisReg);
                                iscon++;
                            }
                        }

                        if(is_any_course_choosen_yet == false)
                        {
                            Globals sharedData = Globals.getInstance();
                            saveCourse(courseID, sharedData.getUsername());
                            btnregisterbutton.setTag("drop");
                        }



                    }
                });





    }


    /**
     * @authors Samath Scott & Dan Parsons
     * Helper method that is used by course conflict method.
     */
    void SendCourse(Course other_cour)
    {
        if(isTimeConflict(course.getStart_time(),course.getEnd_time(),course.getCourse_day(),other_cour.getStart_time(),other_cour.getEnd_time(),other_cour.getCourse_day()) == true) {
            conflict += "Course Code: " + other_cour.getCourse_code() + "\nStart Time: " + other_cour.getStart_time() + "\nEnd Time: " + other_cour.getEnd_time() + "\nDays: " + other_cour.getCourse_day();
            conflict += "\n-------------------------------------------------------\n";
            isconflictexist = true;
        }



    }


    /**
     * This method activates the registration process once the user clicks on
     * the register button. It will reference the saveCourse method below if the user
     * is already registered for the course and if the username is not already regsiered it will
     * check if there is a conflict before registering for the course.
     * @param v
     */
    public void register(View v) {

        if(btnregisterbutton.getTag().equals("drop"))
        {
            Globals sharedData = Globals.getInstance();
            saveCourse(courseID, sharedData.getUsername());
            btnregisterbutton.setTag("add");
        }
        else
        {

            Check_For_Conflicts();
        }

    }









































}