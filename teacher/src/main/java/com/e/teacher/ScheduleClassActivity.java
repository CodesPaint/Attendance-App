package com.e.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScheduleClassActivity extends AppCompatActivity {

    EditText starttime;
    Spinner selectduration;
    Spinner selectclass;
    CalendarView calendarView;
    String[] duration = { "30 Minutes","45 Minutes", "1 Hours"};
    ArrayList<String> classlist;
    CurrentTeacher currentTeacher;

    int newmeetid=-1;
    int classid=-1;
    String txtduration="";
    String d1="";
    String meetname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_class);

        classlist=new ArrayList<>();
        currentTeacher = (CurrentTeacher)getApplicationContext();
        loadSpinner();

        calendarView=(CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                if(d1.equals("")){
                    d1=year+"-"+(month+1)+"-"+(day-1);
                }
            }
        });

        starttime=(EditText) findViewById(R.id.time);
        starttime=(EditText)findViewById(R.id.time);
        selectduration=(Spinner)findViewById(R.id.spduration);
        selectclass=(Spinner)findViewById(R.id.spclass);

        selectclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str[]=adapterView.getItemAtPosition(i).toString().split("-");
                classid=Integer.valueOf(str[1]);
                meetname=str[0].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        selectduration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(txtduration.length()!=0){
                    txtduration=new String();
                }
                txtduration+=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newmeetid = snapshot.child("flagstatus").child("meetid").getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void loadSpinner(){
        String regno=String.valueOf(currentTeacher.getRegistrationno());
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference();
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childDataSnapshot : snapshot.child("classes").getChildren()){
                    String tmp=String.valueOf(childDataSnapshot.child("teacherregistrationno").getValue(Long.class));
                    System.out.println("Helllo"+ regno+" -------"+tmp);
                    if(regno.equals(tmp)){
                        String str=childDataSnapshot.child("classname").getValue(String.class);
                        str+="-";
                        str+=String.valueOf(childDataSnapshot.child("classid").getValue(Integer.class));
                        classlist.add(str);
                    }
                }
                initializeUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        }

    private void initializeUI() {
            System.out.println(currentTeacher.getRegistrationno()+" "+classlist.size());
            ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, classlist);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selectclass.setAdapter(aa);
    }

    public void createMeet(View view) {
        Meet meet=new Meet();
        meet.setMeetid(newmeetid);
        meet.setClassid(classid);
        meet.setDate(d1);
        meet.setStarttime(starttime.getText().toString());
        meet.setDuartion(txtduration);
        meet.setMeetotp(RandomGenerator.generate(8));
        meet.setMeettitle(meetname);
        if(newmeetid!=-1){
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("meetings");
            databaseReference1.child(String.valueOf(meet.getMeetid())).setValue(meet);
            DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference();
            databaseReference2.child("flagstatus").child("meetid").setValue(newmeetid+1);
            Toast.makeText(ScheduleClassActivity.this,"Meeting is have been Scheduled",Toast.LENGTH_LONG).show();
            finish();
        }

    }
}