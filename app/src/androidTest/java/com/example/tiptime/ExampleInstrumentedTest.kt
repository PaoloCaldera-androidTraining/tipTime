package com.example.tiptime

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CalculatorTests {

    /* In order to interact with the activity and test it, the activity must be launched first.
        @get:Rule is an annotation aimed to indicate an operation that must be executed before
        any test in the class
     */
    @get:Rule
    //ActivityScenarioRule launches a specified activity
    val activity = ActivityScenarioRule(MainActivity::class.java)

    // Annotate each test function with @Test
    // Test functions have not to follow the camel case naming convention, but the one with _
    @Test
    fun calculate_20_percent_tip() {
        /* Insight:
            - onView(_: ViewMatcher): ViewInteraction
                The ViewMatcher is a UI component matching specific criteria
                The ViewInteraction is an object we can interact with
            - withId(_: Int): ViewMatcher
                Returns a ViewMatcher with the UI component having the id specified
            - perform(_: ViewAction): ViewInteraction
                interact with a ViewInteraction object, making it perform something
                The ViewAction object specifies an action to perform on the selected view
         */
        onView(withId(R.id.cost_of_service_value))
            .perform(typeText("50.00"))
            .perform(ViewActions.closeSoftKeyboard())

        // click() is another method, together with typeText(), that returns a ViewAction object
        onView(withId(R.id.calculate_button))
            .perform(click())

        /* Insight:
            - check(_: ViewAssertion): ViewInteraction
                The ViewAssertion is a particular assertion/condition used for UI components
            - matches. Function that takes a matcher parameter
            - withText. Function that takes a string matcher parameter
         */
        onView(withId(R.id.tip_amount))
            .check(matches(withText(containsString("$10.00"))))
    }

    @Test
    fun calculate_15_percentage_tip() {

        // Select the EditText view and provide a value
        onView(withId(R.id.cost_of_service_value))
            .perform(typeText("50.00"))
            .perform(ViewActions.closeSoftKeyboard())

        // Select the RadioButton with the 15% tip option and click it
        onView(withId(R.id.option_ok))
            .perform(click())

        // Select the round-up switch and toggle it
        onView(withId(R.id.round_up_switch))
            .perform(click())

        // Select the calculate button and click it
        onView(withId(R.id.calculate_button))
            .perform(click())

        // Select the tip amount TextView and check if the result is correct
        onView(withId(R.id.tip_amount))
            .check(matches(withText(containsString("$8.00"))))
    }
}