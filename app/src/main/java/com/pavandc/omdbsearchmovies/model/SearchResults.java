package com.pavandc.omdbsearchmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pavandc on 2018-01-24.
 */

public class SearchResults {

    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("Search")
    private List<SearchItem> movies;

    public int getTotalResults() {
        return totalResults >= 0 ? totalResults : 0;
    }

    public List<SearchItem> getMovies() {
        return  movies;
    }
}
