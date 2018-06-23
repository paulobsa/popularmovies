package com.paulobsa.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pauloaraujo on 21/06/18.
 */

public class Util {
    private static String TMDB_API = BuildConfig.API_KEY;

    private static String TMDB_POPULAR_BASE_URL = "http://api.themoviedb.org/3";
    private static String POPULAR_QUERY = "movie/popular";
    private static String TOP_RATED_QUERY = "movie/top_rated";
    private static String API_KEY_PARAM = "api_key";

    public static URL buildTopRatedMoviesURL() {
        return buildMoviesUrl(TOP_RATED_QUERY);
    }

    public static URL buildMostPopularMoviesURL() {
        return buildMoviesUrl(POPULAR_QUERY);
    }

    public static URL buildMoviesUrl(String searchPath) {
        Uri builtUri = Uri.parse(TMDB_POPULAR_BASE_URL).buildUpon()
                .appendEncodedPath(searchPath)
                .appendQueryParameter(API_KEY_PARAM, TMDB_API)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static int getColumnSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 160);
    }
}
