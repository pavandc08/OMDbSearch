package com.pavandc.omdbsearchmovies.services;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.pavandc.omdbsearchmovies.model.SearchResults;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pavandc on 2018-01-28.
 */

public class SearchMoviesTask extends AsyncTask<String, Void, SearchResults> {

    public static final String BASE_URL = "http://omdbapi.com/?apikey=54bf05a7&";

    public interface OnFinishedListener {
        void onSearchFinished(SearchResults items);

        void onLoadMorePage(SearchResults results, int page);

        void onDefaultError(String error);
    }

    private SearchResults searchResults;
    private OnFinishedListener mDispatcher;
    String pageNumber = "1";

    public void addListener(OnFinishedListener listener) {
        mDispatcher = listener;
    }

    @Override
    protected SearchResults doInBackground(String... strings) {
        String movieText = strings[0];
        pageNumber = strings[1];
        URL url;
        HttpURLConnection conn= null;
        BufferedReader reader = null;
        try {
            url = new URL(BASE_URL + "s=" + movieText+"&page="+pageNumber);

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
            searchResults = gson.fromJson(response, SearchResults.class);

        } catch (Exception e) {
            mDispatcher.onDefaultError(e.getMessage());
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
        return searchResults;
    }

    @Override
    protected void onPostExecute(SearchResults searchResults) {
        super.onPostExecute(searchResults);
        if (searchResults != null) {
            if(pageNumber.equalsIgnoreCase("1")) {
                mDispatcher.onSearchFinished(searchResults);
            } else {
                mDispatcher.onLoadMorePage(searchResults, Integer.parseInt(pageNumber));
            }
        }
    }
}
