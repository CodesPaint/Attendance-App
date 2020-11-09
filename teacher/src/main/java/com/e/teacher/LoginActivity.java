package com.e.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText txtregistrationno;
    EditText txtpassword;
    CurrentTeacher currentTeacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtregistrationno=(EditText) findViewById(R.id.etregistrationno);
        txtpassword=(EditText)findViewById(R.id.etpasswrd);

        currentTeacher = (CurrentTeacher)getApplicationContext();
    }
    private void isUser() throws Exception {

        final String userEnteredUsername = txtregistrationno.getText().toString().trim();
        final String userEnteredPassword = AESCrypt.encrypt(txtpassword.getText().toString());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Teachers");

        Query checkUser = reference.child(userEnteredUsername);

        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //               System.out.println("DataSnapshot "+dataSnapshot.getChildrenCount());
                if (dataSnapshot.exists()) {

                    String passwordFromDB = dataSnapshot.child("password").getValue(String.class);
                    String nameFromDB=dataSnapshot.child("registrationno").getValue(String.class);
                    String emailFromDB=dataSnapshot.child("email").getValue(String.class);

                    if(userEnteredPassword.equals(passwordFromDB)){
                        currentTeacher.setName(nameFromDB);
                        currentTeacher.setRegistrationno(Long.parseLong(userEnteredUsername));
                        currentTeacher.setEmail(emailFromDB);
                        Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
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
        if(txtregistrationno.getText().toString().equals("")&&txtpassword.getText().toString().equals("")){
            return;
        }
        try {
            isUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
        startActivity(intent);
    }
}