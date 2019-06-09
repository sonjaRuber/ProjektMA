package com.example.projektma.persistence.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ForumThreadDao {

    @Query("SELECT * FROM threads")
    public LiveData<List<ForumThread>> loadAllForumThreads();

    @Query("SELECT * FROM threads WHERE group_name IS :g_name")
    public LiveData<List<ForumThread>> loadAllForumThreadsForGroup(String g_name);

    @Insert
    public void addForumThreadToGroup(ForumThread t);

    @Update
    public void updateForumThread(ForumThread t);

    @Delete
    public void deleteForumThread(ForumThread t);
}
