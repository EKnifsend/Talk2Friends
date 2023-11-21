package com.example.talk2friends;

import android.content.Intent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;


import static org.hamcrest.Matchers.allOf;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    User nativeJohn = new NativeSpeaker("id1", "john@usc.edu", "john", 20);
    User foreignJuan = new InternationalStudent("id2", "juan@usc.edu", "juan", 21, "spanish");
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Test
    public void testAssociation() {
        Intent intent = new Intent();
        intent.putExtra("user", nativeJohn);

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.association)).check(matches(withText("\uD83C\uDDFA\uD83C\uDDF8")));
    }

    @Test
    public void testName() {
        Intent intent = new Intent();
        intent.putExtra("user", nativeJohn);

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.username)).check(matches(withText("john")));
    }
}
