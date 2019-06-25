package com.example.projektma;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class PostActivity extends AppCompatActivity{
    SharedPreferences userSave;
    SharedPreferences.Editor editor;
    Intent intent;
    EditText editText;

    public static class TaskAddPost extends AsyncTask<JSONObject, Void, Boolean>{
        @Override
        protected Boolean doInBackground(JSONObject... postJSON) {
            try {
                return Control.addPost(postJSON[0]);
            }
            catch(Exception e){
                System.out.println("Exception adding post: " + e);
                return null;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // initialization
        userSave = this.getSharedPreferences("userSave", Context.MODE_PRIVATE);
        intent = getIntent();
        FloatingActionButton addButton = findViewById(R.id.floatingButton);
        editText = findViewById(R.id.editText);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New post in: " + intent.getStringExtra("threadTitle"));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                String checkForOnlySpaces = text.replaceAll(" ", "");

                if(checkForOnlySpaces.equals("")){
                    Toast.makeText(getApplicationContext(), "Add some text before posting!", Toast.LENGTH_SHORT).show();
                } else{
                    // generate JSON to post
                    JSONObject postJSON = new JSONObject();
                    try{
                        postJSON.put("text", text);
                        postJSON.put("tid", intent.getIntExtra("tid", -1));
                        postJSON.put("uname", userSave.getString("activeUser", "default"));
                    } catch (Exception e){
                        System.out.println("Exception creating JSON to post: " + e);
                    }

                    boolean success = false;
                    try{
                        success = new TaskAddPost().execute(postJSON).get();
                    } catch(Exception e){
                        System.out.println("Exception adding post: " + e);
                    }

                    if(!success){
                        Toast.makeText(getApplicationContext(), "Post could not be added. Try again later.", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getApplicationContext(), "Post successfully added!", Toast.LENGTH_SHORT).show();
                        PostActivity.this.finish();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == (R.id.action_logout)){
            userSave = this.getSharedPreferences("userSave", Context.MODE_PRIVATE);
            editor = userSave.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
