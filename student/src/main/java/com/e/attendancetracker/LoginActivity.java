package com.e.attendancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextView txtforgotpassword;
    private EditText scholarno;
    private EditText password;

    private CurrentStudent currentStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        scholarno=(EditText) findViewById(R.id.etschnmbr);
        password=(EditText) findViewById(R.id.etpasswrd);
        txtforgotpassword=(TextView) findViewById(R.id.tvforgotpassword);
        txtforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }
    public void openDialog(){
        DialogForgotPassword dialogobj=new DialogForgotPassword();
        dialogobj.show(getSupportFragmentManager(),"Forgot Password");
    }
    private Boolean validateUsername() {
        String val = scholarno.getText().toString();
        if (val.isEmpty()) {
            // scholarno.setError("Field cannot be empty");
            Toast.makeText(getApplicationContext(),"Field cannot be empty",Toast.LENGTH_SHORT).show();
            return false;
        } else {

            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getText().toString();
        if (val.isEmpty()) {
            //password.setError("Field cannot be empty");
            Toast.makeText(getApplicationContext(),"Field cannot be empty",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private void isUser() throws Exception {

        final String userEnteredUsername = scholarno.getText().toString().trim();
        final String userEnteredPassword = AESCrypt.encrypt(password.getText().toString());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Students");

        Query checkUser = reference.child(userEnteredUsername);

        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //               System.out.println("DataSnapshot "+dataSnapshot.getChildrenCount());
                if (dataSnapshot.exists()) {

                    String passwordFromDB = dataSnapshot.child("password").getValue(String.class);
                    String nameFromDB=dataSnapshot.child("name").getValue(String.class);
                    String emailFromDB=dataSnapshot.child("email").getValue(String.class);

                    if(userEnteredPassword.equals(passwordFromDB)){
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
        if (!validateUsername() | !validatePassword()) {
            return;
        }else{
            try {

                isUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}