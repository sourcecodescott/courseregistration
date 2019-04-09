package com.example.course_registration;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;



@RunWith(AndroidJUnit4.class)
/**
 * This class is an espresso test for the program requirements
 * activity. It will first login to the test user and then click
 * on the program requirements button on the main activity (landing page)
 * and then click on the CS program to make sure all of the courses in
 * the CS program show up.
 */
public class ProgramRequirementsTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
        onView(withId(R.id.txtUsername))
                .perform(typeText("bobsimpson"), closeSoftKeyboard());
        onView(withId(R.id.txtPassword))
                .perform(typeText("test123"), closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.login_button))
                .perform(ViewActions.click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.btn_program_requirements))
                .perform(ViewActions.click());

        onView(withText("CS"))
            .inRoot(isDialog()) // <---
            .check(matches(isDisplayed()))
            .perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.txt_program_requirements)).check(matches(withText(containsString(
                "\nCS100: A basic introduction to basic logic operators\n\n\nCS105: An introduction to computer science for lower-year students.\n\n\nCS200: Learn social skills that help you in the real world!\n\n\nCS205: Explore the world of world wide web\n\n"))));

    }
    
}