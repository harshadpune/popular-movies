package com.udacity.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private Uri builtUri;
    private ArrayList<MoviesData> moviesData;
    private final int INDEX_POPULAR_MOVIE = 0;
    private final int INDEX_TOP_RATED_MOVIE = 1;
    private int selectedMenuItem = -1;
    MenuItem menuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        mDataBinding = DataBindingUtil.setContentView(this,R.layout.activity_popular_movies);
        showPopularAndTopRatedMovies(INDEX_POPULAR_MOVIE);
    }


    public class PopularMoviesAsyncTask extends AsyncTask<URL, Void, String>{

        private GridArrayAdapter gridArrayAdapter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDataBinding.pbLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
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
            mDataBinding.pbLoading.setVisibility(View.GONE);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        if(selectedMenuItem == -1)
            return  true;

        switch (selectedMenuItem){
            case R.id.filter_popular:
                menuItem = (MenuItem) menu.findItem(R.id.filter_popular);
                menuItem.setChecked(true);
                break;

            case R.id.filter_top_rated:
                menuItem = (MenuItem) menu.findItem(R.id.filter_top_rated);
                menuItem.setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.filter_popular){
            item.setChecked(true);
            selectedMenuItem = item.getItemId();
            showPopularAndTopRatedMovies(INDEX_POPULAR_MOVIE);
        }else if(item.getItemId() == R.id.filter_top_rated){
            item.setChecked(true);
            selectedMenuItem = item.getItemId();
            showPopularAndTopRatedMovies(INDEX_TOP_RATED_MOVIE);
        }
        return true;
    }




    private void showPopularAndTopRatedMovies(int option){
        if(option == INDEX_POPULAR_MOVIE) {
            builtUri = Uri.parse(AppConstants.MOVIE_APP_BASE_URL).buildUpon()
                    .appendPath(AppConstants.PARAM_POPULAR_MOVIE)
                    .appendQueryParameter(AppConstants.QUERY_API_KEY, AppConstants.API_KEY).build();
        }else if(option == INDEX_TOP_RATED_MOVIE){
            builtUri = Uri.parse(AppConstants.MOVIE_APP_BASE_URL).buildUpon()
                    .appendPath(AppConstants.PARAM_TOP_RATED_MOVIE)
                    .appendQueryParameter(AppConstants.QUERY_API_KEY, AppConstants.API_KEY).build();
        }

        URL queryUrl = null;
        try {
            queryUrl = new URL(builtUri.toString());
            new PopularMoviesAsyncTask().execute(queryUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
