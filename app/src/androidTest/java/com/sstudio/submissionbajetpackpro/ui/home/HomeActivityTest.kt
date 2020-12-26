package com.sstudio.submissionbajetpackpro.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.utils.DataDummy
import com.sstudio.submissionbajetpackpro.utils.EspressoIdlingResource
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test


class HomeActivityTest {
    private val dummyMovie = DataDummy.generateDummyMovies()
    private val dummyTv = DataDummy.generateDummyTvShow()

    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovie() {
        onView(withId(R.id.rv_list_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovie.size
            )
        )
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_list_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.txt_title)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_title)).check(matches(withText(dummyMovie[0].originalTitle)))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(withText(dummyMovie[0].overview)))
    }

    @Test
    fun loadTv() {
        onView(
            Matchers.allOf(
                withText(R.string.tab_tv),
                isDescendantOfA(withId(R.id.navigation_tv)),
                isDisplayed()
            )
        )
            .perform(click())
        onView(withId(R.id.rv_list_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list_tv_show)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTv.size
            )
        )
    }

    @Test
    fun loadDetailTv() {
        onView(withText("Acara Tv")).perform(click())
        onView(withId(R.id.rv_list_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.txt_title)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_title)).check(matches(withText(dummyTv[0].originalName)))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(withText(dummyTv[0].overview)))
    }

    @Test
    fun loadFavoriteMovie() {
        onView(withId(R.id.rv_list_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.btn_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(
            Matchers.allOf(
                withText(R.string.tab_favorite),
                isDescendantOfA(withId(R.id.navigation_favorite)),
                isDisplayed()
            )
        )
            .perform(click())
        onView(withId(R.id.rv_list_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    @Test
    fun loadFavoriteTv() {
        onView(
            Matchers.allOf(
                withText(R.string.tab_tv),
                isDescendantOfA(withId(R.id.navigation_tv)),
                isDisplayed()
            )
        )
            .perform(click())
        onView(withId(R.id.rv_list_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.btn_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(
            Matchers.allOf(
                withText(R.string.tab_favorite),
                isDescendantOfA(withId(R.id.navigation_favorite)),
                isDisplayed()
            )
        )
            .perform(click())
        onView(withText(R.string.tab_2)).perform(click())
        onView(withId(R.id.rv_list_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list_tv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }
}