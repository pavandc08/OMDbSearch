package com.pavandc.omdbsearchmovies.presenters;

import com.pavandc.omdbsearchmovies.interfaces.DetailsView;
import com.pavandc.omdbsearchmovies.model.MovieDetail;
import com.pavandc.omdbsearchmovies.repository.MovieRepository;

/**
 * Created by pavandc on 2018-01-27.
 */

public class DetailsPresenter implements MovieRepository.onShowDetails {

    private MovieRepository movieRepository;
    private DetailsView detailsView;

    public DetailsPresenter(MovieRepository movieRepository, DetailsView view) {
         this.movieRepository = movieRepository;
         this.detailsView = view;
    }

    public void getMovieDetails(String imdbId) {
        detailsView.showLoading();
        movieRepository.getMovieDetails(imdbId, this);
    }

    @Override
    public void onMovieDetail(MovieDetail movieDetail) {
        detailsView.hideLoading();
        detailsView.showMovieDetails(movieDetail);
    }

    @Override
    public void onError(String message) {
        detailsView.hideLoading();
        detailsView.showError(message);
    }
}
