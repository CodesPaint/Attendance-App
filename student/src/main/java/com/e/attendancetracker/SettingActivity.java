package com.e.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if(findViewById(R.id.settings)!=null){
            if(savedInstanceState!=null){
                return;
            }
            getFragmentManager().beginTransaction().add(R.id.settings,new SettingFragment()).commit();
        }
    }
}