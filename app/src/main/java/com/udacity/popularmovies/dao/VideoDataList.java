package com.udacity.popularmovies.dao;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HARSHAD on 30/06/2018.
 */

public class VideoDataList {

    @SerializedName("id")
    public String videoId;
    @SerializedName("results")
    public List<VideoInformation> videoInformation = new ArrayList();

    public class VideoInformation{
        @SerializedName("id")
        public String id;
        @SerializedName("key")
        public String key;
        @SerializedName("name")
        public String name;
        @SerializedName("site")
        public String site;
        @SerializedName("size")
        public String size;
        @SerializedName("type")
        public String type;
    }
}
