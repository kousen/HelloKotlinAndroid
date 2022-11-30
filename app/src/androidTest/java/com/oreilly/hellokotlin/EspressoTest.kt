package com.oreilly.hellokotlin

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EspressoTest {

    // Alternative is @JvmField and @Rule instead of annotating getter
//    @get:Rule
//    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun nameUpdatesTextView() {
        launchActivity<MainActivity>()

        onView(withId(R.id.edit_text))
                .perform(typeText("Dolly"))

        onView(withId(R.id.hello_button))
                .perform(click())

        onView(withId(R.id.welcome_text))
                .check(matches(withText("Hello, Dolly!")))
    }
}