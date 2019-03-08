package com.example.course_registration;

/**
 * Author: Samath and Carter
 * Global Variables
 */

public class Globals {

    private static Globals instance = new Globals();


    /**
     *
     * @return instance
     */
    public static Globals getInstance() {
        return instance;
    }

    /**
     *
     * @param instance
     */
    public static void setInstance(Globals instance) {
        Globals.instance = instance;
    }

    private String notification_index;
    private String username;


    private Globals() {

    }

    public String getUsername() {
        return username;
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
     * @return
     */
    public String getValue() {
        return notification_index;
    }

    /**
     *
     * @param notification_index
     */
    public void setValue(String notification_index) {
        this.notification_index = notification_index;
    }
}