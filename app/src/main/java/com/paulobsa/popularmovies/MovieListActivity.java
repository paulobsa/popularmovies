package com.paulobsa.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class FilmListActivity extends Activity  {

    private RequestQueue mRequestQueue;
    private String LOG_TAG = "POPULAR_MOVIES";
    private ArrayList<String> listaFilmes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(this);
        fetchTopRated();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (R.id.action_refresh == id) {
            fetchTopRated();
        }
        return super.onOptionsItemSelected(item);
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

        listaFilmes.clear();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray itens = response.getJSONArray("results");

                            for(int i = 0; i < itens.length(); i++){
                                listaFilmes.add(itens.getJSONObject(i).toString());
                            }

                            //set film list
                            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
                    }
                });

        mRequestQueue.add(jsObjRequest);
    }
}
