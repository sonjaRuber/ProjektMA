package com.example.projektma;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControlTest {
    // login function tests --------------------------------------------------------------
    @Test
    public void loginTestHeinrich() throws Exception {
        JSONObject result = Control.login("Heinrich", "12345");
        assertEquals(result.getString("username"), "Heinrich");
        assertTrue(result.getBoolean("success"));
    }

    @Test
    public void loginTestDietrich() throws Exception {
        JSONObject result = Control.login("Dietrich", "abcde");
        assertEquals(result.getString("username"), "Dietrich");
        assertTrue(result.getBoolean("success"));
    }

    @Test
    public void loginTestFalse() throws Exception {
        JSONObject result = Control.login("obviouslynotauser", "thisisobviouslynotthepassword");
        assertEquals(result.getString("username"), "obviouslynotauser");
        assertFalse(result.getBoolean("success"));
    }

    // getGroups function tests ----------------------------------------------------------
    @Test
    public void getGroupsTestHeinrich() throws Exception{
        JSONArray resultArray = Control.getGroups("Heinrich");
        assertEquals(resultArray.getString(0), "Klavierspieler mit 12 Fingern");
        assertEquals(resultArray.getString(1),"Jonglieren");
    }

    @Test
    public void getGroupsTestFriedrich() throws Exception{
        JSONArray resultArray = Control.getGroups("Heinrich");
        assertEquals(resultArray.getString(0), "Klavierspieler mit 12 Fingern");
        assertEquals(resultArray.getString(1),"Jonglieren");
    }

    @Test
    public void getGroupsTestFalse() throws Exception{
        JSONArray resultArray = Control.getGroups("notauser");
        assertNull(resultArray);
    }

    // getThreads function tests ---------------------------------------------------------
    @Test
    public void getThreadsTest() throws Exception{
        JSONObject result = Control.getThreads("Jonglieren");
        String control = "{\"tids\":[4,5],\"titles\":[\"Jonglieren mit 1 Ball wie?\",\"Erbsen-Challenge\"]}";
        assertEquals(result.toString(), control);
    }

    @Test
    public void getThreadsTestGroupWithSpaces() throws Exception{
        JSONObject result = Control.getThreads("Klavierspieler mit 12 Fingern");
        String control = "{\"tids\":[1,2,3],\"titles\":[\"Darf man auch mit 14 Fingern mitmachen?\",\"Präludium in C-Dur\",\"Finger gebrochen beim Spielen von die TrÃ¤umerei, was tun?\"]}";
    }

    @Test
    public void getThreadsTestFalse() throws Exception {
        JSONObject result = Control.getThreads("not a group");
        assertNull(result);
    }

    // getPosts function tests -----------------------------------------------------------
    @Test
    public void getPostsTest1() throws Exception{
        JSONObject result = Control.getPosts(1);
        String control = "{\"unames\":[\"Dietrich\",\"Heinrich\"],\"texts\":[\"Ich frage nur mal so...\",\"Unerhört, raus hier!\"],\"timestamps\":[\"2019-09-07\",\"2019-01-08\"],\"pids\":[1,2]}";
        assertEquals(result.toString(), control);
    }

    @Test
    public void getPostsTest2() throws Exception{
        JSONObject result = Control.getPosts(3);
        String control = "{\"unames\":[\"Heinrich\",\"Dietrich\",\"Klaus\",\"Friedrich\"],\"texts\":[\"Ich glaub ich spür meine Finger nicht mehr, soll ich zum Arzt?\",\"Hau dir ein Paar Aconitum Napellus Globuli in D12 rein, das sollte helfen.\",\"Wenn du nicht mehr spielen kannst, kann ich dein Klavier kaufen?\",\"Dieser Mann träumt.\"],\"timestamps\":[\"2019-03-08\",\"2019-05-08\",\"2019-06-08\",\"2019-12-12\"],\"pids\":[3,4,5,6]}";
        assertEquals(result.toString(), control);
    }

    // addPost function tests ------------------------------------------------------------
    @Test
    public void addPostTest() throws Exception{
        JSONObject postJSON = new JSONObject();
        postJSON.put("tid", 2);
        postJSON.put("uname", "Testrich");
        postJSON.put("text", "Unit Test: addPostTest");
        assertTrue(Control.addPost(postJSON));
    }

    @Test
    public void addPostTestInvalidThread() throws Exception{
        JSONObject postJSON = new JSONObject();
        postJSON.put("tid", -1);
        postJSON.put("uname", "Testrich");
        postJSON.put("text", "Unit Test: addPostTest");
        assertFalse(Control.addPost(postJSON));
    }

    @Test
    public void addPostTestNoUser() throws Exception{
        JSONObject postJSON = new JSONObject();
        postJSON.put("tid", -1);
        postJSON.put("text", "Unit Test: addPostTest");
        assertFalse(Control.addPost(postJSON));
    }
}