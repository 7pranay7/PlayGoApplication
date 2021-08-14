package com.pxm.playgoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Hook extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteTextView2;
    Button mapsearch,hook;
    String city;
    String sport;
    String locationname;
    Double latitude,longitude;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);

        autoCompleteTextView=findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView2=findViewById(R.id.autoCompleteTextView2);

        mapsearch=findViewById(R.id.search_map_btn);
        hook=findViewById(R.id.Hook_btn);

        String []option ={"Jaipur","Kanpur","Varanasi",};
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.dropdown_city,option);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(),false);
        autoCompleteTextView.setAdapter(arrayAdapter);

        String []option2 ={"Badminton","Cricket","Football",};
        ArrayAdapter arrayAdapter2=new ArrayAdapter(this,R.layout.dropdown_city,option2);
        autoCompleteTextView2.setText(arrayAdapter2.getItem(0).toString(),false);
        autoCompleteTextView2.setAdapter(arrayAdapter2 );

        mapsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hook.this,mapPage1.class));
            }
        });

        hook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                city=autoCompleteTextView.getText().toString();
                sport=autoCompleteTextView2.getText().toString();
                Bundle b=getIntent().getExtras();
                locationname=b.getString("locname");
                latitude= b.getDouble("lat");
                longitude= b.getDouble("long");
                /*Intent intent=new Intent(Hook.this,testpage.class);
                intent.putExtra("k1",locationname);
                intent.putExtra("k2",latitude);
                intent.putExtra("k3",longitude);

                startActivity(intent);*/

                UserHelperClass helperClass=new UserHelperClass(city,sport,locationname,latitude,longitude);
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("playground");
                myRef.child(city).setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(Hook.this,HookComplete.class));
                            finish();
                        }else{
                            Toast.makeText(Hook.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

 }
}
