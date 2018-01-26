package com.pavandc.omdbsearchmovies.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pavandc.omdbsearchmovies.R;
import com.pavandc.omdbsearchmovies.model.SearchItem;
import com.pavandc.omdbsearchmovies.services.LoadImageTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavandc on 2018-01-25.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    public interface MovieItemListener {
        void onMovieClicked(SearchItem movieClicked);
    }

    private Context mContext;
    private MovieItemListener mListener;
    private List<SearchItem> mMovies;


    public MovieListAdapter(Context context, MovieItemListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        SearchItem movie = mMovies.get(position);
        holder.title.setText(movie.getTitle());
        holder.year.setText(String.format(mContext.getResources().getString(R.string.released_year), movie.getYear()));
        holder.director.setText(movie.getType());
        if (!movie.getPoster().equalsIgnoreCase("N/A")) {
            holder.loadImage(movie.getPoster());
        }
        holder.itemView.setOnClickListener(view -> mListener.onMovieClicked(mMovies.get(position)));
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public List<SearchItem> getMovies() {
        return mMovies;
    }

    public void setMovies(List<SearchItem> movies) {
        mMovies = new ArrayList<>(movies);
        notifyDataSetChanged();
    }

    public void addMovies(List<SearchItem> movies) {
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }


    protected class MovieViewHolder extends RecyclerView.ViewHolder implements LoadImageTask.ImageInterface {
        public TextView title;
        TextView year;
        TextView director;
        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            year = itemView.findViewById(R.id.movie_year);
            director = itemView.findViewById(R.id.movie_director);
            poster = itemView.findViewById(R.id.movie_poster);
        }

        public void loadImage(String imageUrl) {
            LoadImageTask loadImageTask = new LoadImageTask();
            loadImageTask.addListener(this);
            loadImageTask.execute(imageUrl);

        }

        @Override
        public void imageDownloaded(Bitmap bitmap) {
            poster.setImageBitmap(bitmap);
        }
    }
}
