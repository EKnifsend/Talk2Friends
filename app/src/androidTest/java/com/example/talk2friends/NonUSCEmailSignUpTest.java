package com.example.talk2friends;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NonUSCEmailSignUpTest {

    @Rule
    public ActivityScenarioRule<AuthActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AuthActivity.class);

    @Test
    public void nonUSCEmailSignUpTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5104);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.usernameEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.auth_nav_host_fragment),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.usernameEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.auth_nav_host_fragment),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("johnmurnen45"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.emailEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.auth_nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("murnen@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.passwordEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.auth_nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("validpassword456%"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.ageEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.auth_nav_host_fragment),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("22"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.signUpButton), withText("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.auth_nav_host_fragment),
                                        0),
                                5),
                        isDisplayed()));
        materialButton.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
