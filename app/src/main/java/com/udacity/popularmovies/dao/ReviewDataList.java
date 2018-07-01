package com.udacity.popularmovies.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HARSHAD on 30/06/2018.
 */

public class ReviewDataList {

    @SerializedName("id")
    public String videoId;
    @SerializedName("results")
    public List<ReviewDataList.ReviewsInformation> reviewsInformation = new ArrayList();

    public class ReviewsInformation {
        @SerializedName("author")
        public String author;
        @SerializedName("content")
        public String content;
        @SerializedName("url")
        public String url;
    }

}
