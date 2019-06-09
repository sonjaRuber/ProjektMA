package com.example.projektma.persistence.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "posts",
        indices = {@Index("p_id"), @Index("thread_id"), @Index("user_name"), @Index("p_timestamp"), @Index("p_content"), @Index("p_is_original_post")},
        foreignKeys = {
                @ForeignKey(entity = ForumThread.class, parentColumns = "t_id", childColumns = "thread_id"),
                @ForeignKey(entity = User.class, parentColumns = "u_name", childColumns = "user_name")
})
public class Post {

    public Post(int id, long timestamp, @NonNull String content, boolean isOriginalPost, int threadId, @NonNull String userName) {
        this.id = id;
        this.timestamp = timestamp;
        this.content = content;
        this.isOriginalPost = isOriginalPost;
        this.threadId = threadId;
        this.userName = userName;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "p_id")
    public int id;

    @NonNull
    @ColumnInfo(name = "p_timestamp") // convert into a date
    public long timestamp;

    @NonNull
    @ColumnInfo(name = "p_content")
    public String content;

    @NonNull
    @ColumnInfo(name = "p_is_original_post")
    public boolean isOriginalPost;


    // foreign keys
    @NonNull
    @ColumnInfo(name = "thread_id")
    public int threadId;

    @NonNull
    @ColumnInfo(name = "user_name")
    public String userName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id &&
                timestamp == post.timestamp &&
                isOriginalPost == post.isOriginalPost &&
                threadId == post.threadId &&
                content.equals(post.content) &&
                userName.equals(post.userName);
    }

}
