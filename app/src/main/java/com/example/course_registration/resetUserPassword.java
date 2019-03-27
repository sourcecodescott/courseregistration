package com.example.course_registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class resetUserPassword extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Globals myglobe = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_user_password);




    }

}
