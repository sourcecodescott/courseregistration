package com.example.course_registration;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.*;
//import com.android.courseregistration.activity.MainActivity;
//
public class MainActivityTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.course_registration", appContext.getPackageName());
    }

}