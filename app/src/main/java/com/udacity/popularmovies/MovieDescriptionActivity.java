package com.udacity.popularmovies;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.dao.MoviesData;
import com.udacity.popularmovies.dao.ReviewDataList;
import com.udacity.popularmovies.databinding.ActivityMovieDetailsBinding;
import com.udacity.popularmovies.utils.RetrofitAPIClient;
import com.udacity.popularmovies.utils.RetrofitAPIInterface;
import com.udacity.popularmovies.dao.VideoDataList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by HARSHAD on 16/06/2018.
 */

public class MovieDescriptionActivity extends AppCompatActivity{

    private ActivityMovieDetailsBinding activityMovieDetailsBinding;
    private final String SOURCE_DATE_FORMAT = "yyyy-mm-dd";
    private final String TARGET_DATE_FORMAT = "dd MMM yyyy";
    private MoviesData moviesData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initComponents();
        loadVideosAndReviews();
    }



    private void initComponents() {
        Bundle data = getIntent().getExtras();
        moviesData = null;
        if (data != null && data.containsKey((AppConstants.MOVIE_POSITION))) {
            moviesData = (MoviesData) data.getSerializable(AppConstants.MOVIE_POSITION);
        }
        if (moviesData != null) {
            activityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
            activityMovieDetailsBinding.pbDetailsLoading.setVisibility(View.VISIBLE);
            Picasso.with(this).load(AppConstants.POSTER_BASE_URL + "" + moviesData.getMoviePosterLink()).into(activityMovieDetailsBinding.ivMoviePhoto, new Callback() {
                @Override
                public void onSuccess() {
                    Palette.from(((BitmapDrawable) activityMovieDetailsBinding.ivMoviePhoto.getDrawable()).getBitmap()).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(palette.getVibrantColor(getResources().getColor(R.color.black))));
                            activityMovieDetailsBinding.ivPallet.setBackgroundColor(palette.getVibrantColor(getResources().getColor(R.color.black)));
                        }
                    });
                }

                @Override
                public void onError() {
                    // do nothing. Shows default background
                }
            });
            activityMovieDetailsBinding.tvTitle.setText(moviesData.getTitle());
            activityMovieDetailsBinding.tvOverview.setText(moviesData.getPlotSynopsis());
            activityMovieDetailsBinding.tvReleaseDate.setText(formatDate(moviesData.getReleaseDate()));
            activityMovieDetailsBinding.tvRating.setText("" + moviesData.getVoteAverage() + "/10");
            activityMovieDetailsBinding.ratingBar.setRating(Float.parseFloat("" + moviesData.getVoteAverage()));

        }else{
            Toast.makeText(this, ""+getString(R.string.movie_error), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadVideosAndReviews() {
        RetrofitAPIInterface retrofitAPIInterface = RetrofitAPIClient.getClient().create(RetrofitAPIInterface.class);
        //Get video list
        final Call<VideoDataList> videoDataList = retrofitAPIInterface.getVideoList(moviesData.getId(),AppConstants.API_KEY);
        videoDataList.enqueue(new retrofit2.Callback<VideoDataList>() {
            @Override
            public void onResponse(Call<VideoDataList> call, Response<VideoDataList> response) {
                VideoDataList videoDataList =   response.body();
                List<VideoDataList.VideoInformation> videoInformations = videoDataList.videoInformation;
                VideosThumbnailAdapter videosThumbnailAdapter = new VideosThumbnailAdapter(MovieDescriptionActivity.this, videoInformations);
                activityMovieDetailsBinding.rvVideosList.setAdapter(videosThumbnailAdapter );
                if(videoDataList.videoInformation.size() ==0)
                    activityMovieDetailsBinding.tvVideosLabel.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<VideoDataList> call, Throwable t) {
                activityMovieDetailsBinding.tvVideosLabel.setVisibility(View.GONE);
                activityMovieDetailsBinding.rvVideosList.setVisibility(View.GONE);
                videoDataList.cancel();
            }
        });

        //GetVideoList
        final Call<ReviewDataList> reviewDataList = retrofitAPIInterface.getReviewList(moviesData.getId(),AppConstants.API_KEY);
        reviewDataList.enqueue(new retrofit2.Callback<ReviewDataList>() {
            @Override
            public void onResponse(Call<ReviewDataList> call, Response<ReviewDataList> response) {
                ReviewDataList reviewDataList = response.body();
                List<ReviewDataList.ReviewsInformation> reviewsInformations = reviewDataList.reviewsInformation;
                ReviewsDataAdapter reviewsDataAdapter = new ReviewsDataAdapter(MovieDescriptionActivity.this, reviewsInformations);
                activityMovieDetailsBinding.rvReviewsList.setAdapter(reviewsDataAdapter);
                activityMovieDetailsBinding.pbDetailsLoading.setVisibility(View.GONE);
                if(reviewDataList.reviewsInformation.size() ==0)
                    activityMovieDetailsBinding.tvReviewLabel.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ReviewDataList> call, Throwable t) {
                activityMovieDetailsBinding.pbDetailsLoading.setVisibility(View.GONE);
                activityMovieDetailsBinding.tvReviewLabel.setVisibility(View.GONE);
                reviewDataList.cancel();
            }
        });
    }
    private String formatDate(String releaseDate){
        SimpleDateFormat inputDate = new SimpleDateFormat(SOURCE_DATE_FORMAT);
        Date sourceDate = null;
        try {
            sourceDate = inputDate.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat changedDate = new SimpleDateFormat(TARGET_DATE_FORMAT);
        return changedDate.format(sourceDate);
    }
}
