package com.paulobsa.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class MovieDetailActivity extends Activity {

    public final static String MOVIE_JSON = "movie_json";
    MovieJSONParser jsonParser;
    ImageView movieBackdrop;
    ImageView moviePoster;
    TextView movieTitle;
    TextView movieOverview;
    TextView movieReleaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieBackdrop = findViewById(R.id.movie_backdrop);
        movieTitle = findViewById(R.id.movie_title);
        movieOverview = findViewById(R.id.movie_overview);
        movieReleaseDate = findViewById(R.id.movie_release_date);

        if (getIntent().hasExtra(MOVIE_JSON)) {
            try {
                jsonParser = new MovieJSONParser(getIntent().getStringExtra(MOVIE_JSON));
                getActionBar().setTitle(jsonParser.getTitle());
                getActionBar().setDisplayHomeAsUpEnabled(true);
                Picasso.get().load(jsonParser.getBackDropPath()).into(movieBackdrop);
                movieTitle.setText(jsonParser.getTitle());
                movieReleaseDate.setText(jsonParser.getReleaseDate());
                movieOverview.setText(jsonParser.getOverview());


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
