package com.example.projektma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

public class ProfileActivity extends AppCompatActivity {

    private String groupBtn0String;
    private String groupBtn1String;
    private TextView userName;
    private int backpressed;
    SharedPreferences userSave;
    SharedPreferences.Editor editor;

    // runnable for group request
    private class getGroupsRunnable implements Runnable {
        private String uname;

        public getGroupsRunnable(String uname){
            this.uname = uname;
        }

        public void run(){
            try{
                JSONArray groupsArray = Control.getGroups(uname);
                if(groupsArray != null){
                    groupBtn0String = groupsArray.getString(0);
                    groupBtn1String = groupsArray.getString(1);
                } else{
                    groupBtn0String = "FAILED TO GET GROUPS";
                    groupBtn1String = "FAILED TO GET GROUPS";
                }
            }
            catch(Exception e){
                System.out.println("Exception in getGroupsThread/run(): " + e);
            }
        }
    }

    // variable onClickListener based on thread ID
    private class OnClickListenerGroupBtn implements View.OnClickListener {
        private String groupName;

        public OnClickListenerGroupBtn(String groupName){
            this.groupName = groupName;
        }

        public void onClick(View v){
            Intent intent = new Intent(ProfileActivity.this, GroupActivity.class);
            intent.putExtra("groupName", groupName);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        backpressed = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // for logout with backpressed purposes
        backpressed = 0;

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Button groupBtn0 = findViewById(R.id.groupButton);
        Button groupBtn1 = findViewById(R.id.groupButton2);

        userSave = this.getSharedPreferences("userSave", MODE_PRIVATE);

        // toolbar title = user
        getSupportActionBar().setTitle(userSave.getString("activeUser", "default"));

        // initialize buttons array
        Button[] groupBtns = new Button[2];
        groupBtns[0] = groupBtn0;
        groupBtns[1] = groupBtn1;

        // get groups to display
        try{
            getGroupsRunnable ggr = new getGroupsRunnable(userSave.getString("activeUser", "default"));
            Thread getGroupsThread = new Thread(ggr);
            getGroupsThread.start();
            getGroupsThread.join(5000);
        }
        catch(Exception e){
            System.out.println("Exception in profileActivity/onCreate/getGroupsThread: " + e);
        }

        // display group names on buttons
        groupBtn0.setText(groupBtn0String);
        groupBtn1.setText(groupBtn1String);

        // set onClickListeners for group buttons
        for(int i = 0; i < 2; i++){
            final Button groupBtnX = groupBtns[i];
            groupBtns[i].setOnClickListener(new OnClickListenerGroupBtn(groupBtns[i].getText().toString()));
        }
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

    @Override
    public void onBackPressed(){
        backpressed++;

        if(backpressed > 1){
            userSave = this.getSharedPreferences("userSave", Context.MODE_PRIVATE);
            editor = userSave.edit();
            editor.clear();
            editor.apply();
            Toast.makeText(getApplicationContext(), "Successfully logged out.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "Press back again to log out.", Toast.LENGTH_SHORT).show();
        }
    }
}
