package com.example.course_registration;

/**
 * @authors Nicholas Brisson & Mat Kallada
 * abstract class for the firestore instance function
 */
public class FirestoreInstance {
    public String get_record_attribute(String collection_name, String course_id, String attribute, CallBack ss) {
        return "";
    }

    public int count_rows_by_field(String collection_name, String field_name, String field_value, CallBack ss) {
        return 1;
    }

    public int obtain_all_document_with_attribute_equals_to(String collection_name, String field_name, String field_value, CallBack ss) {
        return 1;
    }


}
