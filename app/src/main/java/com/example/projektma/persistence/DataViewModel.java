package com.example.projektma.persistence;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.List;

import com.example.projektma.persistence.room.ForumThread;
import com.example.projektma.persistence.room.LearningGroup;
import com.example.projektma.persistence.room.Post;
import com.example.projektma.persistence.room.User;

public class DataViewModel extends AndroidViewModel {

    private DataRepository mRepository;

    private LiveData<List<LearningGroup>> mSavedLearningGroups;
    private HashMap<LearningGroup, LiveData<List<ForumThread>>> mapMSavedForumThreads;
    private HashMap<ForumThread, LiveData<List<Post>>> mapMSavedPosts;
    private HashMap<LearningGroup, LiveData<List<User>>> mapMSavedUsers;

    public DataViewModel (Application application) {
        super(application);
        mRepository = new DataRepository(application);

        mapMSavedForumThreads = new HashMap<>();
        mapMSavedPosts = new HashMap<>();
        mapMSavedUsers = new HashMap<>();
    }

    public LiveData<List<LearningGroup>> getMSavedLearningGroups() {
        if (mSavedLearningGroups == null) {
            mSavedLearningGroups = mRepository.getMSavedLearningGroups();
        }
        return mSavedLearningGroups;
    }

    public LiveData<List<ForumThread>> getMSavedForumThreadsForGroup(LearningGroup g) {
        if (!mapMSavedForumThreads.containsKey(g)) {
            mapMSavedForumThreads.put(g, mRepository.loadAllForumThreadsForGroup(g));
        }
        return mapMSavedForumThreads.get(g);
    }

    public LiveData<List<User>> getMSavedUsersForGroup(LearningGroup g) {
        if (!mapMSavedUsers.containsKey(g)) {
            mapMSavedUsers.put(g, mRepository.loadUsersForGroup(g));
        }
        return mapMSavedUsers.get(g);
    }

    public LiveData<List<Post>> getMSavedPostsForForumThread(ForumThread t) {
        if (!mapMSavedPosts.containsKey(t)) {
            mapMSavedPosts.put(t, mRepository.loadAllPostsForForumThread(t));
        }
        return mapMSavedPosts.get(t);
    }

    public void submitCreateGroup(LearningGroup g) {
        mRepository.addGroup(g);
        // to be implemented
    }

    public void submitCreateForumThread(ForumThread t) {
        // to be implemented
    }

    public void submitCreatePost(Post p) {
        // to be implemented
    }

    public void submitJoinGroup(LearningGroup g) {
        // to be implemented
    }
}