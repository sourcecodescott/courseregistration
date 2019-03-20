package com.example.course_registration;

import org.junit.Test;

import java.util.HashMap;
import static org.junit.Assert.assertEquals;
public class ProgramRequirementsUnitTest {


    @Test
    public void purposely_failing_test() {
        assertEquals("dasasd", "dasa233");
    }

    @Test
    public void test_if_generated_string_for_program_requirements_is_correct(){
        HashMap<String, HashMap<String, HashMap<String, String>>> fake_database = new HashMap<>();
        HashMap<String, HashMap<String, String>> collection = new HashMap<>();

        final Long music_max_students = new Long(90);

        HashMap<String, String> music105 = new HashMap<>();
        music105.put("max_students",music_max_students.toString());
        music105.put("program", "MUSIC");
        music105.put("course_description", "Learn how to play the recorder and preform in front of everyone. ");
        music105.put("course_code", "MUSIC105");
        collection.put("MUSIC105", music105);

        HashMap<String, String> music200 = new HashMap<>();
        music200.put("max_students",music_max_students.toString());
        music200.put("program", "MUSIC");
        music200.put("course_description", "play piano like a boss");
        music200.put("course_code", "MUSIC200");
        collection.put("MUSIC200", music200);

        HashMap<String, String> csci105 = new HashMap<>();
        csci105.put("max_students",music_max_students.toString());
        csci105.put("program", "CS");
        csci105.put("course_description", "An introduction to computer science for lower-year students.");
        csci105.put("course_code", "CS105");
        collection.put("CS105", csci105);

        fake_database.put("Courses", collection);


        MockFirestoreInstance firebase_instance = new MockFirestoreInstance(fake_database);
        final ProgramRequirementsActivity pra = new ProgramRequirementsActivity();


        CallBack ss = new CallBack() {
            public void callback(Object string) {
                String expected="\nMUSIC105: Learn how to play the recorder and preform in front of everyone. \n\n\nMUSIC200: play piano like a boss\n\n";
                assertEquals(expected, string);
            };
        };

        pra.create_course_requirements_text("MUSIC", ss, firebase_instance);

    }

}
