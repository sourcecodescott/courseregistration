package com.example.course_registration;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * @authors Nicholas Brisson & Mat Kallada
 * Unit testing for the course details functions that displays the number of students
 * registered in a course and determines if there is room for a student to register.
 */
public class ExampleUnitTest {


    
    /**
     * Test if the function that blocks a student from a registering for a full course
     * works. This entails returning the current amount of registered students in a course
     * from the mock database.
     */
    @Test
    public void test_if_blocking_course_registration_on_full_course(){
        HashMap<String, HashMap<String, HashMap<String, String>>> fake_database = new HashMap<>();
        HashMap<String, HashMap<String, String>> collection = new HashMap<>();
        HashMap<String, String> music105 = new HashMap<>();
        Long music_max_students = new Long(90);

        music105.put("max_students",music_max_students.toString());
        collection.put("MUSIC105", music105);
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

    /**
     * Test method that checks if we are able to retrieve the max amount of students
     * from the mock database.
     */
    @Test
    public void test_whether_we_can_get_field_of_a_record_from_database() {
        HashMap<String, HashMap<String, HashMap<String, String>>> fake_database = new HashMap<>();
        HashMap<String, HashMap<String, String>> collection = new HashMap<>();
        HashMap<String, String> music105 = new HashMap<>();
        Long music_max_students = new Long(90);
        music105.put("max_students",music_max_students.toString());
        collection.put("MUSIC105", music105);
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

    /**
     * Test if we are able to query the amount of students currently enrolled in
     * the current course from the mock database
     */
    @Test
    public void test_function_that_counts_number_of_students_in_course() {
        HashMap<String, HashMap<String, HashMap<String, String>>> fake_database = new HashMap<>();
        HashMap<String, HashMap<String, String>> collection = new HashMap<>();
        HashMap<String, String> music105 = new HashMap<>();
        music105.put("max_students", "90");
        collection.put("MUSIC105", music105);
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
    //tests particular method in studentschedulelist - checkOrder functions correctly in ordering classes
    @Test
    public void test_function_that_courses_ordered_correctly(){
        HashMap<String, HashMap<String, HashMap<String, String>>> fake_database = new HashMap<>();
        HashMap<String, HashMap<String, String>> collection = new HashMap<>();
        HashMap<String, String>  art100 = new HashMap<>();
        art100.put("start_time", "9:30");
        collection.put("MUSIC105", art100);
        fake_database.put("Courses", collection);
        HashMap<String, String> music200 = new HashMap<>();
        music200.put("start_time", "10:00");
        collection.put("MUSIC200", music200);
        fake_database.put("Courses", collection);
        HashMap<String, String> csci350 = new HashMap<>();
        csci350.put("start_time", "7:00");
        collection.put("CSCI350", csci350);
        HashMap<String, String> chem200 = new HashMap<>();
        chem200.put("start_time", "13:00");
        collection.put("CHEM200", chem200);
        fake_database.put("Courses", collection);

        ArrayList<String> mockArrayList = new ArrayList<String>();
        mockArrayList.add(art100.get("start_time"));
        mockArrayList.add(music200.get("start_time"));
        mockArrayList.add(csci350.get("start_time"));
        mockArrayList.add(chem200.get("start_time"));

        StudentScheduleList ssl = new StudentScheduleList();
        ssl.checkOrder(mockArrayList);
        int rightOrder[] = {2, 0, 1, 3};
        assertArrayEquals(rightOrder, ssl.checkOrder(mockArrayList));



    }

    /**
     * Tests to see if a student is in a course, before they can drop it, using the mock database.
     * Checks to see if both the student name and course name is in the StudentRegisteredInCourse collection
     * then ensures these two values match the values in the student and course collection
     */
    @Test
    public void test_if_a_student_is_in_a_course() {
        HashMap<String, HashMap<String, HashMap<String, String>>> fake_database = new HashMap<>();
        HashMap<String, HashMap<String, String>> collection = new HashMap<>();
        HashMap<String, String> art100 = new HashMap<>();
        art100.put("Course Name", "ART100");
        collection.put("ART100", art100);
        fake_database.put("Courses", collection);

        HashMap<String, HashMap<String, String>> collection2 = new HashMap<>();
        HashMap<String, String> student_course_link = new HashMap<>();
        student_course_link.put("name", "Henry");
        student_course_link.put("course", "ART100");
        collection2.put("H3aeLS2Jenhz9PXbORsy", student_course_link);
        fake_database.put("StudentRegisteredInCourse", collection2);

        HashMap<String, HashMap<String, String>> collection3 = new HashMap<>();
        HashMap<String, String> student = new HashMap<>();
        student.put("name", "Henry");
        collection3.put("student1", student);
        fake_database.put("Students", collection3);

        final String student_name = "Henry";
        final String course_name = "ART100";
        MockFirestoreInstance firebase_instance = new MockFirestoreInstance(fake_database);
        CallBack ss = new CallBack() {
            public void callback(Object attribute) {
                assertEquals(student_name, attribute);
            };
        };
        firebase_instance.get_record_attribute("StudentRegisteredInCourse", "H3aeLS2Jenhz9PXbORsy", "name", ss);
        ss = new CallBack() {
            public void callback(Object attribute) {
                assertEquals(student_name, attribute);
            };
        };
        firebase_instance.get_record_attribute("Students", "student1", "name", ss);
        ss = new CallBack() {
            public void callback(Object attribute) {
                assertEquals(course_name, attribute);
            };
        };
        firebase_instance.get_record_attribute("StudentRegisteredInCourse", "H3aeLS2Jenhz9PXbORsy", "course", ss);
        ss = new CallBack() {
            public void callback(Object attribute) {
                assertEquals(course_name, attribute);
            };
        };
        firebase_instance.get_record_attribute("Courses", "ART100", "Course Name", ss);


    }
}