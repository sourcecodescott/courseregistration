package com.example.course_registration.model;

import java.io.Serializable;

public class course_schedule_course implements Serializable {
    private String courseCode;
    private String courseName;
    private String courseDay;
    private String courseDescription;
    private String courseProgram;
    private String courseTime;
    private String courseLocation;

    public course_schedule_course(){

    }

    public course_schedule_course(String courseCode, String courseName, String courseDay, String courseDescription,
                  String courseProgram, String courseTime, String courseLocation){
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseDay = courseDay;
        this.courseDescription = courseDescription;
        this.courseProgram = courseProgram;
        this.courseTime = courseTime;
        this.courseLocation = courseLocation;
    }

    public String getCourseCode(){
        return this.courseCode;
    }

    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }

    public String getCourseName(){
        return this.courseName;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public String getCourseLocation(){
        return this.courseLocation;
    }

    public void setCourseLocation(String courseLocation){
        this.courseLocation = courseLocation;
    }

    public String getCourseDay(){
        return this.courseDay;
    }

    public void setCourseDay(String courseDays){
        this.courseDay = courseDays;
    }

    public String getCourseDescription(){
        return this.courseDescription;
    }

    public void setCourseDescription(String courseDescription){
        this.courseDescription = courseDescription;
    }

    public String getCourseProgram(){
        return this.courseProgram;
    }

    public void setCourseProgram(String courseProgram){
        this.courseProgram = courseProgram;
    }

    public String getCourseTime(){
        return this.courseTime;
    }

    public void setCourseTime(String courseTime){
        this.courseTime = courseTime;
    }

}
