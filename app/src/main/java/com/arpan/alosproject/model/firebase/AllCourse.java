package com.arpan.alosproject.model.firebase;

import java.io.Serializable;
import java.util.List;

public class AllCourse implements Serializable {
    public String courseDescription;
    public String courseLanguage;
    public String courseName;
    public List<CourseVideo> courseVideo;

    public AllCourse() {
    }

    public AllCourse(String courseDescription, String courseLanguage, String courseName, List<CourseVideo> courseVideo) {
        this.courseDescription = courseDescription;
        this.courseLanguage = courseLanguage;
        this.courseName = courseName;
        this.courseVideo = courseVideo;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseLanguage() {
        return courseLanguage;
    }

    public void setCourseLanguage(String courseLanguage) {
        this.courseLanguage = courseLanguage;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<CourseVideo> getCourseVideo() {
        return courseVideo;
    }

    public void setCourseVideo(List<CourseVideo> courseVideo) {
        this.courseVideo = courseVideo;
    }
}
