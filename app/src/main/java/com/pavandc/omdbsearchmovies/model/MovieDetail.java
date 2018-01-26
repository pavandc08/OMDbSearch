package com.pavandc.omdbsearchmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pavandc on 2018-01-24.
 */

public class MovieDetail {

    @SerializedName("Title")
    private  String title;
    @SerializedName("Year")
    private  String year;
    @SerializedName("Rated")
    private  String rated;
    @SerializedName("Released")
    private  String releaseDate;
    @SerializedName("Runtime")
    private  String runtime;
    @SerializedName("Genre")
    private  String genre;
    @SerializedName("Director")
    private  String director;
    @SerializedName("Writer")
    private  String writer;
    @SerializedName("Actors")
    private  String actors;
    @SerializedName("Plot")
    private  String plot;
    @SerializedName("Language")
    private  String language;
    @SerializedName("Country")
    private  String country;
    @SerializedName("Awards")
    private  String awards;
    @SerializedName("Poster")
    private String poster;
    @SerializedName("Metascore")
    private  String metascore;
    @SerializedName("imdbRating")
    private  float imdbRating;
    @SerializedName("imdbVotes")
    private  String imdbVotes;
    @SerializedName("imdbID")
    private  String imdbId;
    @SerializedName("Type")
    private  String type;


    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getAwards() {
        return awards;
    }

    public String getPoster() {
        return poster;
    }

    public String getMetascore() {
        return metascore;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getType() {
        return type;
    }
}
