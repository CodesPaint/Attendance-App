package com.e.attendancetracker;

import java.util.ArrayList;

public class Attendance {
    private String date;
    private long meetingid;
    ArrayList<String> scholarlist=new ArrayList<>();

    public Attendance() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(long meetingid) {
        this.meetingid = meetingid;
    }

    public ArrayList<String> getScholarlist() {
        return scholarlist;
    }

    public void setScholarlist(ArrayList<String> scholarlist) {
        this.scholarlist = scholarlist;
    }
}
