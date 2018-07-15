package com.oreilly.hellokotlin

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EspressoTest {

    // Alternative is @JvmField and @Rule instead of annotating getter
    @get:Rule
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun nameUpdatesTextView() {
        onView(withId(R.id.edit_text))
                .perform(typeText("Dolly"))

        onView(withId(R.id.hello_button))
                .perform(click())

        onView(withId(R.id.welcome_text))
                .check(matches(withText("Hello, Dolly!")))
    }
}