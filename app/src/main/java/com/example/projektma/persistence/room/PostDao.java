package com.example.projektma.persistence.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {

    @Query("SELECT * FROM posts WHERE thread_id IS :t_id")
    public LiveData<List<Post>> loadAllPostsForForumThread(int t_id);

    @Insert
    public void addPostToForumThread(Post p);

    @Update
    public void updatePost(Post p);

    @Delete
    public void deletePost(Post p);
}
