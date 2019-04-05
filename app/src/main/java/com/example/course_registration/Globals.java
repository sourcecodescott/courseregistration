package com.example.course_registration;

/**
 * @author Samath
 * Global variables that will be used across the application
 */

public class Globals {


    private static Globals instance = new Globals();

    // Getter-Setters
    public static Globals getInstance() {
        return instance;
    }

    /**
     * assign instance to global instance
     * @param instance
     */
    public static void setInstance(Globals instance) {
        Globals.instance = instance;
    }

    private String notification_index;
    private String username;
    private String hiddentoken;


    private Globals() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getValue() {
        return notification_index;
    }


    public void setValue(String notification_index) {
        this.notification_index = notification_index;
    }

    public String getHiddentoken() {
        return hiddentoken;
    }

    public void setHiddentoken(String hiddentoken) {
        this.hiddentoken = hiddentoken;
    }
}