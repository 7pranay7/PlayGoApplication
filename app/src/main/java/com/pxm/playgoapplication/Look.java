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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Look extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteTextView2;
    String city;
    String sport;
    String locationname;
    Double latitude,longitude;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button look;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);

        autoCompleteTextView=findViewById(R.id.autoCompleteTextView3);
        autoCompleteTextView2=findViewById(R.id.autoCompleteTextView4);

        look=findViewById(R.id.Look_btn);

        String []option ={"Jaipur","Kanpur","Varanasi",};
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.dropdown_city,option);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(),false);
        autoCompleteTextView.setAdapter(arrayAdapter);

        String []option2 ={"Badminton","Cricket","Football",};
        ArrayAdapter arrayAdapter2=new ArrayAdapter(this,R.layout.dropdown_city,option2);
        autoCompleteTextView2.setText(arrayAdapter2.getItem(0).toString(),false);
        autoCompleteTextView2.setAdapter(arrayAdapter2 );

        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city=autoCompleteTextView.getText().toString().trim();
                sport=autoCompleteTextView2.getText().toString();

                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("playground");

                Query checkcity=myRef.orderByChild("city").equalTo(city);

                checkcity.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            locationname = snapshot.child(city).child("locationName").getValue(String.class);
                            if (!TextUtils.isEmpty(locationname)) {
                                Intent intent = new Intent(Look.this, MapPage_2.class);
                                intent.putExtra("k1", locationname);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Look.this, "No playground found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


    }
}