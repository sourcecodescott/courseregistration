package com.example.course_registration;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Query;
import android.util.Log;

import java.lang.*;



public class RealFirestoreInstance extends FirestoreInstance{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference studentRegistrationRef;

    private List<String> document_names;
    private List<String> document_ids;


    public RealFirestoreInstance (FirebaseFirestore db) {
        this.db = db;
    }


    public interface Callback {
        void call(Integer s);
    }

    public String get_record_attribute(String collection_name, String course_id, String attribute) {

        studentRegistrationRef = db.collection(collection_name);

        studentRegistrationRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        document_names = new ArrayList<String>();
                        document_ids = new ArrayList<String>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            Log.d("myTag", "HELLO");
//                            Course course = documentSnapshot.toObject(Course.class);
//
//                            course.setDocumentId(documentSnapshot.getId());
//
//                            String documentId = course.getDocumentId();
//                            String title = course.getCourse_name();
//                            String description = course.getCourse_description();
//
//                            data +=  "\nCourse Code: " + documentId + "\nTitle: " + title + "\n\n";
//                            document_names.add(title);
//                            document_ids.add(documentId);

                        }

                    }

                });

        return "ds";

        //final Query query = db.collection(collection_name).get(course_id);
    }

    public int count_rows_by_field(String collection_name, final String field_name, final String field_value, final CallBack callback) {

        CollectionReference query = db.collection(collection_name);

        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        document_names = new ArrayList<String>();
                        document_ids = new ArrayList<String>();

                        Integer counter = 0;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                              Log.d("myTag", "HELLO"  + documentSnapshot.get("course"));
                              Log.d("myTag", "HELLO"  + documentSnapshot.get("student"));

                              if (documentSnapshot.get(field_name).equals(field_value)){
                                  counter++;
                              }

//                            Course course = documentSnapshot.toObject(Course.class);
//
//                            course.setDocumentId(documentSnapshot.getId());
//
//                            String documentId = course.getDocumentId();
//                            String title = course.getCourse_name();
//                            String description = course.getCourse_description();
//
//                            data +=  "\nCourse Code: " + documentId + "\nTitle: " + title + "\n\n";
//                            document_names.add(title);
//                            document_ids.add(documentId);

                        }

                        callback.callback(counter);

                    }

                });

        return 0;

    }

}
