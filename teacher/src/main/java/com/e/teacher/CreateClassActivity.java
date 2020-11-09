package com.e.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class CreateClassActivity extends AppCompatActivity {

    ArrayList<Batch> batchArrayList;
    ArrayList<String> teacherlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref1,ref2,ref3;
        ref1=databaseReference.child("batches");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                collectData((Map<String,Object>) snapshot.getValue());
                System.out.println("Size"+batchArrayList.size());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void collectData(Map<String,Object> batches) {

        for (Map.Entry<String, Object> entry : batches.entrySet()){
            Map singleBatch = (Map) entry.getValue();
            Batch batch = new Batch();
            batch.setBatchname(singleBatch.get("batchname").toString());
            batch.setBatchid(Integer.valueOf((Integer) singleBatch.get("batchid")));
            batchArrayList.add(batch);
        }
    }
    public void createClass(View view){


    }
}