package com.pavandc.omdbsearchmovies.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pavandc.omdbsearchmovies.R;
import com.pavandc.omdbsearchmovies.services.LoadImageTask;

/**
 * Created by pavandc on 2018-01-27.
 */

public class FullScreenPosterFragment extends Fragment implements LoadImageTask.ImageInterface {
    public static final String POSTER_URL = "poster_url";

    private ImageView mPosterImage;

    public static FullScreenPosterFragment newInstance(String poster) {
        Bundle bundle = new Bundle();
        bundle.putString(POSTER_URL, poster);
        FullScreenPosterFragment fragment = new FullScreenPosterFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_full_poster, container, false);
        String url = getArguments().getString(POSTER_URL);
        mPosterImage = view.findViewById(R.id.img_movie_poster);
        mPosterImage.setClickable(true);
        LoadImageTask ld = new LoadImageTask();
        ld.addListener(this);
        ld.execute(url);

        return view;
    }

    @Override
    public void imageDownloaded(Bitmap bitmap) {
        mPosterImage.setImageBitmap(bitmap);
    }
}
