package com.pavandc.omdbsearchmovies.interfaces;

/**
 * Created by pavandc on 2018-01-25.
 */

public interface State {

        String getLastSearchTerm();

        int getTotal();

        int getTotalFetched();

        int getResultsPerPage();
}
