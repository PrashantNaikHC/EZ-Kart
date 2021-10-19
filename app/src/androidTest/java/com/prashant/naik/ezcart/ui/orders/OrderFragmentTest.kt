package com.prashant.naik.ezcart.ui.orders

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class OrderFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @ExperimentalCoroutinesApi
    @Test
    fun testLoginFragmentUIisDisplayed() {
        launchFragmentInHiltContainer<OrderFragment> {  }

        Espresso.onView(ViewMatchers.withId(R.id.orders_header))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.orders_recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}