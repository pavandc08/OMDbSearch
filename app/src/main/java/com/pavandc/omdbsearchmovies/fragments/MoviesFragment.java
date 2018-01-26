package com.pavandc.omdbsearchmovies.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pavandc.omdbsearchmovies.R;
import com.pavandc.omdbsearchmovies.adapters.MovieListAdapter;
import com.pavandc.omdbsearchmovies.model.SearchItem;
import com.pavandc.omdbsearchmovies.presenters.MainPresenter;
import com.pavandc.omdbsearchmovies.repository.MovieRepositoryImpl;
import com.pavandc.omdbsearchmovies.util.EndlessRecyclerViewScrollListener;
import com.pavandc.omdbsearchmovies.interfaces.MainView;
import com.pavandc.omdbsearchmovies.interfaces.State;
import com.pavandc.omdbsearchmovies.interfaces.UserActionsListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by pavandc on 2018-01-25.
 */

public class MoviesFragment extends Fragment implements MainView {


    private static final String KEY_MOVIES = "movies";
    private static final String KEY_SEARCH_QUERY = "search_query";
    private static final String KEY_LAST_SEARCHED = "last_searched_state";
    private static final String KEY_TOTAL_ITEMS = "total_items_state";
    private static final String KEY_FETCHED_ITEMS = "fetched_items_state";
    private static final String KEY_PER_PAGE = "per_page_state";


    private ProgressBar mProgressBar;
    private ImageView mImgEmptyScreen;
    private TextView mTextViewEmptyScreen;

    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private UserActionsListener mUserActionListener;
    private String mQueryString;
    private SearchView mSearchView;


    public MoviesFragment() {}

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle bundle) {

        View rootView = inflater.inflate(R.layout.fragment_movie_search, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        mAdapter = new MovieListAdapter(getContext(), movie -> mUserActionListener.openMovieDetails(movie));

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(getScrollListener(layoutManager));

        mProgressBar = rootView.findViewById(R.id.progress_bar);
        mImgEmptyScreen = rootView.findViewById(R.id.img_empty_screen);
        mTextViewEmptyScreen = rootView.findViewById(R.id.tv_empty_screen);
        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();
        mUserActionListener.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserActionListener = null;
        mRecyclerView.removeOnScrollListener(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        // restores the search result state
        if (savedInstanceState != null && savedInstanceState.getParcelableArray(KEY_MOVIES) != null) {
            Parcelable[] movies = savedInstanceState.getParcelableArray(KEY_MOVIES);
            assert movies != null;
            List<SearchItem> moviesList = Arrays.asList((SearchItem[]) movies);
            //TODO mAdapter.setMovies(movies);
            hideEmptyStateElements();
        }

        // recovers the query string
        if (savedInstanceState != null) {
            mQueryString = savedInstanceState.getString(KEY_SEARCH_QUERY);
        }

        // creates the presenter with the saved state
        mUserActionListener = new MainPresenter(new MovieRepositoryImpl(this.getContext()), this,
                readStateFromBundle(savedInstanceState));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the state of the search result
        if (mAdapter.getItemCount() != 0) {
            outState.putParcelableArray(KEY_MOVIES, mAdapter.getMovies().toArray(new SearchItem[]{}));
        }
        //save state of search query
        outState.putString(KEY_SEARCH_QUERY, mSearchView.getQuery().toString());

        //save state
        writeStateToBundle(outState, mUserActionListener.getState());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);

        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mUserActionListener.searchMovie(query);
                hideKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // recover the searchView state
        if (mQueryString != null && !mQueryString.isEmpty()) {
            menuItem.expandActionView();
            mSearchView.setQuery(mQueryString, false);
            mSearchView.clearFocus();
        }
    }


    private void hideEmptyStateElements() {
        mImgEmptyScreen.setVisibility(View.GONE);
        mTextViewEmptyScreen.setVisibility(View.GONE);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    private EndlessRecyclerViewScrollListener getScrollListener(LinearLayoutManager layoutManager) {
        return new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                mUserActionListener.getMoreResults();
            }
        };
    }

    @Override
    public void showProgress() {
        hideEmptyStateElements();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSearchResults(List<SearchItem> searchResults) {
        hideEmptyStateElements();
        mAdapter.setMovies(searchResults);
    }

    @Override
    public void showMoreResults(@NonNull List<SearchItem> moreResults) {
        mAdapter.addMovies(moreResults);
    }

    @Override
    public void clearSearchResults() {
        mAdapter.setMovies(Collections.emptyList());
    }

    @Override
    public void showEmptyResult() {
        mImgEmptyScreen.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_search_black_24dp, null));
        mTextViewEmptyScreen.setText(R.string.no_result);
        mImgEmptyScreen.setVisibility(View.VISIBLE);
        mTextViewEmptyScreen.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(int message) {
        Toast.makeText(getActivity(), "Something Went Wrong. Check your network Connection", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMovieDetailsUi(@NonNull String imdbId) {

    }

    private void writeStateToBundle(Bundle bundle, State state) {
        bundle.putString(KEY_LAST_SEARCHED, state.getLastSearchTerm());
        bundle.putInt(KEY_TOTAL_ITEMS, state.getTotal());
        bundle.putInt(KEY_FETCHED_ITEMS, state.getTotalFetched());
        bundle.putInt(KEY_PER_PAGE, state.getResultsPerPage());
    }

    private State readStateFromBundle(final Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        return new State() {
            @Override
            public String getLastSearchTerm() {
                return bundle.getString(KEY_LAST_SEARCHED);
            }

            @Override
            public int getTotal() {
                return bundle.getInt(KEY_TOTAL_ITEMS);
            }

            @Override
            public int getTotalFetched() {
                return bundle.getInt(KEY_FETCHED_ITEMS);
            }

            @Override
            public int getResultsPerPage() {
                return bundle.getInt(KEY_PER_PAGE);
            }
        };
    }
}
