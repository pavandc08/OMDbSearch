package com.pavandc.omdbsearchmovies.repository;

import android.support.annotation.NonNull;

import com.pavandc.omdbsearchmovies.model.MovieDetail;
import com.pavandc.omdbsearchmovies.model.SearchResults;

/**
 * Created by pavandc on 2018-01-24.
 */

public abstract class MovieRepository {

    public interface OnFinishedListener {
        void onSearchFinished(SearchResults items);

        void onLoadMorePage(SearchResults results, int page);

        void onDefaultError();

    }

    public interface onShowDetails {
        void onMovieDetail(MovieDetail movieDetail);

        void onError(String message);

    }


    /**
     * Search for a movie by its title. Will try to get the first result page.
     *
     * @param title    the title to search for.
     * @param listener listener to know when the network call is done.
     */
    public abstract void search(@NonNull String title, OnFinishedListener listener);

    /**
     * Get the specified page result of a search for the received title.
     *
     * @param title    the title to search for.
     * @param page     the page number  of search results
     * @param listener listener to know when the network call is done.
     */
    public abstract void search(@NonNull String title, @NonNull int page, OnFinishedListener listener);

    /**
     * Get the Details page of the movie clicked
     *
     * @param imdbId   the imdbId of the movie.
     * @param listener listener to know when the network call is done.
     */
    public abstract void getMovieDetails(@NonNull String imdbId, onShowDetails listener);

}
