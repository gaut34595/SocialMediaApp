package com.example.socialmedia;

public class VideoLink {
    private String videoId;

    public VideoLink() {
        // Required empty constructor for Firebase Realtime Database
    }

    public VideoLink(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}

