package com.udacity.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HARSHAD on 16/06/2018.
 */

public class JsonUtils {

    public static ArrayList<MoviesData> parseNetworkResponse(String response) throws JSONException {
        JSONArray moviesResult = new JSONObject(response).optJSONArray(AppConstants.JSON_RESULTS);
        ArrayList<MoviesData> moviesArray = new ArrayList();
        for(int i=0; i< moviesResult.length(); i++){
            MoviesData moviesData = new MoviesData();
            moviesData.setId(moviesResult.optJSONObject(i).optString(AppConstants.JSON_ID));
            moviesData.setTitle(moviesResult.optJSONObject(i).optString(AppConstants.JSON_TITLE));
            moviesData.setReleaseDate(moviesResult.optJSONObject(i).optString(AppConstants.JSON_RELEASE_DATE));
            moviesData.setMoviePosterLink(moviesResult.optJSONObject(i).optString(AppConstants.JSON_POSTER_PATH));
            moviesData.setVoteAverage(moviesResult.optJSONObject(i).optString(AppConstants.JSON_VOTE_AVERAGE));
            moviesData.setPlotSynopsis(moviesResult.optJSONObject(i).optString(AppConstants.JSON_OVERVIEW));
            moviesArray.add(moviesData);
        }
        return moviesArray;
    }
}
