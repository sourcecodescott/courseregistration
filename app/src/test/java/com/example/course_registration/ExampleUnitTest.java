package com.example.course_registration;

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
        final Long music_max_students = new Long(90);

        music105.put("max_students",music_max_students.toString());
        collection.put("MUSIC105", music105);
        fake_database.put("Courses", collection);


        HashMap<String, HashMap<String, String>> collection2 = new HashMap<>();
        HashMap<String, String> sric_one = new HashMap<>();
        sric_one.put("course", "MUSIC105");
        collection2.put("H3aeLS2Jenhz9PXbORsy", sric_one);
        fake_database.put("StudentRegisteredInCourse", collection2);

        MockFirestoreInstance firebase_instance = new MockFirestoreInstance(fake_database);

        final ViewCourseDetail vcd = new ViewCourseDetail();

        CallBack ss = new CallBack() {
            public void callback(Object attribute) {
                Integer current_students = (int) attribute;

                Long max_students = music_max_students;
                boolean output = vcd.test_whether_you_can_register(current_students, max_students);
                assertEquals(1, (int) current_students);

                // The test below tests the mathematical function determining whether a student can register for a course.
                assertEquals(true, output);

            };
        };

        vcd.check_number_of_students_in_course("MUSIC105", firebase_instance, ss);

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