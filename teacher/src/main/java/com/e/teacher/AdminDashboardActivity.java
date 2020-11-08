package com.e.teacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class AdminDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutaddteacher;
    LinearLayout layoutaddbatch;
    LinearLayout layoutcreateclass;
    LinearLayout layoutadminchangepassword;
    LinearLayout layoutlogout;
    LinearLayout layoutexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        layoutaddteacher=(LinearLayout) findViewById(R.id.layoutaddteacher);
        layoutaddteacher.setOnClickListener(this);

        layoutaddbatch=(LinearLayout) findViewById(R.id.layoutaddbatch);
        layoutaddbatch.setOnClickListener(this);

        layoutcreateclass=(LinearLayout) findViewById(R.id.layoutcreateclass);
        layoutcreateclass.setOnClickListener(this);

        layoutadminchangepassword=(LinearLayout) findViewById(R.id.layoutadminchangepassword);
        layoutadminchangepassword.setOnClickListener(this);

        layoutlogout=(LinearLayout) findViewById(R.id.layoutlogout);
        layoutlogout.setOnClickListener(this);

        layoutexit=(LinearLayout) findViewById(R.id.layoutexit);
        layoutexit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Intent intent;
        switch (view.getId()) {
            case R.id.layoutaddteacher:
                intent=new Intent(AdminDashboardActivity.this,AddTeacherActivity.class);
                startActivity(intent);
                return;
            case R.id.layoutcreateclass:
                intent=new Intent(AdminDashboardActivity.this,CreateClassActivity.class);
                startActivity(intent);
                return;
            case R.id.layoutaddbatch:
                intent=new Intent(AdminDashboardActivity.this,AddBatchActivity.class);
                startActivity(intent);
                return;
            case R.id.layoutadminchangepassword:
                intent=new Intent(AdminDashboardActivity.this,AdminChangePasswordActivity.class);
                startActivity(intent);
                return;
            case R.id.layoutlogout:
                return;
            case R.id.layoutexit:
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboardActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Do you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return;
        }
    }
}