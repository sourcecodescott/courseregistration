package com.example.course_registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @authors Nicholas Brisson & Mat Kallada
 * This class is a mock database that emulates our real database on
 * firebase. It is used for the unit testing.
 */
public class MockFirestoreInstance extends FirestoreInstance{

    private HashMap<String, HashMap<String, HashMap<String, String>>> database;

    /**
     * Instantiate the fake database
     * @param database reference to a fake database of type HashMap
     *                 in order to replicate it as best as possible.
     */
    public MockFirestoreInstance (HashMap database) {

        this.database = database;
    }

    /**
     *
     * @param collection_name the identifying name of the appropriate collection
     * @param course_id the course code that identifies the course we are viewing
     * @param attribute
     * @param ss use callback in order to return the number of students
     * @return the record
     */
    public String get_record_attribute(String collection_name, String course_id, String attribute, CallBack ss){
        HashMap<String, HashMap<String, String>> this_collection_hash_map = database.get(collection_name);
        HashMap<String, String> selected_document = this_collection_hash_map.get(course_id);

        ss.callback(selected_document.get(attribute));

        return "1";
    }

    /**
     *  This method returns the amount of student instances that are in a course
     * @param collection_name the identifying name of the appropriate collection
     * @param field_name name of the field
     * @param field_value value of that field (student name)
     * @param ss callback
     * @return the amount of students in the course
     */
    public int count_rows_by_field(String collection_name, String field_name, String field_value, CallBack ss) {
        HashMap<String, HashMap<String, String>> collection = database.get(collection_name);

        int counter = 0;

        for (String document_id : collection.keySet()) {
            HashMap<String, String> document = collection.get(document_id);
            if (document.get(field_name).equals(field_value)){
                counter++;
            }

        }

        ss.callback(counter);

        return 0;
    }




    public int obtain_all_document_with_attribute_equals_to(String collection_name, final String field_name, final String field_value, final CallBack callback){

        ArrayList<Map<String, Object>> objs = new ArrayList<Map<String, Object>>();

        HashMap<String, HashMap<String,String>> ds = database.get(collection_name);
        for(Map.Entry<String, HashMap<String,String>> entry : ds.entrySet()) {

            String key = entry.getKey();
            HashMap value = entry.getValue();

            if (value.get(field_name).equals(field_value)){
                objs.add(value);
            }
        }

        callback.callback(objs);


        return 1;
    }

}
