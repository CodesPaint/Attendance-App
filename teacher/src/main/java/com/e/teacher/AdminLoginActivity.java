package com.e.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AdminLoginActivity extends AppCompatActivity {

    EditText txtusername;
    EditText txtpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        txtusername=(EditText)findViewById(R.id.etusername);
        txtpassword=(EditText)findViewById(R.id.etpasswrd);
    }
    public void callLogin(View view){
        if(txtusername.getText().toString()!=""&&txtpassword.getText().toString()!=""){

            Intent intent=new Intent(AdminLoginActivity.this,AdminDashboardActivity.class);
            startActivity(intent);
            return;
        }
    }
}