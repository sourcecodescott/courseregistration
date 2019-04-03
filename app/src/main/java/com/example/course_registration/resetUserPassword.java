package com.example.course_registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class resetUserPassword extends AppCompatActivity {


    Globals myglobe = Globals.getInstance();
    String currentUser = myglobe.getUsername();
    String currentPassword = myglobe.getPassword();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_user_password);




    }

    public void resetPassword(View v){
        TextView userEnteredPassword = findViewById(R.id.passConfirm);
        TextView userNewPassword = findViewById(R.id.newPass);
        String enteredPassword = userEnteredPassword.getText().toString();
        String newPassword = userNewPassword.getText().toString();
        if(confirmPasswordsMatch(enteredPassword, currentPassword) == 1){
            updateUserPassword(newPassword);
            Toast.makeText(resetUserPassword.this, "Password updated.", Toast.LENGTH_SHORT).show();
            myglobe.setPassword(newPassword);
            currentPassword = myglobe.getPassword();
        }
        else {
            Toast.makeText(resetUserPassword.this, "Your current password does not match the password entered.", Toast.LENGTH_SHORT).show();

        }

    }

    public void updateUserPassword(String s){
        db.collection("Student").document(currentUser)
                .update(
                        "password", s
                );



    }

    public int confirmPasswordsMatch(String confirmpassword, String currpassword){
        TextView userEnteredPassword = findViewById(R.id.passConfirm);
        String enteredPassword =userEnteredPassword.getText().toString();
        if (currentPassword.equals(enteredPassword)){
            return 1;
        }
        return 0;
    }

}
