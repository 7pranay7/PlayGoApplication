package com.pxm.playgoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout name,username,email,phoneNo,password,cpassword;
    Button callLogin,regBtn;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        callLogin=findViewById(R.id.call_login);
        name=findViewById(R.id.name);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        phoneNo=findViewById(R.id.phoneNo);
        password=findViewById(R.id.password);
        cpassword=findViewById(R.id.cpassword);
        regBtn=findViewById(R.id.reg_btn);

        mAuth=FirebaseAuth.getInstance();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void createUser(){
        String Email=email.getEditText().getText().toString();
        String Password=password.getEditText().getText().toString();
        String Username=username.getEditText().getText().toString();
        String PhoneNo=phoneNo.getEditText().getText().toString();
        String Name=name.getEditText().getText().toString();
        String Cpassword=cpassword.getEditText().getText().toString();
        UserHelperClass helperClass=new UserHelperClass(Name,Username,Email,PhoneNo,Password);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        if(TextUtils.isEmpty(Name)){
            name.setError("Name cannot be empty");
            name.requestFocus();
        }
        else if(TextUtils.isEmpty(Username)){
            username.setError("Username cannot be empty");
            username.requestFocus();
        }else if(TextUtils.isEmpty(Email)){
            email.setError("Email cannot be empty");
            email.requestFocus();
        } else if(TextUtils.isEmpty(PhoneNo) || PhoneNo.length()<10){
            phoneNo.setError("Enter valid phone number");
            phoneNo.requestFocus();
        }
        else if(TextUtils.isEmpty(Password)){
            password.setError("Password cannot be empty");
            password.requestFocus();
        }else if(TextUtils.isEmpty(Cpassword)){
            cpassword.setError("Re enter password");
            cpassword.requestFocus();
        }else if(!Password.equals(Cpassword)){
            cpassword.setError("Passwords does not match");
            cpassword.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUp.this, "Registeration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this,Login.class));
                        myRef.child(Username).setValue(helperClass);
                        finish();
                    }else{
                        Toast.makeText(SignUp.this, "Registeration error. "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}
