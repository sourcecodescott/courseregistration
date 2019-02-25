package com.example.course_registration;

import com.google.firebase.firestore.Exclude;

public class Student {
    private String name;
    private String password;
    private String studentId;
    private String username;


    public Student() {
        //public no-arg constructor needed
    }

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