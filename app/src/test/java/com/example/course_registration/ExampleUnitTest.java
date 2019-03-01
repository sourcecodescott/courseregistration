package com.example.course_registration;

import org.junit.Test;

import static org.junit.Assert.*;
import com.example.course_registration.ViewCourseDetail;

import com.example.course_registration.StudentRegisteredInCourse;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_if_we_can_register_for_course(){
        firebase_instance = MockFirestoreInstance();

        output= ViewCourseDetail.check_if_we_can_register_in_course("Fld9C5XdTAPhPT85WkFl", "CS100", firebase_instance);
        assertEquals(True, output);
    }

    @Test
    public void test_if_database_increases_when_we_register(){
        firebase_instance = MockFirestoreInstance();

        output= ViewCourseDetail.check_database_increment("Fld9C5XdTAPhPT85WkFl", "CS100", firebase_instance);
        assertEquals(True, output);
    }

    @Test
    public void test_get_students_in_course() {
        firebase_instance = MockFirestoreInstance();

        output= ViewCourseDetail.check_number_of_students_in_course("CS100", firebase_instance);
        assertEquals(True, output);

    }

}