package com.pavandc.omdbsearchmovies.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pavandc on 2018-01-26.
 */

public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

    public interface  ImageInterface {
        void imageDownloaded(Bitmap bitmap);
    }

    private  ImageInterface mListener;

    public  void addListener(ImageInterface listener) {
        mListener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String imageUrl = strings[0];
        Bitmap bitmap = null;
        try {
            HttpURLConnection urlConnection;
            URL url = new URL(imageUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream input = urlConnection.getInputStream();

            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        mListener.imageDownloaded(result);
    }
}
