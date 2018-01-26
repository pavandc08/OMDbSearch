package com.pavandc.omdbsearchmovies.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.pavandc.omdbsearchmovies.R;
import com.pavandc.omdbsearchmovies.model.MovieDetail;
import com.pavandc.omdbsearchmovies.model.SearchResults;
import com.pavandc.omdbsearchmovies.services.OmdbApi;
import com.pavandc.omdbsearchmovies.services.OmdbService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pavandc on 2018-01-24.
 */

public class MovieRepositoryImpl extends  MovieRepository {

    private OmdbService omdbService = OmdbApi.getOmdbService();
    private Context context;
    private String apiKey;

    public MovieRepositoryImpl(Context context) {
        this.context = context;
        apiKey = context.getString(R.string.omdb_api_key);
    }

    @Override
    public void search(@NonNull String title, final OnFinishedListener listener) {
        Call<SearchResults> call = omdbService.searchMovie(apiKey, title, 1);
        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                listener.onSearchFinished(response.body());
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                listener.onDefaultError();
            }
        });

    }

    @Override
    public void search(@NonNull String title, @NonNull int page, final OnFinishedListener listener) {

        Call<SearchResults> call = omdbService.searchMovie(apiKey, title, page);

        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
              listener.onLoadMorePage(response.body(), page);
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                listener.onDefaultError();
            }
        });
    }

    @Override
    public void getMovieDetails(@NonNull final String imdbId, final OnFinishedListener listener) {
       Call<MovieDetail> callback =  omdbService.getMovieDetails(apiKey, imdbId);

       callback.enqueue(new Callback<MovieDetail>() {
           @Override
           public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                //listener.onMovieDetail(response.body());
           }

           @Override
           public void onFailure(Call<MovieDetail> call, Throwable t) {
                listener.onDefaultError();
           }
       });
    }
}
