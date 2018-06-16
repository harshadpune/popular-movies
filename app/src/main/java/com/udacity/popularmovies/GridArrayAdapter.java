package com.udacity.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HARSHAD on 16/06/2018.
 */

class GridArrayAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflator;
    private Context context;
    ArrayList<MoviesData> moviesData;


    public GridArrayAdapter(PopularMoviesActivity context, ArrayList<MoviesData> moviesData) {
        this.context = context;
        this.moviesData = moviesData;
        mLayoutInflator = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return moviesData.size();
    }

    @Override
    public MoviesData getItem(int position) {
        return moviesData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflator.inflate(R.layout.grid_item,null);
        ImageView ivGridItem = (ImageView) convertView.findViewById(R.id.ivGridItem);
        String uriPath = AppConstants.POSTER_BASE_URL +""+moviesData.get(position).getMoviePosterLink();
        Picasso.with(context).load(uriPath).into(ivGridItem);
        return convertView;
    }

}
