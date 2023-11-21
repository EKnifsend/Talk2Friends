package com.example.talk2friends;

import static org.junit.Assert.*;

import android.content.Intent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.test.ex

public class BuildUserTest {
    User nativeJohn = new NativeSpeaker("id1", "john@usc.edu", "john", 20);
    User foreignJuan = new InternationalStudent("id2", "juan@usc.edu", "juan", 21, "spanish");

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void TestNativeJohn() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MainActivity mainActivity = new MainActivity();

        // Get the private method using reflection
        Method method = MainActivity.class.getDeclaredMethod("buildUser", Intent.class);
        method.setAccessible(true); // Make the private method accessible

        // Create an Intent with the user data
        Intent intent = new Intent();
        intent.putExtra("user", nativeJohn);

        // Invoke the private method with the Intent
        User user = (User) method.invoke(mainActivity, intent);

        // Check if the returned user matches the expected user
        assertEquals((User) nativeJohn, user);
    }
}