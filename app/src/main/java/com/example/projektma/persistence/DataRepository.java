package com.example.projektma.persistence;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import com.example.projektma.persistence.room.DatabaseGroups;
import com.example.projektma.persistence.room.ForumThread;
import com.example.projektma.persistence.room.ForumThreadDao;
import com.example.projektma.persistence.room.LearningGroup;
import com.example.projektma.persistence.room.LearningGroupDao;
import com.example.projektma.persistence.room.Post;
import com.example.projektma.persistence.room.PostDao;
import com.example.projektma.persistence.room.User;
import com.example.projektma.persistence.room.UserDao;

class DataRepository {

    private LearningGroupDao mLearningGroupDao;
    private ForumThreadDao mForumThreadDao;
    private PostDao mPostDao;
    private UserDao mUserDao;


    DataRepository(Application application) {
        DatabaseGroups db = DatabaseGroups.getInstance(application);
        mLearningGroupDao = db.getGroupDao();
        mForumThreadDao = db.getThreadDao();
        mPostDao = db.getPostDao();
        mUserDao = db.getUserDao();
    }

    LiveData<List<LearningGroup>> getMSavedLearningGroups() {
        return mLearningGroupDao.loadAllGroups();
    }

    LiveData<List<ForumThread>> loadAllForumThreadsForGroup(LearningGroup g) {
        return mForumThreadDao.loadAllForumThreadsForGroup(g.name);
    }

    LiveData<List<User>> loadUsersForGroup(LearningGroup g) {
        return mUserDao.loadUsersForGroup(g.name);
    }

    LiveData<List<Post>> loadAllPostsForForumThread(ForumThread t) {
        return mPostDao.loadAllPostsForForumThread(t.id);
    }


    void addGroup(LearningGroup g) {
        // inserts group object on a separate thread
        new addGroupAsyncTask(mLearningGroupDao).execute(g);
    }

    private static class addGroupAsyncTask extends AsyncTask<LearningGroup, Void, Void> {

        private LearningGroupDao mAsyncTaskDao;

        addGroupAsyncTask(LearningGroupDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final LearningGroup... params) {
            mAsyncTaskDao.enterGroup(params[0]);

            return null;
        }
    }
}
