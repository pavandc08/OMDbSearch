package com.pavandc.omdbsearchmovies.search;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.pavandc.omdbsearchmovies.R;
import com.pavandc.omdbsearchmovies.util.ViewVisibilityIdlingResource;

import org.junit.Before;
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

    @Before
    public void setup(){
//        String  searchRepsonse = ResourceHelper.getResource("espresso/search-response.json");
//        Header jsonHeader = new  Header("Content-Type", "application/json; charset=utf-8");
//        mockServerClient = this.mockServerClient;
//
//        this.mockServerClient.when(
//                HttpRequest.request()
//                        .withMethod("GET")
//                        .withPath("/")
//        )
//                .respond(
//                HttpResponse.response()
//                        .withStatusCode(200)
//                        .withHeader(jsonHeader)
//                        .withBody(searchRepsonse)
//        );

    }

    @Test
    public void showSearchResultsTest() {
        assertViewDisplayed(R.id.menu_search);
        Espresso.onView(withId(R.id.menu_search)).perform(click());

    }

    public void assertViewDisplayed(int id) {
        //isDisplayed() is better for ScrollViews.
        //The full height/width of the view is greater than the height/width of the visible rectangle
        IdlingResource idlingResource = new ViewVisibilityIdlingResource(id);
        IdlingRegistry.getInstance().register(idlingResource);
        Espresso.onView(ViewMatchers.withId(id)).check(matches(isDisplayed()));
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

}
