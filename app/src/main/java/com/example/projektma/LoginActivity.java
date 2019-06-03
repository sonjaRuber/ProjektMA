package com.example.projektma;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity{

    private TextView not_have_account;
    private EditText userNameORemailEditText, passwordET;
    private Button loginBtn;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        /**
         * Actionbar hat den Fehler verursacht wtf
         */

        // initialize
        not_have_account = findViewById(R.id.not_have_account);
        userNameORemailEditText = findViewById(R.id.userNameORemailEditText);
        passwordET = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.login_btn);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
            }
        });

        not_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed(); //previous activity
        return super.onSupportNavigateUp();
    }
}
