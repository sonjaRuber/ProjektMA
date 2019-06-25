package com.example.projektma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RegisterActivity extends AppCompatActivity{

    // views
    EditText mUserNameEt, mEmailEt, mPasswordEt;
    Button mRegisterBtn;
    TextView mHaveAccount;

    //progressbar to display while registering user
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //init
        mUserNameEt = findViewById(R.id.userNameEditText);
        mEmailEt = findViewById(R.id.emailEditText);
        mPasswordEt = findViewById(R.id.passwordET);
        mRegisterBtn = findViewById(R.id.register_btn);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User . . .");
        mHaveAccount = findViewById(R.id.have_account);

        //handle button register
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
            }
        });


        mHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed(); //previous activity
        return super.onSupportNavigateUp();
    }
}
