package com.example.course_registration;

import com.google.firebase.firestore.Exclude;

/**
 * @author Dan and Pascha
 * Student class to display student information that is used across the board
 */
public class Student {
    private String name;
    private String password;
    private String studentId;
    private String username;

    /**
     * No Arg constructor
     */
    public Student() {

    }

    /**
     * Student parameteres to define a student information
     * @param name
     * @param password
     * @param studentId
     * @param username
     */
    public Student (String name, String password,String studentId,String username) {
        this.name = name;
        this.password= password;
        this.studentId = studentId;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}