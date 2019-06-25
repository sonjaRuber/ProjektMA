package com.example.projektma;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Control {
    /**
     * Requests affirmation of login data from web server
     * @param id username/email
     * @param pw password
     * @return true if login successful, false if not
     */
    public static JSONObject login(String id, String pw) throws Exception{
        String urlString = "https://nameless-cove-54313.herokuapp.com/login";

        try{
            JSONObject loginJSON = new JSONObject();
            loginJSON.put("username", id);
            loginJSON.put("password", pw);
            String loginJSONString = loginJSON.toString();

            // make request
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(loginJSONString.getBytes("UTF-8"));
            outputStream.close();

            // read response
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            String result = IOUtils.toString(inputStream, "UTF-8");

            // make JSON from response
            JSONObject loginResponse = new JSONObject(result);
            inputStream.close();
            connection.disconnect();

            // check if login was successful based on response
            return loginResponse;
        }
        catch(Exception e){
            System.out.println("Exception in method login: " + e);
            return null;
        }
    }

    public static JSONArray getGroups(String uname) throws Exception{
        String urlString = "https://nameless-cove-54313.herokuapp.com/groups/" + uname;

        try{
            // open http request
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setConnectTimeout(15000);
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");

            // read response
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            String result = IOUtils.toString(inputStream, "UTF-8");
            inputStream.close();
            connection.disconnect();

            // make JSON from response, return its array
            JSONObject resultJSON = new JSONObject(result);
            JSONArray returnArray = resultJSON.getJSONArray("groups");
            if(returnArray.length() == 0){
                return null;
            }
            return returnArray;
        }
        catch(Exception e){
            System.out.println("Exception in method getGroups: " + e);
            return null;
        }
    }

    public static JSONObject getThreads(String groupName) throws Exception{
        String urlString = "https://nameless-cove-54313.herokuapp.com/threads/" + groupName;

        try {
            // open http request
            URL url = new URL(urlString.replaceAll(" ", "%20"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setConnectTimeout(15000);
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");

            // read response
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            String result = IOUtils.toString(inputStream, "UTF-8");
            inputStream.close();
            connection.disconnect();

            // make JSON from response, build and return nested array
            JSONObject resultJSON = new JSONObject(result);
            if(resultJSON.getJSONArray("tids").length() == 0){
                return null;
            }
            return resultJSON;
        }
        catch(Exception e){
            System.out.println("Exception in method getThreads: " + e);
            return null;
        }
    }

    public static JSONObject getPosts(int tid) throws Exception{
        String urlString = "https://nameless-cove-54313.herokuapp.com/posts/" + tid;

        try {
            // open http request
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setConnectTimeout(15000);
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");

            // read response
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            String result = IOUtils.toString(inputStream, "UTF-8");
            inputStream.close();
            connection.disconnect();

            // make JSON from response, build and return nested array
            JSONObject resultJSON = new JSONObject(result);
            if(resultJSON.getJSONArray("pids").length() == 0){
                return null;
            }
            return resultJSON;
        }
        catch(Exception e){
            System.out.println("Exception in method getThreads: " + e);
            return null;
        }
    }

    public static boolean addPost(JSONObject postJSON) throws Exception{
        String urlString = "https://nameless-cove-54313.herokuapp.com/posts/add/" + postJSON.getInt("tid");

        try{
            String postJSONString = postJSON.toString();

            // make request
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(postJSONString.getBytes("UTF-8"));
            outputStream.close();

            // read response
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            String result = IOUtils.toString(inputStream, "UTF-8");
            inputStream.close();
            connection.disconnect();

            // make JSON from response, return success value
            JSONObject resultJSON = new JSONObject(result);
            return resultJSON.getBoolean("success");
        }
        catch(Exception e){
            System.out.println("Exception in method addPost: " + e);
            return false;
        }
    }
}
