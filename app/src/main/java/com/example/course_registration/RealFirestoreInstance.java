package com.example.course_registration;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * @authors Nicholas Brisson & Mat Kallada
 * This class creates an instance of the real database on firestore
 */
public class RealFirestoreInstance extends FirestoreInstance{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference studentRegistrationRef;

    private List<String> document_names;
    private List<String> document_ids;

    /**
     * Create new instance of the database
     * @param db the actual database variable
     */
    public RealFirestoreInstance (FirebaseFirestore db) {
        this.db = db;
    }

    /**
     *
     * @param collection_name the identifying name of the appropriate collection
     * @param course_id the course code that identifies the course we are viewing
     * @param attribute
     * @param callback use callback in order to return the number of students
     * @return the record
     */
    public String get_record_attribute(final String collection_name, final String course_id, final String attribute, final CallBack callback) {

        studentRegistrationRef = db.collection(collection_name);

        studentRegistrationRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        document_names = new ArrayList<String>();
                        document_ids = new ArrayList<String>();

                        Object found = "0";
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            if (documentSnapshot.getId().equals(course_id)){
                                found = documentSnapshot.get(attribute);

                            }


                        }

                        callback.callback(found);

                    }

                });

        return "Asychronous Job Succcess";
    }

    /**
     *  This method returns the amount of student instances that are in a course
     * @param collection_name the identifying name of the appropriate collection
     * @param field_name name of the field
     * @param field_value value of that field (student name)
     * @param callback callback for return
     * @return the amount of students in the course
     */
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

                              if (documentSnapshot.get(field_name).equals(field_value)){
                                  counter++;
                              }


                        }

                        callback.callback(counter);

                    }

                });

        return 1;

    }

    public int obtain_all_document_with_attribute_equals_to(String collection_name, final String field_name, final String field_value, final CallBack callback){
        CollectionReference query = db.collection(collection_name);

        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        document_names = new ArrayList<String>();
                        document_ids = new ArrayList<String>();

                        ArrayList<Map<String, Object>> objs = new ArrayList<Map<String, Object>>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            if (documentSnapshot.get(field_name).equals(field_value)){
                                objs.add(documentSnapshot.getData());
                            }


                        }

                        callback.callback(objs);

                    }

                });

        // Successfully called the async function
        return 1;

    }
    public int count_rows_by_field(String collection_name, final String field_name, final String field_value, final String condition, final CallBack callback) {

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

                            if (documentSnapshot.get(field_name).equals(field_value) && documentSnapshot.get(condition).equals("Full")){
                                counter++;
                            }


                        }

                        callback.callback(counter);

                    }

                });

        return 0;

    }

}
