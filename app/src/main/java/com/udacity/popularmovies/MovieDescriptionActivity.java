package com.udacity.popularmovies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.udacity.popularmovies.databinding.ActivityMovieDetailsBinding;

/**
 * Created by HARSHAD on 16/06/2018.
 */

public class MovieDescriptionActivity extends AppCompatActivity{

    private ActivityMovieDetailsBinding activityMovieDetailsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle data = getIntent().getExtras();
        MoviesData moviesData = (MoviesData) data.getSerializable(AppConstants.MOVIE_POSITION);
        activityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        activityMovieDetailsBinding.tvTitle.setText(moviesData.getTitle());
        activityMovieDetailsBinding.tvOverview.setText(moviesData.getPlotSynopsis());
        activityMovieDetailsBinding.tvReleaseDate.setText(moviesData.getReleaseDate());
    }
}
