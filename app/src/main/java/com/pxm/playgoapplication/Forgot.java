package com.pxm.playgoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {

    Button next;
    TextInputLayout email;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        email=findViewById(R.id.forgot_email);
        next=findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=email.getEditText().getText().toString();
                if(TextUtils.isEmpty(Email)){
                    email.setError("Email cannot be empty");
                    email.requestFocus();
                }
                else{
                    mAuth=FirebaseAuth.getInstance();
                    mAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Forgot.this, "Email verified", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Forgot.this,Forgot2.class));
                            finish();
                        }
                        else{
                            Toast.makeText(Forgot.this, "Error. "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
                }
            }
        });

    }

}