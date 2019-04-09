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

    /**
     * String password was added for the password reset implementation
     * this was added for the implementation of the password reset feature
     */
    private String password;



    private Globals() {

    }

    /**
     *
     * @return the username string
     */
    public String getUsername() {
        return username;
    }


    /**
     *
     * @param username
     * sets the string username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @param password
     * sets the string password
     */
    public void setPassword(String password){
        this.password = password;
    }

    public String getValue() {
        return notification_index;
    }


    public void setValue(String notification_index) {
        this.notification_index = notification_index;
    }

    /**
     *
     * @return the hiddentoken String
     */
    public String getHiddentoken() {
        return hiddentoken;
    }

    /**
     *
     * @param hiddentoken
     * sets the parameter string
     */

    public void setHiddentoken(String hiddentoken) {
        this.hiddentoken = hiddentoken;
    }

    /**
     * This method is used in the password reset activity
     * @return the password string
     */
    public String getPassword() {
        return password;

    }
}