package com.example.socialmedia;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeUtils {
    public static String extractVideoIdFromLink(String videoLink) {
        String videoId = null;
        if (videoLink != null && !videoLink.isEmpty()) {
            // Regular expression pattern to match the video ID in the YouTube link
            Pattern pattern = Pattern.compile("(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*");
            Matcher matcher = pattern.matcher(videoLink);

            if (matcher.find()) {
                videoId = matcher.group();
            }
        }
        return videoId;
    }
}
