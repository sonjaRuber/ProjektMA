package com.example.projektma.persistence.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT u_name FROM users LEFT JOIN group_user_join ON users.u_name = group_user_join.user_name " +
           "WHERE group_user_join.group_name IS :g_name")
    public LiveData<List<User>> loadUsersForGroup(String g_name);

    @Insert
    public void addUser(User u);

    @Update
    public void updateUser(User u);

    @Delete
    public void deleteUser(User u);

}
