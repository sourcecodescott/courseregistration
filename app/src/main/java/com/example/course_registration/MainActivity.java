package com.example.course_registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity {


    private TextView txtStudentName;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference coursebookRef = db.collection("Student");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStudentName = findViewById(R.id.textView2);

        getFirstandLastName();

    }

    //Method called every time the activity starts.
    @Override
    protected void onStart() {
        super.onStart();
        islogggedin();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        islogggedin();
    }


    void islogggedin()
    {
        Intent intentmymy;
        intentmymy = getIntent();

        if(intentmymy.hasExtra("success") == false) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }



    }

    //Method called every time the activity stops
    @Override
    protected void onStop() {
        super.onStop();
    }



    public void schedule(View v) {



    }

    public void courselist(View v) {

        Intent intent = new Intent(MainActivity.this,view_course_list.class);

        startActivity(intent);

    }


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


                            if(username.equals(global_username))
                            {
                                txtStudentName.setText(stu.getName());
                            }


                        }

                    }
                });
    }




}




