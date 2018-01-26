package com.pavandc.omdbsearchmovies.services;

import com.pavandc.omdbsearchmovies.model.MovieDetail;
import com.pavandc.omdbsearchmovies.model.SearchResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pavandc on 2018-01-23.
 */

public interface OmdbService {

    @GET("/")
    Call<SearchResults> searchMovie(@Query("apikey")String apikey, @Query("s") String movieName, @Query("page") int page);

    @GET("/")
    Call<MovieDetail> getMovieDetails(@Query("apikey")String apikey, @Query("i") String imdbId);
}
