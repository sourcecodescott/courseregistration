/**
 * author: Carter and Ali
 * Espresso Test
 */
package com.example.course_registration;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;


public class ScheduleInstrumentedTest {


@Rule
public ActivityTestRule startOnLogin = new ActivityTestRule(LoginActivity.class);


    @Test
    public void mainScheduleButton_leadsToStudentSchedule(){


        Espresso.onView(ViewMatchers.withId(R.id.txtUsername))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText("bobsimpson"));
        Espresso.onView(ViewMatchers.withId(R.id.txtPassword))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText("test123"));
        Espresso.onView(ViewMatchers.withId(R.id.login_button))
                .perform(ViewActions.click());

        Intents.init();

        intended(hasComponent(MainActivity.class.getName()));

        Espresso.onView(ViewMatchers.withId(R.id.btn_schedule))
                .perform(ViewActions.click());

        intended(hasComponent(StudentScheduleList.class.getName()));


    }
}
