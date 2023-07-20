package com.example.socialmedia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private ArrayList<String> videoLinks;

    public VideoAdapter(ArrayList<String> videoLinks) {
        this.videoLinks = videoLinks;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        String videoId = videoLinks.get(position);
        holder.bind(videoId);
    }

    @Override
    public int getItemCount() {
        return videoLinks.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private YouTubePlayerView youTubePlayerView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
        }

        public void bind(String videoId) {
            // Initialize and load the YouTube video for the given videoId
            youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> youTubePlayer.loadVideo(videoId, 0));
        }
    }
}

