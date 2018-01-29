package com.pavandc.omdbsearchmovies.presenters;

import android.support.annotation.NonNull;

import com.pavandc.omdbsearchmovies.R;
import com.pavandc.omdbsearchmovies.interfaces.MainView;
import com.pavandc.omdbsearchmovies.interfaces.State;
import com.pavandc.omdbsearchmovies.interfaces.UserActionsListener;
import com.pavandc.omdbsearchmovies.model.SearchItem;
import com.pavandc.omdbsearchmovies.model.SearchResults;
import com.pavandc.omdbsearchmovies.repository.MovieRepositoryImpl;
import com.pavandc.omdbsearchmovies.services.SearchMoviesTask;

/**
 * Created by pavandc on 2018-01-24.
 */

public class MainPresenter implements SearchMoviesTask.OnFinishedListener, UserActionsListener {

    private MainView mainView;
    private MovieRepositoryImpl movieRepository;

    private String mLastSearch;
    private int mFetched;
    private int mTotal;
    private int mResultsPerPage;

    public MainPresenter(MovieRepositoryImpl repository, MainView mainView, State state) {
        this.movieRepository = repository;
        this.mainView = mainView;
        if (state != null) {
            mLastSearch = state.getLastSearchTerm();
            mFetched = state.getTotalFetched();
            mTotal = state.getTotal();
            mResultsPerPage = state.getResultsPerPage();
        }
    }

    @Override
    public void searchMovie(@NonNull String title) {
        mainView.showLoading();
        mainView.clearSearchResults();

        if (title.trim().isEmpty()) {
            return;
        }
        clearState();
        mLastSearch = title.trim();
        movieRepository.search(mLastSearch, this);

    }

    @Override
    public void onSearchFinished(SearchResults searchResults) {
        if (mainView != null) {
            mainView.hideLoading();
            if (searchResults.getMovies() != null && !searchResults.getMovies().isEmpty()
                    && searchResults.getTotalResults() > 0) {
                mainView.showSearchResults(searchResults.getMovies());
                mTotal = searchResults.getTotalResults();
                mFetched = searchResults.getMovies().size();
                mResultsPerPage = searchResults.getMovies().size();
            } else {
                mainView.showEmptyResult();
                mTotal = 0;
                mFetched = 0;
            }
        }
    }

    @Override
    public void onLoadMorePage(SearchResults searchResults, int pageNumber) {
        if (mainView != null && !searchResults.getMovies().isEmpty()) {
            mainView.showMoreResults(searchResults.getMovies());
            mFetched += searchResults.getMovies().size();
        }
    }

    @Override
    public void getMoreResults() {
        if (mFetched == mTotal) {
            return;
        }
        int nextPage = (mFetched / mResultsPerPage) + 1;
        int totalPages = (mTotal / mResultsPerPage) + ((mTotal % mResultsPerPage) == 0 ? 0 : 1);
        if (nextPage > totalPages) {
            return;
        }
        movieRepository.search(mLastSearch, nextPage, this);
    }


    @Override
    public State getState() {
        return new State() {
            @Override
            public String getLastSearchTerm() {
                return mLastSearch;
            }

            @Override
            public int getTotal() {
                return mTotal;
            }

            @Override
            public int getTotalFetched() {
                return mFetched;
            }

            @Override
            public int getResultsPerPage() {
                return mResultsPerPage;
            }
        };
    }

    @Override
    public void onDefaultError(String error) {
        handleError(R.string.error);
    }


    @Override
    public void openMovieDetails(@NonNull SearchItem movie) {
        mainView.showMovieDetailsUi(movie.getImdbID());
    }

    private void handleError(int id) {
        if (mainView != null) {
            mainView.hideLoading();
            mainView.showError(id);
        }
    }

    public void onDestroy() {
        mainView = null;
    }

    public MainView getMainView() {
        return mainView;
    }

    private void clearState() {
        mLastSearch = null;
        mTotal = 0;
        mFetched = 0;
        mResultsPerPage = 0;
    }
}
