package com.example.course_registration.model;

import com.google.firebase.firestore.Exclude;



import java.io.Serializable;

/**
 * Author: Dan and Pascha
 * Course Model tha twill be used across all Activities.
 * This course holds attritbutes
 */

public class Course implements Serializable{
    private String documentId;
    private String course_day;
    private String course_description;
    private String course_name;
    private String course_time;
    private String program;
    private String course_code;

    /**
     *public no-arg constructor needed
     */
    public Course() {

    }

    /**
     *
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

    /**
     *
     * @return
     */
    public String getCourse_code() {
        return course_code;
    }

    /**
     *
     * @return
     */
    public String getCourse_day() {
        return course_day;
    }

    /**
     *
     * @return
     */
    public String getCourse_description() {
        return course_description;
    }

    public String getCourse_name() {
        return course_name;
    }

    /**
     *
     * @return
     */
    public String getCourse_time() {
        return course_time;
    }

    /**
     *
     * @return
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     *
     * @return
     */
    public String getProgram() {
        return program;
    }

    /**
     *
     * @param course_day
     */
    public void setCourse_day(String course_day) {
        this.course_day = course_day;
    }

    /**
     *
     * @param course_description
     */
    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }

    /**
     *
     * @param documentId
     */

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     *
     * @param course_name
     */
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setCourse_time(String course_time) {
        this.course_time = course_time;
    }

    public void setProgram(String program) {
        this.program = program;
    }


}