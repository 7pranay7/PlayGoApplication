package com.pxm.playgoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HookComplete extends AppCompatActivity {

    Button returnhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_complete2);
        returnhome=findViewById(R.id.return_home);
       returnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HookComplete.this,Home.class));
                finish();
            }
        });

    }
}