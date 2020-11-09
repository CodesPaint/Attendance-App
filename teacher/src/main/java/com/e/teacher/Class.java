package com.e.teacher;

public class Class {
    private int classid;
    private String classname;
    private long teacherregistrationno;
    private int batchid;

    public Class() {
    }

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public long getTeacherregistrationno() {
        return teacherregistrationno;
    }

    public void setTeacherregistrationno(long teacherregistrationno) {
        this.teacherregistrationno = teacherregistrationno;
    }

    public int getBatchid() {
        return batchid;
    }

    public void setBatchid(int batchid) {
        this.batchid = batchid;
    }
}
