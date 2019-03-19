package com.example.course_registration;

import com.google.firebase.firestore.Exclude;

/**
 * @author Greg and Pascha
 * REturn the students registered in a course to calcuakte hwo many people are in course
 */
public class StudentRegisteredInCourse {

    private String course;
    private String student;
    private String id;

    /**
     * set parameters for regsitered in coursse, student and course
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
     * return the id of peoron in course
     * @return
     */
    public String getId() {
        return id;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    /**
     * Returns setID for the student in course 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
}