package com.udacity.popularmovies.utils;

import com.udacity.popularmovies.dao.ReviewDataList;
import com.udacity.popularmovies.dao.VideoDataList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HARSHAD on 30/06/2018.
 */

public interface RetrofitAPIInterface {

    @GET("movie/{id}/videos")
    Call<VideoDataList> getVideoList(@Path("id") String movieId, @Query("api_key") String api_key);

    @GET("movie/{id}/reviews")
    Call<ReviewDataList> getReviewList(@Path("id") String movieId, @Query("api_key") String api_key);
}
