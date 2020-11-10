package com.e.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import java.util.ArrayList;

public class GenerateReportActivity extends AppCompatActivity {

    Spinner selectbatch;
    ArrayList<String> classlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);

        
    }
}