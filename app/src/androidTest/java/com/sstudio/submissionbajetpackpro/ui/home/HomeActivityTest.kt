package com.sstudio.submissionbajetpackpro.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.utils.DataDummy
import org.junit.Before
import org.junit.Test


class HomeActivityTest{
    private val dummyMovie = DataDummy.generateDummyMovies()
    private val dummyTv = DataDummy.generateDummyTv()

    @Before
    fun setup(){
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun loadMovie(){
        onView(withId(R.id.rv_list_movie)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rv_list_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
    }

    @Test
    fun loadDetailMovie(){
        onView(withId(R.id.rv_list_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            ViewActions.click()
        ))
        onView(withId(R.id.txt_title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.txt_title)).check(ViewAssertions.matches(withText(dummyMovie[0].title)))
        onView(withId(R.id.txt_overview)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.txt_overview)).check(ViewAssertions.matches(withText(dummyMovie[0].overview)))
    }

    @Test
    fun loadTv(){
        onView(withText("Acara Tv")).perform(ViewActions.click())
        onView(withId(R.id.rv_list_tv_show)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rv_list_tv_show)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTv.size))
    }

    @Test
    fun loadDetailTv(){
        onView(withText("Acara Tv")).perform(ViewActions.click())
        onView(withId(R.id.rv_list_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            ViewActions.click()
        ))
        onView(withId(R.id.txt_title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.txt_title)).check(ViewAssertions.matches(withText(dummyTv[0].title)))
        onView(withId(R.id.txt_overview)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.txt_overview)).check(ViewAssertions.matches(withText(dummyTv[0].overview)))
    }
}