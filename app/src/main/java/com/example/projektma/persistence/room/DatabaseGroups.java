package com.example.projektma.persistence.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {LearningGroup.class, User.class, ForumThread.class, Post.class, LearningGroupUserJoin.class}, version=2, exportSchema = true)
public abstract class DatabaseGroups extends RoomDatabase {

    private static DatabaseGroups INSTANCE;

    public abstract LearningGroupDao getGroupDao();
    public abstract UserDao getUserDao();
    public abstract PostDao getPostDao();
    public abstract ForumThreadDao getThreadDao();
    public abstract LearningGroupUserJoinDao getGroupUserJoinDao();

    private static final Object accessMonitor = new Object();

    public static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // SQLite API -> Room
        }
    };

    /*
    public static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // sample migration
        }
    };
    */

    public static DatabaseGroups getInstance(Context context) {
        synchronized (accessMonitor) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        DatabaseGroups.class, "Sample1.db")
                        //.addMigrations(MIGRATION_1_2)
                        .build();
            }
            return INSTANCE;
        }
    }

}
