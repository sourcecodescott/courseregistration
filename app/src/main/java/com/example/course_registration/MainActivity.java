//Taken from julianos Assignment 3 to help create a recycler view 
// Used as Main content for Course list Main activity. 
//
package com.example.course_registration;

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
import com.example.course_registration.model.Course;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button addContactButton;
    private FirebaseFirestore database;
    private FirestoreRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.contactlist);
        database = FirebaseFirestore.getInstance();

        adapter = setUpAdapter(database);
        setUpRecyclerView(recyclerView,adapter);

    }

    private void setUpRecyclerView(RecyclerView rv, FirestoreRecyclerAdapter adapter)
    {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }


    private FirestoreRecyclerAdapter setUpAdapter(FirebaseFirestore db)
    {
        final Query query = db.collection("Courses").orderBy("course_code").limit(50);
        FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>()
                .setQuery(query,Course.class)
                .build();


        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Course,ContactViewHolder>(options)
        {
  
            @Override
            public void onBindViewHolder(ContactViewHolder holder, int position, final Course model)
            {
                holder.course.setText(model.getCourse_code());
                holder.coursenum.setText(model.getCourse_name());

        
                holder.detailsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,ViewCourseDetail.class);

                        intent.putExtra("contact",model);
                        startActivity(intent);
\
                    }
                });
            }

            @Override
            public ContactViewHolder onCreateViewHolder(ViewGroup group, int i)
            {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.contact_entry,group,false);
                return new ContactViewHolder(view);

            }
        };

        return adapter;

    }

    @Override
    protected void onStart() {
        super.onStart();


        islogggedin();


        adapter.startListening();
    }

//resume function to maintain status of log in
    @Override
    protected void onResume()
    {
        super.onResume();

        islogggedin();
    }

// login function added to see login as user
    void islogggedin()
    {
        Intent intentmymy;
        intentmymy = getIntent();

        if(intentmymy.hasExtra("success") == false) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }



    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}




