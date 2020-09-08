package com.androidstrike.quizease.Model;

public class RegisteredCourses {

    private String CourseCode;
    private String CourseTitle;
    private String CourseLecturer;
    private String CourseCredit;

    public RegisteredCourses() {
    }

    public RegisteredCourses(String courseCode, String courseTitle, String courseLecturer, String courseCredit) {
        CourseCode = courseCode;
        CourseTitle = courseTitle;
        CourseLecturer = courseLecturer;
        CourseCredit = courseCredit;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public String getCourseTitle() {
        return CourseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        CourseTitle = courseTitle;
    }

    public String getCourseLecturer() {
        return CourseLecturer;
    }

    public void setCourseLecturer(String courseLecturer) {
        CourseLecturer = courseLecturer;
    }

    public String getCourseCredit() {
        return CourseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        CourseCredit = courseCredit;
    }
}
