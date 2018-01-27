package com.pavandc.omdbsearchmovies.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pavandc.omdbsearchmovies.R;
import com.pavandc.omdbsearchmovies.activities.MainActivity;
import com.pavandc.omdbsearchmovies.interfaces.DetailsView;
import com.pavandc.omdbsearchmovies.model.MovieDetail;
import com.pavandc.omdbsearchmovies.presenters.DetailsPresenter;
import com.pavandc.omdbsearchmovies.repository.MovieRepositoryImpl;
import com.pavandc.omdbsearchmovies.services.LoadImageTask;

/**
 * Created by pavandc on 2018-01-27.
 */

public class DetailsFragment extends Fragment implements DetailsView, LoadImageTask.ImageInterface {

    public final static String KEY_IMDB_ID = "imdb_id";
    DetailsPresenter presenter;

    private ActionBar mActionBar;
    private ProgressBar mProgressBar;
    private View mDetails;

    private ImageView mImgPoster;
    private TextView mTitle;

    private TextView mReleased;
    private TextView mRuntime;
    private TextView mGenre;
    private TextView mCountry;
    private TextView mLanguage;
    private TextView mRated;
    private RatingBar mRatingBar;

    private TextView mDirector;
    private TextView mActors;
    private TextView mAwards;
    private TextView mPlot;

    public DetailsFragment() {
    }

    public static DetailsFragment newInstance(@NonNull String imdbId) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMDB_ID, imdbId);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter = new DetailsPresenter(new MovieRepositoryImpl(getContext()), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("");

        mProgressBar = rootView.findViewById(R.id.progress_bar);
        mDetails = rootView.findViewById(R.id.details);

        mImgPoster = rootView.findViewById(R.id.img_poster);
        mTitle = rootView.findViewById(R.id.movie_title);
        mRatingBar = rootView.findViewById(R.id.rating_bar);

        mReleased = rootView.findViewById(R.id.released);
        mRuntime = rootView.findViewById(R.id.runtime);
        mGenre = rootView.findViewById(R.id.genre);
        mCountry = rootView.findViewById(R.id.country);
        mLanguage = rootView.findViewById(R.id.language);
        mRated = rootView.findViewById(R.id.rated);

        mDirector = rootView.findViewById(R.id.director);
        mActors = rootView.findViewById(R.id.actors);
        mAwards = rootView.findViewById(R.id.awards);
        mPlot = rootView.findViewById(R.id.plot);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        String imdbId = getArguments().getString(KEY_IMDB_ID);
        assert imdbId != null;
        presenter.getMovieDetails(imdbId);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMovieDetails(@NonNull MovieDetail movieDetail) {
        mDetails.setVisibility(View.VISIBLE);
        mActionBar.setTitle(movieDetail.getTitle());
        LoadImageTask loadImageTask = new LoadImageTask();
        loadImageTask.addListener(this);
        loadImageTask.execute(movieDetail.getPoster());

        if (movieDetail.getPoster().equalsIgnoreCase("N/A")) {
            mImgPoster.setClickable(false);
        } else {
            mImgPoster.setOnClickListener(v -> showFullScreenMoviePoster(movieDetail.getPoster()));
        }

        mTitle.setText(movieDetail.getTitle());

        mRatingBar.setRating(movieDetail.getImdbRating() / 10 * mRatingBar.getNumStars());
        mRatingBar.setContentDescription(getString(R.string.details_rating_description,
                String.valueOf(movieDetail.getImdbRating())));

        mReleased.setText(movieDetail.getReleaseDate());
        mRuntime.setText(movieDetail.getRuntime());
        mGenre.setText(movieDetail.getGenre());
        mCountry.setText(movieDetail.getCountry());
        mLanguage.setText(movieDetail.getLanguage());
        mRated.setText(movieDetail.getRated());

        mDirector.setText(movieDetail.getDirector());
        mActors.setText(movieDetail.getActors());
        mAwards.setText(movieDetail.getAwards());
        mPlot.setText(movieDetail.getPlot());
    }

    @Override
    public void showLoading() {
        mDetails.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showFullScreenMoviePoster(@NonNull String poster) {
        Fragment fullScreenFragment = FullScreenPosterFragment.newInstance(poster);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fullScreenFragment)
                .addToBackStack("fullScreen-poster")
                .commit();
    }

    @Override
    public void showError(@Nullable String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void imageDownloaded(Bitmap bitmap) {
        mImgPoster.setImageBitmap(bitmap);
    }
}
