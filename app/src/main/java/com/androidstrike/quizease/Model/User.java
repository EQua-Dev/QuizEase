package com.androidstrike.quizease.Model;

public class User {
    private String Email, Password, RegNo; //Phone;

    public User() {
    }

    public User(String email, String password, String regNo) //String phone)
     {
        Email = email;
        Password = password;
        RegNo = regNo;
//        Phone = phone;
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

//    public String getPhone() {
//        return Phone;
//    }
//
//    public void setPhone(String phone) {
//        Phone = phone;
//    }
}

