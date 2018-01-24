package com.pavandc.omdbsearchmovies.presenters;

import com.pavandc.omdbsearchmovies.model.MovieDetail;
import com.pavandc.omdbsearchmovies.model.SearchResults;
import com.pavandc.omdbsearchmovies.repository.MovieRepository;
import com.pavandc.omdbsearchmovies.repository.MovieRepositoryImpl;
import com.pavandc.omdbsearchmovies.views.MainView;

/**
 * Created by pavandc on 2018-01-24.
 */

public class MainPresenter implements MovieRepository.OnFinishedListener {

    private MainView mainView;
    private MovieRepository movieRepository = new MovieRepositoryImpl();

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public  void onResume() {
        if(mainView != null) {
            mainView.showProgress();
            if(!mainView.isConnected()) {
               // handleError()
                return;
            }

        }
    }


    @Override
    public void onSearchFinished(SearchResults searchResults) {
        if(mainView != null) {
            if(searchResults.getTotalResults() > 0) {
                mainView.showMovieResults(searchResults.getMovies());
            } else {
                mainView.showEmptyResult();
            }

        }
    }

    @Override
    public void onDefaultError() {
            handleError( 1222);
    }


    @Override
    public void onMovieDetail(MovieDetail movieDetail) {


    }

    private  void handleError(int id) {
        if (mainView != null) {
            mainView.showError(id);
            mainView.hideProgress();
        }
    }

    public void onDestroy() {
        mainView = null;
    }

    public MainView getMainView() {
        return mainView;
    }
}
