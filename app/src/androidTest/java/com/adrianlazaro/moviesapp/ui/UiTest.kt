package com.adrianlazaro.moviesapp.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.adrianlazaro.moviesapp.R
import com.adrianlazaro.moviesapp.data.network.TheMovieDb
import com.adrianlazaro.moviesapp.utils.fromJson
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityTestRule = ActivityTestRule(MoviesActivity::class.java, false, false)

    @get:Rule
    val grantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson(
                "popularmovies.json"
            )
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", TheMovieDb(mockWebServerRule.server.url("/").toString()).okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun clickAMovieNavigatesToDetail() {
        activityTestRule.launchActivity(null)

        onView(withId(R.id.rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                4,
                ViewActions.click()
            )
        )

        onView(withId(R.id.toolbar_detail))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText("Spider-Man: Far from Home"))))

    }

}