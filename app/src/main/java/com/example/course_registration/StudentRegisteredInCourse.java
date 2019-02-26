package com.example.course_registration;

import com.google.firebase.firestore.Exclude;


public class StudentRegisteredInCourse {

    private String course;
    private String student;

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

    public void setCourse(String course) {
        this.course = course;
    }

    public void setStudent(String student) {
        this.student = student;
    }
}