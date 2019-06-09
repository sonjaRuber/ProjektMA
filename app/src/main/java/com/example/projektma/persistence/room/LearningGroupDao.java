package com.example.projektma.persistence.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LearningGroupDao {

    @Query("SELECT * FROM groups")
    public LiveData<List<LearningGroup>> loadAllGroups();

    @Insert
    public void enterGroup(LearningGroup g);

    @Update
    public void updateGroup(LearningGroup g);

    @Delete
    public void removeGroup(LearningGroup g);

}
