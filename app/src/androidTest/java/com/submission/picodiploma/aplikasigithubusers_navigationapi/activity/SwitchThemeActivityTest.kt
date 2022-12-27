package com.submission.picodiploma.aplikasigithubusers_navigationapi.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.submission.picodiploma.aplikasigithubusers_navigationapi.R


@RunWith(AndroidJUnit4ClassRunner::class)
class SwitchThemeActivityTest{

    @Before
    fun setup(){
        ActivityScenario.launch(SwitchThemeActivity::class.java)
    }
    @Test
    fun getTheme() {
        onView(withId(R.id.switch_theme)).check(matches(isDisplayed()))
        onView(withId(R.id.switch_theme)).perform(click())

    }

}