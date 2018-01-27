package com.pavandc.omdbsearchmovies.interfaces;

import android.support.annotation.NonNull;

import com.pavandc.omdbsearchmovies.model.MovieDetail;
import com.pavandc.omdbsearchmovies.model.SearchItem;

/**
 * Created by pavandc on 2018-01-25.
 */

public interface UserActionsListener {

    void searchMovie(@NonNull String title);

    void getMoreResults();

    void openMovieDetails(@NonNull SearchItem movie);

    State getState();
}
