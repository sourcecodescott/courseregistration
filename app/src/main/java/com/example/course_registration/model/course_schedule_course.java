package com.example.course_registration.model;

import java.io.Serializable;

public class course_schedule_course implements Serializable {
    private String courseCode;
    private String courseName;
    private String courseDay;
    private String courseDescription;
    private String courseProgram;
    private String courseTime;

    public course_schedule_course(){

    }

    public course_schedule_course(String courseCode, String courseName, String courseDay, String courseDescription,
                  String courseProgram, String courseTime){
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseDay = courseDay;
        this.courseDescription = courseDescription;
        this.courseProgram = courseProgram;
        this.courseTime = courseTime;
    }

    public String getCourseCode(){
        return this.courseCode;
    }

    public String getCourseName(){
        return this.courseName;
    }

    public String getCourseDay(){
        return this.courseDay;
    }

    public String getCourseDescription(){
        return this.courseDescription;
    }

    public String getCourseProgram(){
        return this.courseProgram;
    }

    public String getCourseTime(){
        return this.courseTime;
    }



}
