package com.e.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class ScheduleClassActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText starttime;
    Spinner selectduration;
    Spinner selectclass;
    String[] duration = { "30 Minutes","45 Minutes", "1 Hours",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_class);

        starttime=(EditText)findViewById(R.id.time);
        selectduration=(Spinner)findViewById(R.id.spduration);
        selectclass=(Spinner)findViewById(R.id.spclass);
        selectclass.setOnItemSelectedListener(this);
        selectduration.setOnItemSelectedListener(this);


        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,duration);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        selectduration.setAdapter(aa);

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ScheduleClassActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //starttime.setText(selectedHour + ":" + selectedMinute);
                        if(selectedMinute == 0 ) {
                            starttime.setText(selectedHour +":00");
                        }
                        else if(selectedMinute < 10){
                            starttime.setText(selectedHour + ":0" + selectedMinute);
                        }
                        else {
                            starttime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(view.getId()){
            case R.id.spclass:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}