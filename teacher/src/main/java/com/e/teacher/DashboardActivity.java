package com.e.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;

    LinearLayout layoutclass;
    LinearLayout layoutscheduleclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        layoutclass=(LinearLayout)findViewById(R.id.layoutclass);
        layoutclass.setOnClickListener(this);
        layoutscheduleclass=(LinearLayout)findViewById(R.id.layoutscheduleclass);
        layoutscheduleclass.setOnClickListener(this);

        drawerLayout=(DrawerLayout)findViewById(R.id.dashboardactivity);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.menu_icon);
        setSupportActionBar(toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onClick(View view){
        Intent intent;
        switch (view.getId()) {
            case R.id.layoutclass:
                intent=new Intent(DashboardActivity.this,ListClassActivity.class);
                startActivity(intent);
                return;
            case R.id.layoutscheduleclass:
                intent=new Intent(DashboardActivity.this,ScheduleClassActivity.class);
                startActivity(intent);
                return;
        }


    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}