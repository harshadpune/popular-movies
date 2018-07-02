package com.udacity.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.dao.VideoDataList;
import java.util.List;

/**
 * Created by HARSHAD on 30/06/2018.
 */

class VideosThumbnailAdapter extends RecyclerView.Adapter<VideosThumbnailAdapter.ViewHolder> {

    private Context context;
    private List<VideoDataList.VideoInformation> videoInformations;

    public VideosThumbnailAdapter(Context context, List<VideoDataList.VideoInformation> videoInformations){
        this.context = context;
        this.videoInformations = videoInformations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_thumbnail_row_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String url = null;
        try {
            url = "https://img.youtube.com/vi/"+videoInformations.get(position).key+"/0.jpg";
            Picasso.with(context).load(url).into(holder.ivVideoThumbnail, new Callback() {
                @Override
                public void onSuccess() {
                    holder.pbThumbnailLoading.setVisibility(View.GONE);
                    holder.ivPlayIcon.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {
                    holder.pbThumbnailLoading.setVisibility(View.GONE);
                    holder.ivPlayIcon.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.tvTrailerName.setText(videoInformations.get(position).name);
        holder.tvType.setText(videoInformations.get(position).type);
        holder.tvSite.setText(videoInformations.get(position).site);

    }


    @Override
    public int getItemCount() {
        return videoInformations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvTrailerName;
        private final TextView tvType;
        private final TextView tvSite;
        private final ImageView ivVideoThumbnail;
        private final ProgressBar pbThumbnailLoading;
        private final ImageView ivPlayIcon;
        private final ConstraintLayout clRowContainer;

        public ViewHolder(View view) {
            super(view);
            ivVideoThumbnail = (ImageView) view.findViewById(R.id.ivVideoThumbnail);
            tvTrailerName = (TextView) view.findViewById(R.id.tvTrailerName);
            tvType = (TextView) view.findViewById(R.id.tvType);
            tvSite = (TextView) view.findViewById(R.id.tvSite);
            pbThumbnailLoading = (ProgressBar) view.findViewById(R.id.pbThumbnailLoading);
            ivPlayIcon = (ImageView) view.findViewById(R.id.ivPlayIcon);
            clRowContainer = (ConstraintLayout) view.findViewById(R.id.clRowContainer);
            clRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.clRowContainer){
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                 Uri.parse(AppConstants.YOUTUBE_BASE_URL+""+videoInformations.get(getAdapterPosition()).key));
                 context.startActivity(webIntent);
            }

        }
    }
}
