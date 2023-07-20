package com.example.socialmedia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class VideoLinkAdapter extends RecyclerView.Adapter<VideoLinkAdapter.VideoLinkViewHolder> {

    private ArrayList<VideoLink> videoLinks;

    public VideoLinkAdapter(ArrayList<VideoLink> videoLinks) {
        this.videoLinks = videoLinks;
    }

    @NonNull
    @Override
    public VideoLinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoLinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoLinkViewHolder holder, int position) {
        VideoLink videoLink = videoLinks.get(position);
        holder.bind(videoLink);
    }

    @Override
    public int getItemCount() {
        return videoLinks.size();
    }

    public static class VideoLinkViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private TextView tvTimestamp;
        private YouTubePlayerView youtubePlayerView;

        public VideoLinkViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            youtubePlayerView = itemView.findViewById(R.id.youtube_player_view);
        }

        public void bind(VideoLink videoLink) {
            tvUsername.setText("Uploaded by: " + videoLink.getUserName());
            tvTimestamp.setText(videoLink.getTimeStamp());
            initializeYouTubePlayer(videoLink.getVideoId());
        }

        private void initializeYouTubePlayer(String videoId) {
            youtubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
                @Override
                public void onYouTubePlayer(YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(videoId, 0);
                }
            });
        }

    }
}

