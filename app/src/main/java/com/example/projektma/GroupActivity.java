package com.example.projektma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {
    private ArrayList<String> threadTitles;
    private ArrayList<Integer> tids;
    Intent intent;
    SharedPreferences userSave;
    SharedPreferences.Editor editor;
    public static Activity groupActivity;

    public static class TaskFetchThreads extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... groupName){
            try{
                return Control.getThreads(groupName[0]);
            }
            catch(Exception e){
                System.out.println("Exception getting threads: " + e);
                return null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        groupActivity = this;

        // initialization
        intent = getIntent();
        String groupName = intent.getStringExtra("groupName");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton addButton = findViewById(R.id.floatingButton);

        // toolbar title = group name
        getSupportActionBar().setTitle(groupName);

        RecyclerView threadList = findViewById(R.id.recyclerViewThreadList);

        //
        JSONObject threads = null;
        JSONArray tidsJSONArray = null;
        JSONArray titlesJSONArray = null;
        this.threadTitles = new ArrayList<String>();
        this.tids = new ArrayList<Integer>();

        // fill datasets with data from server
        try{
            threads = new TaskFetchThreads().execute(groupName).get();
            tidsJSONArray = threads.getJSONArray("tids");
            titlesJSONArray = threads.getJSONArray("titles");
            for(int i = 0; i < tidsJSONArray.length(); i++){
                tids.add((Integer) tidsJSONArray.get(i));
                threadTitles.add((String) titlesJSONArray.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // use a linear post_layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        threadList.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter mAdapter = new ThreadListAdapter(threadTitles, tids);
        threadList.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed(){
        this.finish();
        super.onBackPressed();
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
