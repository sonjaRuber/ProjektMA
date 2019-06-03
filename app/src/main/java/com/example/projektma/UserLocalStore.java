package com.example.projektma;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore
{
    public static final String SP_Name = "userDetails";
    //for storing data on the phone
    SharedPreferences userLocalDatabase;


    // need to get the context from the activity
    public UserLocalStore(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_Name, 0 );
    }


    /**
     * Method to store data
     */
    public void storeUserData(User user)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", user.username);
        spEditor.putString("email", user.email);
        spEditor.putString("password", user.password);
        spEditor.commit();
    }


    /**
     * Methods to get data to database
     */
    public User getLoggedInUser()
    {
        String username = userLocalDatabase.getString("username", "");
        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password", "");

        User storedUser = new User(username, email, password);

        return storedUser;
    }


    public void setUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }


    public boolean getUserLoggedIn()
    {
        if (userLocalDatabase.getBoolean("loggedIn", false) == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public void clearUserData()
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
