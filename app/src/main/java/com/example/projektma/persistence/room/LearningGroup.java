package com.example.projektma.persistence.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "groups")
public class LearningGroup {

    public LearningGroup(@NonNull String name, String tags, String description) {
        this.name = name;
        this.tags = tags;
        this.description = description;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "g_name")
    public String name;

    // room cannot save arrays/collections, use space to separate tags
    @ColumnInfo(name = "g_tags")
    @NonNull
    public String tags;

    @ColumnInfo(name = "g_description")
    @NonNull
    public String description;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningGroup learningGroup = (LearningGroup) o;
        return name.equals(learningGroup.name) &&
                tags.equals(learningGroup.tags) &&
                description.equals(learningGroup.description);
    }

}
