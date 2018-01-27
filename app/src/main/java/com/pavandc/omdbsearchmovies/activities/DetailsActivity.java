package com.pavandc.omdbsearchmovies.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.pavandc.omdbsearchmovies.R;
import com.pavandc.omdbsearchmovies.fragments.DetailsFragment;

/**
 * Created by pavandc on 2018-01-27.
 */

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_IMDB_ID = "key_imdb_id";


    @Override
    protected void onCreate(@Nullable Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_movie_details);

        if (savedInstancestate == null) {
            String imdbID = getIntent().getStringExtra(EXTRA_IMDB_ID);
            Fragment fragment = DetailsFragment.newInstance(imdbID);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

}
