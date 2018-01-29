package com.pavandc.omdbsearchmovies.repository;

import android.support.annotation.NonNull;

import com.pavandc.omdbsearchmovies.services.MovieDetailsTask;
import com.pavandc.omdbsearchmovies.services.SearchMoviesTask;

/**
 * Created by pavandc on 2018-01-24.
 */

public class MovieRepositoryImpl {
    public MovieRepositoryImpl() {
    }

    /**
     * Search for a movie by its title. Will try to get the first result page.
     *
     * @param title    the title to search for.
     * @param listener listener to know when the network call is done.
     */
    public void search(@NonNull String title, final SearchMoviesTask.OnFinishedListener listener) {
        SearchMoviesTask searchMoviesTask = new SearchMoviesTask();
        searchMoviesTask.addListener(listener);
        searchMoviesTask.execute(title, String.valueOf(1));
    }

    /**
     * Get the specified page result of a search for the received title.
     *
     * @param title    the title to search for.
     * @param page     the page number  of search results
     * @param listener listener to know when the network call is done.
     */
    public void search(@NonNull String title, @NonNull int page, final SearchMoviesTask.OnFinishedListener listener) {
        SearchMoviesTask searchMoviesTask = new SearchMoviesTask();
        searchMoviesTask.addListener(listener);
        searchMoviesTask.execute(title, String.valueOf(page));
    }

    /**
     * Get the Details page of the movie clicked
     *
     * @param imdbId   the imdbId of the movie.
     * @param listener listener to know when the network call is done.
     */
    public void getMovieDetails(@NonNull final String imdbId, final MovieDetailsTask.onShowDetails listener) {
        MovieDetailsTask movieDetailsTask = new MovieDetailsTask();
        movieDetailsTask.addListener(listener);
        movieDetailsTask.execute(imdbId);
    }
}
