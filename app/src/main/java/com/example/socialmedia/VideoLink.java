package com.example.socialmedia;

public class VideoLink {
    private String videoId;
    private String userName; // Add a field for the username

    private String timeStamp;

    public VideoLink() {
        // Required empty constructor for Firestore
    }

    public VideoLink(String videoId, String userName, String timeStamp) {
        this.videoId = videoId;
        this.userName = userName;
        this.timeStamp = timeStamp;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}


