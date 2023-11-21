package com.example.talk2friends;

import static org.junit.Assert.*;

import android.content.Intent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

public class BuildUserTest {
    User nativeJohn = new NativeSpeaker("id1", "john@usc.edu", "john", 20);
    User foreignJuan = new InternationalStudent("id2", "juan@usc.edu", "juan", 21, "spanish");

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void TestNativeJohn() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        activityRule.getScenario().onActivity(activity -> {
            // Create an Intent with the user data
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("user", nativeJohn);

            // Call the private method using reflection
            User user = null;
            try {
                user = activity.buildUser(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Perform assertions
            assertEquals(nativeJohn, user);
        });
    }

    @Test
    public void TestForeignJuan() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        activityRule.getScenario().onActivity(activity -> {
            // Create an Intent with the user data
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("user", foreignJuan);

            // Call the private method using reflection
            User user = null;
            try {
                user = activity.buildUser(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Perform assertions
            assertEquals(foreignJuan, user);
        });
    }
}