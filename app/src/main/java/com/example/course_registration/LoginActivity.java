package com.example.course_registration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

/**
 * @author Nick and Ali
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference coursebookRef = db.collection("Student");

    private TextView textViewData;
    private EditText txtusername;
    private  EditText txtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        textViewData = findViewById(R.id.text_view_data);
        txtusername= findViewById(R.id.txtUsername);
        txtpassword = findViewById(R.id.txtPassword);


    }

    /**
     * Creates a view once the user logs in
     * @param v
     */
    public void login(View v) {
        coursebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        boolean successorfail = false;

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Student course = documentSnapshot.toObject(Student.class);

                            String username = course.getUsername();
                            String password = course.getPassword();

                            if(txtusername.getText().toString().equals(username)&&txtpassword.getText().toString().equals(password)) {

                                Globals sharedData = Globals.getInstance();
                                sharedData.setUsername(username);
                                sharedData.setValue("success");
                                sharedData.setPassword(password);

                                Random rand = new Random();


                                sharedData.setHiddentoken(Integer.toString(rand.nextInt(100000)));

                                successorfail = true;
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                                intent.putExtra("success","success");

                                Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }

                        if(successorfail == false) {
                            textViewData.setText("Incorrect username or password.");
                        }
                    }
                });
    }

}

