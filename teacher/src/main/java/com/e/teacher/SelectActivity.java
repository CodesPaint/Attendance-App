package com.e.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void redirectToAdmin(View view){
        Intent intent=new Intent(SelectActivity.this,CreateClassActivity.class);
        startActivity(intent);
        return;
    }

    public void redirectToTeacher(View view){
        Intent intent=new Intent(SelectActivity.this,LoginActivity.class);
        startActivity(intent);
        return;
    }

}