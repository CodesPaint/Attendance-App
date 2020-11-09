package com.e.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    EditText txtusername;
    EditText txtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        txtusername = (EditText) findViewById(R.id.etusername);
        txtpassword = (EditText) findViewById(R.id.etpasswrd);
    }
    private void isUser() throws Exception {

        final String userEnteredUsername = txtusername.getText().toString().trim();
        final String userEnteredPassword = txtpassword.getText().toString().trim();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Admin");

        Query checkUser = reference.child(userEnteredUsername);

        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //               System.out.println("DataSnapshot "+dataSnapshot.getChildrenCount());
                if (dataSnapshot.exists()) {
                    String passwordFromDB = dataSnapshot.child("password").getValue(String.class);
                    String nameFromDB=dataSnapshot.child("username").getValue(String.class);
                    String emailFromDB=dataSnapshot.child("username").getValue(String.class);

                    if(userEnteredPassword.equals(passwordFromDB)){

                        Intent intent=new Intent(AdminLoginActivity.this,AdminDashboardActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getApplicationContext(),"Incorrect Username Password",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void callLogin(View view) {
        if (txtusername.getText().toString().equals("")) {
            txtusername.setError("Empty Field");
            return;
        }
        if (txtpassword.getText().toString().equals("")) {
            txtpassword.setError("Empty Field");
            return;
        }
        try {
            isUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}