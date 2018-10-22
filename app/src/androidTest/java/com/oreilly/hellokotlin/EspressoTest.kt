package com.oreilly.hellokotlin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class EspressoTest {

    // Alternative is @JvmField and @Rule instead of annotating getter
    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

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