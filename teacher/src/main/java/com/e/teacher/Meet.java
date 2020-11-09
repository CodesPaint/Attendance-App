package com.e.teacher;

public class Meet {
    private long meetid;
    private int classid;
    private String date;
    private String starttime;
    private String duartion;
    private String meetotp;

    public Meet() {
    }

    public long getMeetid() {
        return meetid;
    }

    public void setMeetid(long meetid) {
        this.meetid = meetid;
    }

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getDuartion() {
        return duartion;
    }

    public void setDuartion(String duartion) {
        this.duartion = duartion;
    }

    public String getMeetotp() {
        return meetotp;
    }

    public void setMeetotp(String meetotp) {
        this.meetotp = meetotp;
    }
}
