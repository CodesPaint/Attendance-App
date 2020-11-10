package com.e.attendancetracker;

import android.app.Application;

public class CurrentStudent extends Application {
    private String name;
    private long scholar_number;
    private String department;
    private String email;
    private String password;
    private String contact;
    private String finger_print;
    private String image;
    private int folderid;
    private int batchid;
    public CurrentStudent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScholar_number() {
        return scholar_number;
    }

    public void setScholar_number(long scholar_number) {
        this.scholar_number = scholar_number;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFinger_print() {
        return finger_print;
    }

    public void setFinger_print(String finger_print) {
        this.finger_print = finger_print;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getFolderid() {
        return folderid;
    }

    public void setFolderid(int folderid) {
        this.folderid = folderid;
    }

    public int getBatchid() {
        return batchid;
    }

    public void setBatchid(int batchid) {
        this.batchid = batchid;
    }
}
