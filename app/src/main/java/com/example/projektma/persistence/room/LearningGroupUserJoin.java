package com.example.projektma.persistence.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "group_user_join",
        indices = {@Index("user_name"), @Index("group_name")},
        primaryKeys = {"group_name", "user_name"},
        foreignKeys = {
                @ForeignKey(entity = LearningGroup.class, parentColumns = "g_name", childColumns = "group_name"),
                @ForeignKey(entity =  User.class, parentColumns = "u_name", childColumns =  "user_name")
        }
)
public class LearningGroupUserJoin {

    public LearningGroupUserJoin(@NonNull String groupName, @NonNull String userName) {
        this.groupName = groupName;
        this.userName = userName;
    }

    @NonNull
    @ColumnInfo(name = "group_name")
    public String groupName;
    @NonNull
    @ColumnInfo(name = "user_name")
    public String userName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningGroupUserJoin that = (LearningGroupUserJoin) o;
        return groupName.equals(that.groupName) &&
                userName.equals(that.userName);
    }

}
