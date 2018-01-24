package com.pavandc.omdbsearchmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pavandc.omdbsearchmovies.R;
import com.pavandc.omdbsearchmovies.model.SearchItem;
import com.pavandc.omdbsearchmovies.model.SearchResults;
import com.pavandc.omdbsearchmovies.views.MainView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected  void onResume() {
        super.onResume();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMovieResults(List<SearchItem> searchResults) {

    }

    @Override
    public void showError(int message) {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public  void showEmptyResult() {

    }
}
