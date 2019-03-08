package com.example.course_registration;

import com.google.firebase.firestore.Exclude;

/**
 * Author: Ali and Nick
 * Student Class to be used with database and throughout application for login and student info
 */
public class Student {
    private String name;
    private String password;
    private String studentId;
    private String username;

    public Student() {
        //public no-arg constructor needed
    }

    /**
     *
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

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @param password
     */
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

    /**
     *
     * @return
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @param studentId
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}