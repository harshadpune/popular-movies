package com.udacity.popularmovies;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;

import com.squareup.picasso.Picasso;
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
        initComponents();
    }

    private void initComponents() {
        Bundle data = getIntent().getExtras();
        MoviesData moviesData = (MoviesData) data.getSerializable(AppConstants.MOVIE_POSITION);
        activityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        Picasso.with(this).load(AppConstants.POSTER_BASE_URL+""+moviesData.getMoviePosterLink()).into(activityMovieDetailsBinding.ivMoviePhoto);
        activityMovieDetailsBinding.tvTitle.setText(moviesData.getTitle());
        activityMovieDetailsBinding.tvOverview.setText(moviesData.getPlotSynopsis());
        activityMovieDetailsBinding.tvReleaseDate.setText(moviesData.getReleaseDate());
        activityMovieDetailsBinding.tvRating.setText(""+moviesData.getVoteAverage()+"/10");
        activityMovieDetailsBinding.ratingBar.setRating(Float.parseFloat(""+moviesData.getVoteAverage()));

        Palette.from(((BitmapDrawable)activityMovieDetailsBinding.ivMoviePhoto.getDrawable()).getBitmap()).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(palette.getVibrantColor(getResources().getColor(R.color.black))));
                activityMovieDetailsBinding.ivPallet.setBackgroundColor(palette.getVibrantColor(getResources().getColor(R.color.black)));
            }
        });
    }
}
