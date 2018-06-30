package com.udacity.popularmovies.utils;

import com.udacity.popularmovies.AppConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HARSHAD on 30/06/2018.
 */

public class RetrofitAPIClient {

    static Retrofit retrofit = null;

    public static Retrofit getClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.MOVIE_VIDEO_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
