package com.paulobsa.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieListActivity extends Activity implements MovieListAdapter.MovieAdapterOnclickHandler {

    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private Context context;
    private static String LOG_TAG = "POPULAR_MOVIES";
    private static String MOVIES_LIST = "movies_list";
    private ArrayList<String> moviesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);
        context = this;

        mRecyclerView = findViewById(R.id.movie_recycler_view);
        mAdapter = new MovieListAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        GridLayoutManager manager = new GridLayoutManager(this, Util.getColumnSize(context));
        mRecyclerView.setLayoutManager(manager);

        mRequestQueue = Volley.newRequestQueue(this);

        if (savedInstanceState == null) {
            fetchTopRated();
        } else {
            moviesList = (ArrayList<String>) savedInstanceState.getSerializable(MOVIES_LIST);
            updateMoviesList();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (R.id.top_rated == id) {
            fetchTopRated();
        } else if (R.id.most_popular == id) {
            fetchMostPopular();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MOVIES_LIST, moviesList);
    }

    private void fetchMostPopular(){
        fetchFilms(Util.buildMostPopularMoviesURL().toString());
    }

    private void fetchTopRated(){
        fetchFilms(Util.buildTopRatedMoviesURL().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_itens, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void fetchFilms(String query){

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray itens = response.getJSONArray("results");

                            for(int i = 0; i < itens.length(); i++){
                                moviesList.add(itens.getJSONObject(i).toString());
                            }

                            //set film list
                            Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
                            updateMoviesList();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, R.string.no_connection, Toast.LENGTH_LONG).show();
                    }
                });

        mRequestQueue.add(jsObjRequest);
    }

    private void updateMoviesList() {
        mAdapter.setMoviesList(moviesList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCardClick(String movieJson) {
        Toast.makeText(context, "Card Click", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, MovieDetailActivity.class);
        i.putExtra(MovieDetailActivity.MOVIE_JSON, movieJson);
        startActivity(i);
    }
}
