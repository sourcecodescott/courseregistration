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



public class RealFirestoreInstance extends FirestoreInstance{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference studentRegistrationRef;

    private List<String> document_names;
    private List<String> document_ids;


    public RealFirestoreInstance (FirebaseFirestore db) {
        this.db = db;
    }


    public String get_record_attribute(String collection_name, String course_id, String attribute) {

//        studentRegistrationRef = db.collection(collection_name);
//
//        studentRegistrationRef.get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        String data = "";
//                        document_names = new ArrayList<String>();
//                        document_ids = new ArrayList<String>();
//
//                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//
////                            Course course = documentSnapshot.toObject(Course.class);
////
////                            course.setDocumentId(documentSnapshot.getId());
////
////                            String documentId = course.getDocumentId();
////                            String title = course.getCourse_name();
////                            String description = course.getCourse_description();
////
////                            data +=  "\nCourse Code: " + documentId + "\nTitle: " + title + "\n\n";
////                            document_names.add(title);
////                            document_ids.add(documentId);
//
//                        }
//
//                    }
//
//                });

        return "ds";

        //final Query query = db.collection(collection_name).get(course_id);
    }

    public int count_rows_by_field(String collection_name, String field_name, String field_value) {

//        CollectionReference query = db.collection("StudentRegisteredInCourse");

//        query.get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        String data = "";
//                        document_names = new ArrayList<String>();
//                        document_ids = new ArrayList<String>();
//
//                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//
////                            System.out.println("HELLO");
////                            Course course = documentSnapshot.toObject(Course.class);
////
////                            course.setDocumentId(documentSnapshot.getId());
////
////                            String documentId = course.getDocumentId();
////                            String title = course.getCourse_name();
////                            String description = course.getCourse_description();
////
////                            data +=  "\nCourse Code: " + documentId + "\nTitle: " + title + "\n\n";
////                            document_names.add(title);
////                            document_ids.add(documentId);
//
//                        }
//
//                    }
//
//                });

        return 0;

    }

}
