package com.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.dao.MoviesData;

import java.util.ArrayList;

/**
 * Created by HARSHAD on 25/06/2018.
 */

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MoviesData> mMoviesData;

    public MoviesRecyclerAdapter(Context context, ArrayList<MoviesData> moviesData){
        this.mContext = context;
        this.mMoviesData = moviesData;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String uriPath = AppConstants.POSTER_BASE_URL +""+mMoviesData.get(position).getMoviePosterLink();
        Picasso.with(mContext).load(uriPath).into(holder.ivGridItem);
    }

    @Override
    public int getItemCount() {
        return mMoviesData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivGridItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ivGridItem = (ImageView) itemView.findViewById(R.id.ivGridItem);
            ivGridItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent movieDescriptionIntent = new Intent(mContext, MovieDescriptionActivity.class);
            movieDescriptionIntent.putExtra(AppConstants.MOVIE_POSITION, mMoviesData.get(getAdapterPosition()));
            mContext.startActivity(movieDescriptionIntent);
        }
    }
}
