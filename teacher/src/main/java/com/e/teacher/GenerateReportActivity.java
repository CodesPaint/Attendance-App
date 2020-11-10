package com.e.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GenerateReportActivity extends AppCompatActivity {

    Spinner selectbatch;
    ArrayList<String> batchlist;

    ListView lvreport;
    int batchid=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);

        selectbatch=(Spinner)findViewById(R.id.spselectbatch);
        lvreport=(ListView) findViewById(R.id.lvstudents);
        loadSpinner();

        selectbatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str[]=adapterView.getItemAtPosition(i).toString().split("-");
                batchid=Integer.valueOf(str[1]);

                loaddata();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void loaddata() {

    }

    private void loadSpinner(){
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference();
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childDataSnapshot : snapshot.child("batches").getChildren()){
                        String str=childDataSnapshot.child("batchname").getValue(String.class);
                        str+="-";
                        str+=String.valueOf(childDataSnapshot.child("batchid").getValue(Integer.class));
                        batchlist.add(str);
                }
                initializeUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initializeUI() {

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, batchlist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectbatch.setAdapter(aa);
    }

}