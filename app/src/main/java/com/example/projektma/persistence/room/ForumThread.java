package com.example.projektma.persistence.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "threads",
        indices = {@Index("t_id"), @Index("t_title"), @Index("group_name")},
        foreignKeys = @ForeignKey(entity = LearningGroup.class, parentColumns = "g_name", childColumns = "group_name")
)
public class ForumThread {

    public ForumThread(int id, @NonNull String title, @NonNull String groupName) {
        this.id = id;
        this.title = title;
        this.groupName = groupName;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "t_id")
    public int id;

    @NonNull
    @ColumnInfo(name = "t_title")
    public String title;

    // foreign keys
    @NonNull
    @ColumnInfo(name = "group_name")
    public String groupName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForumThread forumThread = (ForumThread) o;
        return id == forumThread.id &&
                title.equals(forumThread.title) &&
                groupName.equals(forumThread.groupName);
    }

}
