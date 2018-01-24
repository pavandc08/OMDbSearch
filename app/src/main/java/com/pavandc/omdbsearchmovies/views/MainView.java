package com.pavandc.omdbsearchmovies.views;

import com.pavandc.omdbsearchmovies.model.SearchItem;

import java.util.List;

/**
 * Created by pavandc on 2018-01-24.
 */

public interface MainView {

    void showProgress();
    void hideProgress();
    void showMovieResults(List<SearchItem> searchResults);
    void showEmptyResult();
    void showError(int message);

    boolean isConnected();
}
