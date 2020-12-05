package edu.gwu.androidtweets

import android.content.Context
import android.content.SharedPreferences
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.hamcrest.CoreMatchers.not
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.lang.Thread.sleep

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    @After
    fun clearSharedPreferences() {
        val sharedPreferences: SharedPreferences =
            getInstrumentation().targetContext.getSharedPreferences(
                "android-tweets",
                Context.MODE_PRIVATE
            )
        sharedPreferences.edit().clear().commit()
    }

    @Test
    fun testButtonStates() {
        val username = onView(withHint("Username"))
        val password = onView(withHint("Password"))
        val login = onView(withId(R.id.login))
        val signUp = onView(withId(R.id.signUp))
        val progressBar = onView(withId(R.id.progressBar))

        login.check(matches(not(isEnabled())))
        signUp.check(matches(not(isEnabled())))

        username.perform(typeText("nick@gwu.edu"))
        password.perform(typeText("abcd12345"))

        login.check(matches(isEnabled()))
        signUp.check(matches(isEnabled()))
    }

    @Test
    fun testMapsNavigation() {
        val username = onView(withHint("Username"))
        val password = onView(withHint("Password"))
        val login = onView(withText("Login"))
        val signUp = onView(withId(R.id.signUp))
        val progressBar = onView(withId(R.id.progressBar))

        username.perform(clearText())
        password.perform(clearText())

        login.check(matches(not(isEnabled())))
        signUp.check(matches(not(isEnabled())))

        username.perform(typeText("nick@gwu.edu"))
        password.perform(typeText("abcd12345"))

        login.check(matches(isEnabled()))
        signUp.check(matches(isEnabled()))

        login.perform(click())

        sleep(2000)

        onView(withText("Welcome, nick@gwu.edu")).check(matches(isDisplayed()))
    }
}