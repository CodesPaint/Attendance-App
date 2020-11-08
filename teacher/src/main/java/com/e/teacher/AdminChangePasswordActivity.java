package com.e.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminChangePasswordActivity extends AppCompatActivity {

    EditText txtcurrentpassword;
    EditText txtnewpassword;
    EditText txtconfirmnewpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_password);

        txtcurrentpassword=(EditText) findViewById(R.id.etcurrentpassword);
        txtnewpassword=(EditText) findViewById(R.id.etnewpassword);
        txtconfirmnewpassword=(EditText) findViewById(R.id.etconfirmnewpassword);
    }

    public void updatePassword(View view){

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}