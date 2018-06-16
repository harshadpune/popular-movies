package com.udacity.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.udacity.popularmovies.databinding.ActivityPopularMoviesBinding;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PopularMoviesActivity extends AppCompatActivity {

    private ActivityPopularMoviesBinding mDataBinding;
    String movieUri = "https://api.themoviedb.org/3/movie/popular?api_key=883337ae4728664ccdca899d49af9416";
    private Uri builtUri;
    private ArrayList<MoviesData> moviesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        mDataBinding = DataBindingUtil.setContentView(this,R.layout.activity_popular_movies);

        builtUri = Uri.parse(AppConstants.MOVIE_APP_BASE_URL).buildUpon()
                        .appendPath(AppConstants.PARAM_POPULAR_MOVIE)
                        .appendQueryParameter(AppConstants.QUERY_API_KEY, AppConstants.API_KEY).build();

        Log.d("PopularMoviesActivity", "----buildUri "+builtUri.toString());
        URL queryUrl = null;
        try {
            queryUrl = new URL(builtUri.toString());
            new PopularMoviesAsyncTask().execute(queryUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public class PopularMoviesAsyncTask extends AsyncTask<URL, Void, String>{

        private GridArrayAdapter gridArrayAdapter;

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            Log.d("PopularMoviesActivity", "----url "+url);
            String response = null;
            try {
                response = NetworkUtils.getResponseFromUrl(url);
                moviesData = JsonUtils.parseNetworkResponse(response);
                gridArrayAdapter = new GridArrayAdapter(PopularMoviesActivity.this,moviesData);
            } catch (IOException e) {
                e.printStackTrace();
            }catch (JSONException e){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            setAdapterAndListener(gridArrayAdapter);
        }
    }

    private void setAdapterAndListener(GridArrayAdapter gridArrayAdapter) {
        mDataBinding.gvMoviesList.setAdapter(gridArrayAdapter);
        mDataBinding.gvMoviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent movieDescriptionIntent = new Intent(PopularMoviesActivity.this, MovieDescriptionActivity.class);
                movieDescriptionIntent.putExtra(AppConstants.MOVIE_POSITION, moviesData.get(position));
                startActivity(movieDescriptionIntent);
            }
        });
    }
}
