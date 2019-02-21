package com.example.course_registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



public class MainActivity extends AppCompatActivity {



    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference coursebookRef = db.collection("Courses");
    //private DocumentReference courseRef = db.document("Courses/My First Course");

    private TextView textViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewData = findViewById(R.id.text_view_data);

        loadCourses();
    }


    public void loadCourses() {
        coursebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Course course = documentSnapshot.toObject(Course.class);
                            course.setDocumentId(documentSnapshot.getId());

                            String documentId = course.getDocumentId();

                            String title = course.getCourse_name();
                            String description = course.getCourse_description();

                            data +=  "\nCourse Code: " + documentId + "\nTitle: " + title + "\nDescription: " + description
                                    + "\n\n";

                        }

                        textViewData.setText(data);
                    }
                });
    }

}
//new activity needs to grab data