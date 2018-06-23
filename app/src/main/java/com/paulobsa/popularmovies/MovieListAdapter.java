package com.paulobsa.popularmovies;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by pauloaraujo on 23/06/18.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieAdapterHolder> {

    private ArrayList<String> moviesList;
    private MovieAdapterOnclickHandler mHandler;
    private Context mContext;

    public MovieListAdapter(MovieAdapterOnclickHandler handler, Context context) {
        this.mHandler = handler;
        this.mContext = context;
        this.moviesList = new ArrayList<>();
    }

    @Override
    public MovieAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_card, parent, false);

        return new MovieListAdapter.MovieAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.MovieAdapterHolder holder, int pos) {
        final int position = pos;
        try {

            MovieJSONParser moviesJsonParser = new MovieJSONParser(moviesList.get(position));

            holder.mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandler.onCardClick(moviesList.get(position));
                }
            });

            Picasso.get().load(moviesJsonParser.getPosterPath()).into(holder.mImageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    static class MovieAdapterHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        CardView mCard;
        public MovieAdapterHolder(View itemView) {
            super(itemView);
            this.mCard = itemView.findViewById(R.id.movie_card);
            this.mImageView = itemView.findViewById(R.id.img_movie_poster);
        }
    }

    public interface MovieAdapterOnclickHandler{
        void onCardClick(String jsonFilme);
    }

    public void setMoviesList(ArrayList<String> moviesList) {
        this.moviesList = moviesList;
    }
}
