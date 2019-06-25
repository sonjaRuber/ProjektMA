package com.example.projektma;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static Activity mainActivity;

    Button mainRegisterBtn, mainLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        // init views
        mainRegisterBtn = findViewById(R.id.register_btn);
        mainLoginBtn = findViewById(R.id.login_btn);

        /**
         * onclicklistener fuer den mainregister button
         */
        mainRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start RegisterActivity
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));

            }
        });

        /**
         * onclicklistener fuer mainlogin button
         */
        mainLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

}
