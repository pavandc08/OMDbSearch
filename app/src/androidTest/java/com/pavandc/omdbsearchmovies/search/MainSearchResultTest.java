package com.pavandc.omdbsearchmovies.search;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.pavandc.omdbsearchmovies.R;
import com.pavandc.omdbsearchmovies.util.ViewVisibilityIdlingResource;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by pavandc on 2018-01-27.
 */
@RunWith(AndroidJUnit4.class)
public class MainSearchResultTest {

    @Test
    public void showSearchResultsTest() {
        assertViewDisplayed(R.id.menu_search);
        Espresso.onView(withId(R.id.menu_search)).perform(click());
    }

    public void assertViewDisplayed(int id) {
        IdlingResource idlingResource = new ViewVisibilityIdlingResource(id);
        IdlingRegistry.getInstance().register(idlingResource);
        Espresso.onView(ViewMatchers.withId(id)).check(matches(isDisplayed()));
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
