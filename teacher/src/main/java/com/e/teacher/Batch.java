package com.e.teacher;

public class Batch {
    private int batchid;
    private String batchname;

    public Batch() {
    }

    public Batch(int batchid, String batchname) {
        this.batchid = batchid;
        this.batchname = batchname;
    }

    public int getBatchid() {
        return batchid;
    }

    public void setBatchid(int batchid) {
        this.batchid = batchid;
    }

    public String getBatchname() {
        return batchname;
    }

    public void setBatchname(String batchname) {
        this.batchname = batchname;
    }
}
