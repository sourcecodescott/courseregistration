package com.example.course_registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;


public class ProgramRequirementsActivity extends AppCompatActivity {

    private FirebaseFirestore db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_requirements);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();

        db = FirebaseFirestore.getInstance();
        String value = ""; // or other values
        if(b != null) {
            value = b.getString("program");
            CallBack ss = new CallBack() {
                public void callback(Object program_requirements_string) {
                    TextView textview_program_requirements= findViewById(R.id.txt_program_requirements);
                    textview_program_requirements.setText(((String)program_requirements_string));
                };
            };

            RealFirestoreInstance rfi = new RealFirestoreInstance(db);

            create_course_requirements_text(value, ss, rfi);
        }

    }

    public String create_course_requirements_text(String program, final CallBack s2, FirestoreInstance firebase_instance){

        CallBack ss = new CallBack() {
            public void callback(Object objs) {

                ArrayList<Map<String, Object>> list_of_courses = (ArrayList<Map<String, Object>>) objs;

                String program_requirements_string = "";
                for(int i=0; i<list_of_courses.size(); i++) {
                    String course_code = (String) list_of_courses.get(i).get("course_code");
                    String course_description = (String) list_of_courses.get(i).get("course_description");

                    program_requirements_string+="\n"+ course_code +": " + course_description +"\n\n";
                }

                s2.callback(program_requirements_string);

            };
        };

        firebase_instance.obtain_all_document_with_attribute_equals_to("Courses", "program", program, ss);

        // Return status of async job
        return "Success";
    }
}