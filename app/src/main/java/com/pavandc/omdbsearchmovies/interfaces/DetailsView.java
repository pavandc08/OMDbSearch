package com.pavandc.omdbsearchmovies.interfaces;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pavandc.omdbsearchmovies.model.MovieDetail;

/**
 * Created by pavandc on 2018-01-27.
 */

public interface DetailsView {

    void showMovieDetails(@NonNull MovieDetail movieDetail);

    void showLoading();

    void hideLoading();

    void showFullScreenMoviePoster(@NonNull String poster);

    void showError(@Nullable String message);

}
