package com.example.course_registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Authors: Nick & Carter
 *
 * This class contains the implementation for the password reset feature
 *
 * It will first query the database to check if the user's typed password
 * matches the password found in the database for that user. If so, it will
 * update the password to the new password provided by the user. If not, it will
 * not allow the change.
 */
public class resetUserPassword extends AppCompatActivity {


    Globals myglobe = Globals.getInstance();
    String currentUser = myglobe.getUsername();
    String currentPassword = myglobe.getPassword();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    /**
     * Set the content view (UI)
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_user_password);




    }

    /**
     * This method is where the core of the change password will take place.
     * It first reads the current password provided from the user ('userEnteredPassword')
     * and makes sure that it matches the one in the database. If it does, it will first
     * update the user's password in the database using the 'updateUserPassword' method seen below.
     * It will then set the globals password value seen in the 'Globals.java' file to the
     * updated password. Finally, it notifies the user of the update success or fail if the password
     * provided initially didnt't match the existing one on the server.
     *
     * @param v
     */
    public void resetPassword(View v){
        TextView userNewPassword = findViewById(R.id.newPass);
        String newPassword = userNewPassword.getText().toString();
        if(confirmPasswordsMatch() == 1){
            updateUserPassword(newPassword);
            Toast.makeText(resetUserPassword.this, "Password updated.", Toast.LENGTH_SHORT).show();
            myglobe.setPassword(newPassword);
            currentPassword = myglobe.getPassword();
        }
        else {
            Toast.makeText(resetUserPassword.this, "Your current password does not match the password entered.", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * This method updates the password on the database to the new one.
     * @param s
     */
    public void updateUserPassword(String s){
        db.collection("Student").document(currentUser)
                .update(
                        "password", s
                );



    }

    /**
     * This method checks if the passwords match and returns an integer. 1 is a success
     * and 0 is a fail. 
     * @return
     */
    public int confirmPasswordsMatch(){
        TextView userEnteredPassword = findViewById(R.id.passConfirm);
        String enteredPassword =userEnteredPassword.getText().toString();
        if (currentPassword.equals(enteredPassword)){
            return 1;
        }
        return 0;
    }

}
