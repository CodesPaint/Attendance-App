package com.e.attendancetracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MakeAttendanceActivity extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<Integer> classid=new ArrayList<>();
    private ArrayList<String> otplist=new ArrayList<>();
    private ArrayList<String> meetdatelist=new ArrayList<>();
    private ArrayList<Long> meetidlist=new ArrayList<>();
    ListView meetlist;
    List<String> scholarlist;
    CurrentStudent currentStudent;
    int pos=-1;
    String otp="";
    String meetotp="";
    String meetdate="";
    Long meetid=-1L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_attendance);

        currentStudent=(CurrentStudent)getApplicationContext();

        meetlist = (ListView) findViewById(R.id.lvmeetings);

        generateListContent();

        meetlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                inputCode();

                /*DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref1,ref2;
                scholarlist=new ArrayList<String>();
                ref1=ref;
                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        scholarlist.clear();
                        for(DataSnapshot dsp:snapshot.child("attendance").child(meetdate).child(String.valueOf(meetid)).getChildren()){
                            String vals=dsp.child("scholar").getValue(String.class);
                            scholarlist.add(vals);
                        }

                        StringBuilder stringBuilder=new StringBuilder();
                        for(int i=0;i<scholarlist.size();i++){
                            stringBuilder.append(scholarlist.get(i)+"-");
                        }
                        System.out.println(stringBuilder+"");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/

            }
        });



    }
    private void inputCode(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Class OTP for Attendance");
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                otp = input.getText().toString();
                Toast.makeText(MakeAttendanceActivity.this, "Class Code is sent to your mail.", Toast.LENGTH_SHORT).show();
                makePresent();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void makePresent() {
        if(otp.equals(meetotp)){

            DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
            ref.child("attendance").child(meetdate).child(String.valueOf(meetid)).child(String.valueOf(currentStudent.getScholar_number())).setValue("P");
            Toast.makeText(MakeAttendanceActivity.this,"Attendance Recoreded Successfully",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(MakeAttendanceActivity.this,"Incorrect Class Code",Toast.LENGTH_SHORT).show();
        }
    }

    private void sendOTP() {

        String mail = currentStudent.getEmail();
        String message = "Your OTP for class is "+otp;
        String subject = "Class Attendance OTP";

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);

        javaMailAPI.execute();

    }



    private void validate() {

    }

    private void generateListContent() {
        String batch=String.valueOf(currentStudent.getBatchid());

        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference();
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childDataSnapshot : snapshot.child("classes").getChildren()){
                    String val=String.valueOf(childDataSnapshot.child("batchid").getValue(Long.class));
                    if(val.equals(batch)){
                        int clsid=childDataSnapshot.child("classid").getValue(Integer.class);
                        classid.add(clsid);
                    }
                }
                for(int id:classid){
                    for (DataSnapshot childDataSnapshot : snapshot.child("meetings").getChildren()){
                        String val=String.valueOf(childDataSnapshot.child("classid").getValue(Integer.class));
                        if(val.equals(String.valueOf(id))){
                            String str=String.valueOf(childDataSnapshot.child("meettitle").getValue(String.class));
                            str+="-";
                            str+=String.valueOf(childDataSnapshot.child("meetid").getValue(Integer.class));
                            meetidlist.add(childDataSnapshot.child("meetid").getValue(Long.class));
                            data.add(str);

                            String tmp=String.valueOf(childDataSnapshot.child("meetotp").getValue(String.class));
                            otplist.add(tmp);

                            String t=String.valueOf(childDataSnapshot.child("date").getValue(String.class));
                            meetdatelist.add(t);
                        }
                    }
                }
                MyListAdaper myListAdaper=new MyListAdaper(MakeAttendanceActivity.this, R.layout.list_item, data);
                meetlist.setAdapter(myListAdaper);
                System.out.println(classid.size()+"    "+data.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.tvmeetname);
                viewHolder.button = (Button) convertView.findViewById(R.id.btotp);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    pos=position;
                    meetotp= otplist.get(position);
                    meetdate=meetdatelist.get(position);
                    meetid= meetidlist.get(position);
                    sendOTP();
                    inputCode();
                    //Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                }
            });
            mainViewholder.title.setText(getItem(position));
            return convertView;
        }
    }

    public class ViewHolder {

        TextView title;
        Button button;
    }


}