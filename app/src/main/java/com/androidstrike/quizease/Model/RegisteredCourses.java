package com.androidstrike.quizease.Model;

public class RegisteredCourses {

    private String CourseId;
    private String CourseTitle;
    private String CourseCode;
    private String CourseLecturer;
    private String CourseCredit;

    public RegisteredCourses() {
    }

    public RegisteredCourses(String courseId, String courseTitle, String courseCode, String courseLecturer, String courseCredit) {
        CourseId = courseId;
        CourseTitle = courseTitle;
        CourseCode = courseCode;
        CourseLecturer = courseLecturer;
        CourseCredit = courseCredit;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
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
