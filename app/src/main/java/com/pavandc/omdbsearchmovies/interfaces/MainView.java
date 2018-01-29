package com.pavandc.omdbsearchmovies.interfaces;

import android.support.annotation.NonNull;

import com.pavandc.omdbsearchmovies.model.SearchItem;

import java.util.List;

/**
 * Created by pavandc on 2018-01-24.
 */

public interface MainView {

    void showLoading();

    void hideLoading();

    void showSearchResults(@NonNull List<SearchItem> searchResults);

    void showMoreResults(@NonNull List<SearchItem> moreResults);

    void clearSearchResults();

    void showEmptyResult();

    void showError(int message);

    void showMovieDetailsUi(@NonNull String imdbId);

}
