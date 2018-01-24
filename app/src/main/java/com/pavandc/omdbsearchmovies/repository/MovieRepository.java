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

        void onDefaultError();

        void onMovieDetail(MovieDetail movieDetail);
    }


    /**
     * Search for a movie by its title. Will try to get the first result page.
     *
     * @param title the title to search for.
     * @return a search result as an instance of {@link SearchResults}.
     */
        public  abstract  void search(@NonNull String title, OnFinishedListener listener);

    /**
     * Get the specified page result of a search for the received title.
     *
     * @param title the title to search for.
     * @return a search result as an instance of {@link SearchResults}.
     */
        public abstract  void search(@NonNull String title, @NonNull int page, OnFinishedListener listener);


        public abstract  void getMovieDetails(@NonNull String imbId, OnFinishedListener listener);

}
