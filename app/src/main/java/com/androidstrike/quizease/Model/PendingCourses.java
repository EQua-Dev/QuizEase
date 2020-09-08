package com.androidstrike.quizease.Model;

public class PendingCourses {

    private String CourseTitle;
    private String CourseCode;

    public PendingCourses() {
    }

    public PendingCourses(String courseTitle, String courseCode) {
        CourseTitle = courseTitle;
        CourseCode = courseCode;
    }

    public String getCourseTitle() {
        return CourseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        CourseTitle = courseTitle;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }
}
