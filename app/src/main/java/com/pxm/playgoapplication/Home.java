package com.pxm.playgoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mauth;
    ImageView a1,a2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        a1=findViewById(R.id.option1);
        a2=findViewById(R.id.option2);

        mauth=FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);

        navigationView.bringToFront();

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Look.class));
            }
        });

        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Hook.class));
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_look:
                startActivity(new Intent(Home.this,Look.class));
                break;
            case R.id.nav_hook:
                startActivity(new Intent(Home.this,Hook.class));
                break;
            case R.id.logout:
                mauth.signOut();
                Toast.makeText(Home.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home.this,Login.class));
                finish();
                break;
            case R.id.share1:
                Toast.makeText(Home.this, "Share with friends", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share2:
                Toast.makeText(Home.this, "Write us at support@playgo.com", Toast.LENGTH_SHORT).show();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}