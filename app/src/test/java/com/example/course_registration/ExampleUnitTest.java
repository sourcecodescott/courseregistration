package com.example.course_registration;

import org.junit.Test;
import static org.junit.Assert.*;



import java.util.HashMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void broken_unit_test() {
        assertEquals(false, true);
    }

    @Test
    public void test_if_blocking_course_registration_on_full_course(){
        HashMap<String, HashMap<String, HashMap<String, String>>> fake_database = new HashMap<>();
        HashMap<String, HashMap<String, String>> collection = new HashMap<>();
        HashMap<String, String> art100 = new HashMap<>();
        Long music_max_students = new Long(90);
        art100.put("max_students",music_max_students.toString());
        collection.put("MUSIC105", art100);
        fake_database.put("Courses", collection);

        HashMap<String, HashMap<String, String>> collection2 = new HashMap<>();
        HashMap<String, String> sric_one = new HashMap<>();
        sric_one.put("course", "MUSIC105");
        collection2.put("H3aeLS2Jenhz9PXbORsy", sric_one);
        fake_database.put("StudentRegisteredInCourse", collection2);

        MockFirestoreInstance firebase_instance = new MockFirestoreInstance(fake_database);

        ViewCourseDetail vcd = new ViewCourseDetail();

        CallBack ss = new CallBack() {
            public void callback(Object attribute) {
                // A callback that does nothing
            };
        };

        int current_students = vcd.check_number_of_students_in_course("MUSIC105", firebase_instance, ss);
        Long max_students = music_max_students;
        boolean output = vcd.test_whether_you_can_register(current_students, max_students);
        assertEquals(1, current_students);

        // The test below tests the mathematical function determining whether a student can register for a course.
        assertEquals(true, output);

    }

    @Test
    public void test_whether_we_can_get_field_of_a_record_from_database() {
        HashMap<String, HashMap<String, HashMap<String, String>>> fake_database = new HashMap<>();
        HashMap<String, HashMap<String, String>> collection = new HashMap<>();
        HashMap<String, String> art100 = new HashMap<>();
        Long music_max_students = new Long(90);
        art100.put("max_students",music_max_students.toString());
        collection.put("MUSIC105", art100);
        fake_database.put("Courses", collection);

        HashMap<String, HashMap<String, String>> collection2 = new HashMap<>();
        HashMap<String, String> sric_one = new HashMap<>();
        sric_one.put("course", "MUSIC105");
        collection2.put("H3aeLS2Jenhz9PXbORsy", sric_one);
        fake_database.put("StudentRegisteredInCourse", collection2);

        MockFirestoreInstance firebase_instance = new MockFirestoreInstance(fake_database);

        CallBack ss = new CallBack() {
            public void callback(Object attribute) {
                assertEquals("90", attribute);
            };
        };

        firebase_instance.get_record_attribute("Courses", "MUSIC105", "max_students", ss);

    }

    @Test
    public void test_function_that_counts_number_of_students_in_course() {
        HashMap<String, HashMap<String, HashMap<String, String>>> fake_database = new HashMap<>();
        HashMap<String, HashMap<String, String>> collection = new HashMap<>();
        HashMap<String, String> art100 = new HashMap<>();
        art100.put("max_students", "90");
        collection.put("MUSIC105", art100);
        fake_database.put("Courses", collection);

        HashMap<String, HashMap<String, String>> collection2 = new HashMap<>();
        HashMap<String, String> sric_one = new HashMap<>();
        sric_one.put("course", "MUSIC105");
        collection2.put("H3aeLS2Jenhz9PXbORsy", sric_one);
        fake_database.put("StudentRegisteredInCourse", collection2);

        MockFirestoreInstance firebase_instance = new MockFirestoreInstance(fake_database);

        ViewCourseDetail vcd = new ViewCourseDetail();

        CallBack ss = new CallBack() {
            public void callback(Object counted) {
                assertEquals(1, (int) counted);
            };
        };

        vcd.check_number_of_students_in_course("MUSIC105", firebase_instance, ss);

    }

}