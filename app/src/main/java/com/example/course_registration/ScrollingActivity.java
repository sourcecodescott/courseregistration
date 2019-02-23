package com.example.course_registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.Arrays;
import android.content.DialogInterface;
import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.example.course_registration.model.Course;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.app.AlertDialog;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ScrollingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private List<String> document_names;
    private List<String> document_ids;

    @Override
    public void onStart() {

        final String user_id = "Fld9C5XdTAPhPT85WkFl";

        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference coursebookRef = db.collection("Courses");

        coursebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        document_names = new ArrayList<String>();
                        document_ids= new ArrayList<String>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Course course = documentSnapshot.toObject(Course.class);
                            course.setDocumentId(documentSnapshot.getId());

                            String documentId = course.getDocumentId();
                            String title = course.getCourse_name();
                            String description = course.getCourse_description();

                            data +=  "\nCourse Code: " + documentId + "\nTitle: " + title + "\n\n";
                            document_names.add(title);
                            document_ids.add(documentId);

                        }


                        String[] ListElements = document_names.toArray(new String[document_names.size()]);

                        ListView course_listview = (ListView) findViewById(R.id.listview_dynamic);

                        final List < String > ListElementsArrayList = new ArrayList < String >
                                (Arrays.asList(ListElements));


                        final ArrayAdapter < String > adapter = new ArrayAdapter < String >
                                (ScrollingActivity.this, android.R.layout.simple_list_item_1,
                                        ListElementsArrayList);

                        course_listview.setAdapter(adapter);

                        course_listview.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {
                                FirebaseFirestore db2 = FirebaseFirestore.getInstance();
                                CollectionReference registrationRef = db2.collection("StudentRegisteredInCourse");

                                Map<String, Object> new_registration = new HashMap<>();
                                new_registration.put("course", document_ids.get(position));
                                new_registration.put("student", user_id);

                                String uniqueID = UUID.randomUUID().toString();

                                db2.collection("StudentRegisteredInCourse").document(uniqueID )
                                        .set(new_registration)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Log.d(TAG, "DocumentSnapshot successfully written!");
                                            }
                                        });



                                new AlertDialog.Builder(ScrollingActivity.this)
                                        .setTitle("Success")
                                        .setMessage("You have registered successfully for this course.")

//                                        // Specifying a listener allows you to take an action before dismissing the dialog.
//                                        // The dialog is automatically dismissed when a dialog button is clicked.
//                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                // Continue with delete operation
//                                            }
//                                        })
//
//                                        // A null listener allows the button to dismiss the dialog and take no further action.
//                                        .setNegativeButton(android.R.string.no, null)
//                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();

                                System.out.println("position" + document_ids.get(position));
//                                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

                            }
                        });

                    }





                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Button btn= (Button) findViewById(R.id.go_back_to_menu);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScrollingActivity.this, MainActivity.class));
            }

        });
    }
}
