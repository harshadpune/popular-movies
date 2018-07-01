package com.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.popularmovies.dao.ReviewDataList;
import com.udacity.popularmovies.dao.VideoDataList;

import java.util.List;

/**
 * Created by HARSHAD on 30/06/2018.
 */

class ReviewsDataAdapter extends RecyclerView.Adapter<ReviewsDataAdapter.ViewHolder>{


    private final Context context;
    private final List<ReviewDataList.ReviewsInformation> reviewsInformations;

    public ReviewsDataAdapter(Context context, List<ReviewDataList.ReviewsInformation> reviewsInformations){
        this.context = context;
        this.reviewsInformations = reviewsInformations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reviews_row_item, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("ReviewAdapter","------------Review "+position);
        Log.d("ReviewAdapter","------------author "+reviewsInformations.get(position).author);
        Log.d("ReviewAdapter","------------content "+reviewsInformations.get(position).content);
        Log.d("ReviewAdapter","------------url "+reviewsInformations.get(position).url);
        holder.tvReview.setText(reviewsInformations.get(position).content);
        holder.tvAuthor.setText("-"+reviewsInformations.get(position).author);
        holder.tvShowFullReview.setText(Html.fromHtml(context.getResources().getString(R.string.show_full_review)));

       /* holder.tvReview.setText("dsafadf");
        holder.tvAuthor.setText("dadsdfs");
        holder.tvShowFullReview.setText(Html.fromHtml(context.getResources().getString(R.string.show_full_review)));*/
    }

    @Override
    public int getItemCount() {
        return reviewsInformations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvReview;
        private final TextView tvAuthor;
        private final TextView tvShowFullReview;

        public ViewHolder(View view) {
            super(view);
            tvReview = (TextView) view.findViewById(R.id.tvReview);
            tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
            tvShowFullReview = (TextView) view.findViewById(R.id.tvShowFullReview);
            tvShowFullReview.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.tvShowFullReview){
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(""+reviewsInformations.get(getAdapterPosition()).url));
                context.startActivity(webIntent);
            }
        }
    }
}
