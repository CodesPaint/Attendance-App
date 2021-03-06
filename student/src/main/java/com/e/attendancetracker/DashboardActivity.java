package com.e.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void callLogout(View view) {
    }

    public void callSettings(View view) {

        Intent intent=new Intent(DashboardActivity.this,SettingActivity.class);
        startActivity(intent);
    }

    public void callProfile(View view) {
        Intent intent=new Intent(DashboardActivity.this,ProfileActivity.class);
        startActivity(intent);
    }

    public void callMakeAttendance(View view) {
        Intent intent=new Intent(DashboardActivity.this,MakeAttendanceActivity.class);
        startActivity(intent);
    }

    public void callReport(View view) {
        
    }
}