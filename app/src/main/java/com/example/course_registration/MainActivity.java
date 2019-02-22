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
import android.widget.TextView;
import android.widget.Button;

import com.example.course_registration.model.Course;
import com.example.course_registration.viewholder.viewholder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter adapter = setUpAdapter(db);
    private CollectionReference coursebookRef = db.collection("Courses");

    //private DocumentReference courseRef = db.document("Courses/My First Course");
    private TextView textViewData;
    private Button details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewData = findViewById(R.id.text_view_data);
        details = findViewById(R.id.details);
        setUpRecyclerView(recyclerView,adapter);
        //loadCourses();
    }

    private void setUpRecyclerView(RecyclerView rv, FirestoreRecyclerAdapter adapter)
    {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }
   /* public void loadCourses() {
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

                            data +=  "\nCourse Code: " + documentId + "\nTitle: " + title + "\n\n";

                        }
                        details.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent( )
                            }
                        });
                        textViewData.setText(data);
                    }
                });
    }*/

   private FirestoreRecyclerAdapter setUpAdapter(FirebaseFirestore db) {
       Query query = db.collection("course")
       FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>()
               .setQuery(query,Course.class)
               .build();

       FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Course,viewholder>(options)
       {
           @Override
           public void onBindViewHolder(viewholder holder, int position, final Course model) {
               holder.textData.setText(model.getDocumentId());

              /* holder.detailsButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(MainActivity.this,courseDetail.class);
                       intent.putExtra("courses",model);
                       startActivity(intent);

                   }
               });*/
           }

           @Override
           public viewholder onCreateViewHolder(ViewGroup group, int i)
           {
               View view = LayoutInflater.from(group.getContext())
                       .inflate(R.layout.activity_main,group,false);
               return new viewholder(view);

           }
       };

       return adapter;
   }
}
