package com.paulobsa.popularmovies;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pauloaraujo on 23/06/18.
 */

public class MovieJSONParser {

    private JSONObject mJson;

    private static String IMAGE_ROOT = "http://image.tmdb.org/t/p/";
    private static String SMALL_SIZE = "w185/";
    private static String BIG_SIZE = "w780/";

    public MovieJSONParser(String json) throws JSONException {
        mJson = new JSONObject(json);
    }

    public String getPosterPath() throws JSONException {
        return IMAGE_ROOT + SMALL_SIZE + mJson.getString("poster_path");
    }

    public String getBackDropPath() throws JSONException {
        return IMAGE_ROOT + BIG_SIZE + mJson.getString("backdrop_path");
    }

    public String getTitle() throws JSONException {
        return mJson.getString("title");
    }

    public String getOriginalTitle() throws JSONException {
        return mJson.getString("original_title");
    }

    public String getReleaseDate() throws JSONException {
        return mJson.getString("release_date");
    }

    public String getOverview() throws JSONException {
        return mJson.getString("overview");
    }

    public String getVoteAverage() throws JSONException {
        return mJson.getString("vote_average");
    }
}
