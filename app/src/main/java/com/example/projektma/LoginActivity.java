package com.example.projektma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import static com.example.projektma.MainActivity.mainActivity;

public class LoginActivity extends AppCompatActivity{

    private TextView not_have_account;
    private TextView loginFailure;
    private EditText userNameOrEmailET, passwordET;
    private Button loginBtn;
    SharedPreferences userSave;
    SharedPreferences.Editor editor;
    ProgressBar loadingCircle;

    private class loginRunnable implements Runnable{
        public void run() {
            loginFailure.setVisibility(View.INVISIBLE);
            String enteredUser = userNameOrEmailET.getText().toString();
            String enteredPW = passwordET.getText().toString();

            JSONObject loginJSON = new JSONObject();
            JSONObject resultJSON;

            try{
                loginJSON.put("username", enteredUser);
                loginJSON.put("password", enteredPW);
                resultJSON = Control.login(enteredUser, enteredPW);

                if(resultJSON != null && resultJSON.getBoolean("success")){
                    editor.putString("activeUser", resultJSON.getString("username"));
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                    LoginActivity.this.finish();
                    mainActivity.finish();
                    // TODO - do this next call when going back to this screen, not here
                    loadingCircle.setVisibility(View.INVISIBLE);
                }
                else{
                    loadingCircle.setVisibility(View.INVISIBLE);
                    loginFailure.setVisibility(View.VISIBLE);
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userSave = this.getSharedPreferences("userSave", Context.MODE_PRIVATE);
        editor = userSave.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize
        not_have_account = findViewById(R.id.not_have_account);
        userNameOrEmailET = findViewById(R.id.userNameORemailEditText);
        passwordET = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.login_btn);
        loginFailure = findViewById(R.id.loginFailure);
        loginFailure.setVisibility(View.INVISIBLE);

        //loading circle stuff
        loadingCircle = findViewById(R.id.loadingCircle);
        loadingCircle.setVisibility(View.GONE);


        /**
         * Login Button
         */
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.loadingCircle).setVisibility(View.VISIBLE);
                try{
                    Thread loginRunner = new Thread(new loginRunnable());
                    loginRunner.start();
                }
                catch(Exception e){
                    System.out.println("Exception logging in: " + e);
                }
            }
        });

        /**
         * onclicklistener fuer not_have_account button
         */
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
