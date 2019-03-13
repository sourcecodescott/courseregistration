package com.example.course_registration;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.course_registration.MainActivity;
import com.example.course_registration.R;
import com.example.course_registration.ViewCourseDetail;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static com.google.protobuf.WireFormat.FieldType.MESSAGE;



@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckIfRegisterTest {
    private static String MESSAGE = "bobsimpson";

    @Rule
     public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);


    @Before
    public void setUp() throws Exception {
        //Before Test case execution 
    }

    @Test
    public void verifyMessageSentToMessageActivity() {

        // Types a message into a EditText element.
        onView(withId(R.id.txtUsername))
                .perform(typeText(MESSAGE), closeSoftKeyboard());
        MESSAGE = "test123";

        onView(withId(R.id.txtPassword))
                .perform(typeText(MESSAGE), closeSoftKeyboard());

        onView(withId(R.id.btnLogin)).perform(click());

       // intended(toPackage("com.example.course_registration.MainActivity"));


        //onView(withId(R.id.btnRegister)).check(matches(isEnabled()));
    }

    @After
    public void tearDown() throws Exception {
        //After Test case Execution
    }
}