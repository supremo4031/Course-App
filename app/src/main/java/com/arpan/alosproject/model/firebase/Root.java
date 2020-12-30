package com.arpan.alosproject.model.firebase;

import java.util.List;

public class Root{
    public List<AllCourse> allCourses;

    public Root(List<AllCourse> allCourses) {
        this.allCourses = allCourses;
    }

    public Root() {
    }

    public List<AllCourse> getAllCourses() {
        return allCourses;
    }

    public void setAllCourses(List<AllCourse> allCourses) {
        this.allCourses = allCourses;
    }
}
