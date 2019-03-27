package com.example.course_registration.model;

import java.io.Serializable;

/**
 * @author Dan and Pascha
 * Course class sets different student information
 */

public class Course implements Serializable{
    private String documentId;
    private String course_day;
    private String course_description;
    private String course_name;
    private String course_time;
    private String program;
    private String course_code;
    private String start_time;
    private String end_time;
    private int max_students;



    public Course() {
        //public no-arg constructor needed
    }

    /**
     * Set Course information
     * @param course_day
     * @param course_description
     * @param course_name
     * @param course_time
     * @param program
     */
    public Course(String course_day, String course_description,String course_name,String course_time,String program) {
        this.course_day = course_day;
        this.course_description = course_description;
        this.course_name = course_description;
        this.course_time = course_description;
        this.program = course_description;
    }

    /**
     *
     * @param course_code
     */
    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_code() {
        return course_code;
    }

    public String getCourse_day() {
        return course_day;
    }

    public String getCourse_description() {
        return course_description;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getCourse_time() {
        return course_time;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getProgram() {
        return program;
    }

    public void setCourse_day(String course_day) {
        this.course_day = course_day;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setCourse_time(String course_time) {
        this.course_time = course_time;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public int getMax_students() {return max_students;}

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }


}