package com.androidstrike.quizease.Model;

public class Lecturers {


    private String lecturerCourse;
    private String lecturerEmail;
    private String lecturerName;
    private String lecturerPhone;

    public Lecturers() {
    }

    public Lecturers(String lecturerCourse, String lecturerEmail, String lecturerName, String lecturerPhone) {
        this.lecturerCourse = lecturerCourse;
        this.lecturerEmail = lecturerEmail;
        this.lecturerName = lecturerName;
        this.lecturerPhone = lecturerPhone;
    }

    public String getLecturerCourse() {
        return lecturerCourse;
    }

    public void setLecturerCourse(String lecturerCourse) {
        this.lecturerCourse = lecturerCourse;
    }

    public String getLecturerEmail() {
        return lecturerEmail;
    }

    public void setLecturerEmail(String lecturerEmail) {
        this.lecturerEmail = lecturerEmail;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getLecturerPhone() {
        return lecturerPhone;
    }

    public void setLecturerPhone(String lecturerPhone) {
        this.lecturerPhone = lecturerPhone;
    }
}
