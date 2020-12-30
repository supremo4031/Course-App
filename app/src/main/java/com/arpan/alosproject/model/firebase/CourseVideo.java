package com.arpan.alosproject.model.firebase;

import java.io.Serializable;

public class CourseVideo implements Serializable {

    public String videoTitle;
    public String videoUrl;

    public CourseVideo() {
    }

    public CourseVideo(String videoTitle, String videoUrl) {
        this.videoTitle = videoTitle;
        this.videoUrl = videoUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
