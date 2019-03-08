package com.example.course_registration;

import java.util.HashMap;

public class MockFirestoreInstance extends FirestoreInstance{

    private HashMap<String, HashMap<String, HashMap<String, String>>> database;

    public MockFirestoreInstance (HashMap database) {

        this.database = database;
    }

    public String get_record_attribute(String collection_name, String course_id, String attribute){
        HashMap<String, HashMap<String, String>> this_collection_hash_map = database.get(collection_name);
        HashMap<String, String> selected_document = this_collection_hash_map.get(course_id);
        return selected_document.get(attribute);
    }

    public int count_rows_by_field(String collection_name, String field_name, String field_value, CallBack ss) {
        HashMap<String, HashMap<String, String>> collection = database.get(collection_name);
        /*
            StudentRegisteredInCourse: {
                rEIPJ7p2pRyPL0WOgTkyrEIPJ7p2pRyPL0WOgTky: {
                    course: ART105,
                    id: rEIPJ7p2pRyPL0WOgTky
                    student: bobsimpson
                },
                H3aeLS2Jenhz9PXbORsy: {
                    course: ART105,
                    id: rEIPJ7p2pRyPL0WOgTky
                    student: bobsimpson
                }
            }

         */

        int counter = 0;

        for (String document_id : collection.keySet()) {
            HashMap<String, String> document = collection.get(document_id);
            if (document.get(field_name).equals(field_value)){
                counter++;
            }

            //System.out.println("Hello: "+ key);
        }

        return counter;

        //"Courses", "course", course_id

    }


}
