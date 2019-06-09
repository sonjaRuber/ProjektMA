package com.example.projektma.persistence.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

/**
 * Might be removed later as it's unnecessary for local database
 */
@Dao
public interface LearningGroupUserJoinDao {

    @Insert
    public void addUserToLearningGroup(LearningGroupUserJoin j);

    @Update
    public void updateUser(LearningGroupUserJoin j);

    @Delete
    public void deleteUser(LearningGroupUserJoin j);
}
