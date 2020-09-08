package com.androidstrike.quizease.Model;

public class User {
    private String Email, Password, RegNo;

    public User() {
    }

    public User(String email, String password, String regNo) {
        Email = email;
        Password = password;
        RegNo = regNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }
}

