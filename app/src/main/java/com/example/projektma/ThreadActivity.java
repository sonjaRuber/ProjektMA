package com.example.projektma;

import android.app.Activity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class ThreadActivity extends AppCompatActivity{
    private ArrayList<String> datasetPoster;
    private ArrayList<String> datasetTimestamp;
    private ArrayList<String> datasetText;
    SharedPreferences userSave;
    SharedPreferences.Editor editor;
    Intent intent;
    public static Activity threadActivity;

    public static class TaskFetchPosts extends AsyncTask<Integer, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Integer... tids) {
            try {
                return Control.getPosts(tids[0]);
            }
            catch(Exception e){
                System.out.println("Exception getting posts: " + e);
                return null;
            }
        }
    }

    private void refresh(){
        JSONObject posts = null;
        JSONArray datasetPosterJSONArray = null;
        JSONArray datasetTimestampJSONArray = null;
        JSONArray datasetTextJSONArray = null;
        this.datasetPoster = new ArrayList<String>();
        this.datasetTimestamp = new ArrayList<String>();
        this.datasetText = new ArrayList<String>();
        // fetch posts via AsyncTask
        try {
            posts = new TaskFetchPosts().execute(intent.getIntExtra("tid", -1)).get();
            datasetPosterJSONArray = posts.getJSONArray("unames");
            datasetTimestampJSONArray = posts.getJSONArray("timestamps");
            datasetTextJSONArray = posts.getJSONArray("texts");
            for(int i = 0; i < datasetPosterJSONArray.length(); i++){
                datasetPoster.add((String) datasetPosterJSONArray.get(i));
                datasetTimestamp.add((String) datasetTimestampJSONArray.get(i));
                datasetText.add((String) datasetTextJSONArray.get(i));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        threadActivity = this;

        // initialize
        intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton addButton = findViewById(R.id.floatingButton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(intent.getStringExtra("threadTitle"));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThreadActivity.this, PostActivity.class);
                i.putExtra("tid", intent.getIntExtra("tid", -1));
                i.putExtra("threadTitle", intent.getStringExtra("threadTitle"));
                startActivity(i);
            }
        });

        RecyclerView postList = (RecyclerView) findViewById(R.id.recyclerViewPostList);

        // initialize all needed objects for data display
        refresh();

        // use a linear post_layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        postList.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter mAdapter = new PostListAdapter(datasetPoster, datasetTimestamp, datasetText);
        postList.setAdapter(mAdapter);
    }

    protected void onResume(){
        super.onResume();
        RecyclerView postList = (RecyclerView) findViewById(R.id.recyclerViewPostList);
        refresh();
        // use a linear post_layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        postList.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter mAdapter = new PostListAdapter(datasetPoster, datasetTimestamp, datasetText);
        postList.setAdapter(mAdapter);
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
