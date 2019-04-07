package com.example.course_registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * @author Samath and Dan
 * Main activity displays user information and options for user to view
 */
public class MainActivity extends AppCompatActivity {

    private TextView txtStudentName;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference coursebookRef = db.collection("Student");

    private CollectionReference reg_courses = db.collection("StudentRegisteredInCourse");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Main Menu");
        txtStudentName = findViewById(R.id.textView2);
        getFirstandLastName();

    }

    /**
     * Method called everytime the activity starts
     */
    @Override
    protected void onStart() {
        super.onStart();
        islogggedin();
    }

    /**
     * Method called to resume activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        islogggedin();
    }

    public void setHiddenToken() {

        Globals sharedData = Globals.getInstance();
        final String  user = sharedData.getUsername();
        final String  token = sharedData.getHiddentoken();



        reg_courses.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            StudentRegisteredInCourse isreg = documentSnapshot.toObject(StudentRegisteredInCourse.class);

                            String courseisReg = isreg.getCourse();
                            String studentisReg = isreg.getStudent();

                            if(studentisReg.equals(user))
                            {
                                DocumentReference mycourse = db.collection("Courses").document(courseisReg);
                                mycourse.update("hiddentoken",token);
                            }
                        }
                    }

                });
    }

    void islogggedin() {
        Intent intentmymy;
        intentmymy = getIntent();

        if(intentmymy.hasExtra("success") == false) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            setHiddenToken();
        }
    }

    /**
     * Method called whenever method is stopped
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Student scehdule Activity
     * @param v
     */
    public void schedule(View v) {
        Intent intent = new Intent(MainActivity.this, StudentScheduleList.class);
        startActivity(intent);
    }

    /**
     * Student scehdule Password Reset
     * @param v
     */
    public void password_reset(View v) {
        Intent intent = new Intent(MainActivity.this, resetUserPassword.class);
        startActivity(intent);
    }

    /**
     * Student Program requirements to view program requirements
     * @param v
     */
    public void program_requirements(View v) {

        final String[] items = {
                "ART",
                "CS",
                "ENG",
                "MUSIC"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose a Program")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, ProgramRequirementsActivity.class);

                        Bundle b = new Bundle();
                        b.putString("program", items[which]);
                        intent.putExtras(b);

                        startActivity(intent);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Student courselist to add and drop courses
     * @param v
     */
    public void courselist(View v) {
        Intent intent = new Intent(MainActivity.this,view_course_list.class);
        startActivity(intent);
    }

    /**
     * Retrieve student name
     */
    public void getFirstandLastName() {
        coursebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        Globals sharedData = Globals.getInstance();
                        final String global_username = sharedData.getUsername();


                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Student stu = documentSnapshot.toObject(Student.class);

                            String username = stu.getUsername();

                            if(username.equals(global_username)) {
                                txtStudentName.setText(stu.getName());
                            }
                        }
                    }
                });
    }
}




