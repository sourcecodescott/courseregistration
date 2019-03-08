package com.example.course_registration;

import com.google.firebase.firestore.Exclude;


public class StudentRegisteredInCourse {

    private String course;
    private String student;

    /**
     *
     * @param course
     * @param student
     */
    public StudentRegisteredInCourse(String course, String student) {
        this.course = course;
        this.student = student;
    }

    public StudentRegisteredInCourse() {
    }

    public String getCourse() {
        return course;
    }

    public String getStudent() {
        return student;
    }

    /**
     *
     * @param course
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     *
     * @param student
     */
    public void setStudent(String student) {
        this.student = student;
    }
}