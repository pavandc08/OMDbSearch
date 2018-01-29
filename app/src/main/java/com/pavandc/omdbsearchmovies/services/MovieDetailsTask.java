package com.pavandc.omdbsearchmovies.services;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.pavandc.omdbsearchmovies.model.MovieDetail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.pavandc.omdbsearchmovies.services.SearchMoviesTask.BASE_URL;

/**
 * Created by pavandc on 2018-01-28.
 */

public class MovieDetailsTask  extends AsyncTask<String, Void, MovieDetail> {

    public interface onShowDetails {
        void onMovieDetail(MovieDetail movieDetail);

        void onError(String message);
    }

    private MovieDetail movieDetail;
    private onShowDetails mDispatcher;

    public void addListener(onShowDetails listener) {
        mDispatcher = listener;
    }

    @Override
    protected MovieDetail doInBackground(String... strings) {
        String imdbId = strings[0];
        URL url;
        HttpURLConnection conn= null;
        BufferedReader reader = null;
        try {
            url = new URL(BASE_URL + "i=" + imdbId);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            if (conn.getResponseCode() != 200) {
                return null;
            }

            InputStream inputStream = conn.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            String response = buffer.toString();
            Gson gson = new Gson();
            movieDetail = gson.fromJson(response, MovieDetail.class);

        } catch (Exception e) {
            mDispatcher.onError(e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return movieDetail;
    }

    @Override
    protected void onPostExecute(MovieDetail movieDetail) {
        super.onPostExecute(movieDetail);
        if (movieDetail != null) {
            mDispatcher.onMovieDetail(movieDetail);
        }
    }
}
