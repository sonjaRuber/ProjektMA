package com.example.projektma;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;

import java.util.List;

import com.example.projektma.persistence.room.DatabaseGroups;
import com.example.projektma.persistence.room.ForumThread;
import com.example.projektma.persistence.room.LearningGroup;
import com.example.projektma.persistence.room.LearningGroupDao;
import com.example.projektma.persistence.room.LearningGroupUserJoin;
import com.example.projektma.persistence.room.LearningGroupUserJoinDao;
import com.example.projektma.persistence.room.Post;
import com.example.projektma.persistence.room.PostDao;
import com.example.projektma.persistence.room.ForumThreadDao;
import com.example.projektma.persistence.room.User;
import com.example.projektma.persistence.room.UserDao;

import static org.junit.Assert.*;

/**
 * Insert and Query operations have been tested, but not Update and Delete yet.
 */
@RunWith(AndroidJUnit4.class)
public class RoomDatabaseGroupsTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private DatabaseGroups db;

    private LearningGroupDao mLearningGroupDao;
    private UserDao mUserDao;
    private ForumThreadDao mForumThreadDao;
    private PostDao mPostDao;
    private LearningGroupUserJoinDao mLearningGroupUserJoinDao;

    @Before
    public void createDb() {

        Context context = ApplicationProvider.getApplicationContext();

        db = Room.inMemoryDatabaseBuilder(context, DatabaseGroups.class)
                .addMigrations(DatabaseGroups.MIGRATION_1_2)
                .allowMainThreadQueries()
                .build();

        mLearningGroupDao = db.getGroupDao();
        mUserDao = db.getUserDao();
        mForumThreadDao = db.getThreadDao();
        mPostDao = db.getPostDao();
        mLearningGroupUserJoinDao = db.getGroupUserJoinDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void buildOneGroup() throws Exception {

        LearningGroup g1 = new LearningGroup("Woodworking", "wood craft handcraft", "We make sculptures.");
        mLearningGroupDao.enterGroup(g1);

        LearningGroup ge2 = LiveDataTestHelper.getValue(mLearningGroupDao.loadAllGroups()).get(0);

        assertEquals(g1, ge2);
    }

    @Test
    public void buildMultipleGroups() throws Exception {

        LearningGroup g1 = new LearningGroup("Woodworking", "wood craft handcraft", "We make sculptures.");
        LearningGroup g2 = new LearningGroup("Metalworking", "metal craft handcraft", "We make sculptures.");
        LearningGroup g3 = new LearningGroup("The Guitarists", "music sound guitar acoustic", "Music is life.");
        mLearningGroupDao.enterGroup(g1);
        mLearningGroupDao.enterGroup(g2);
        mLearningGroupDao.enterGroup(g3);

        List<LearningGroup> learningGroups = LiveDataTestHelper.getValue(mLearningGroupDao.loadAllGroups());

        assertEquals(g1, learningGroups.get(0));
        assertEquals(g2, learningGroups.get(1));
        assertEquals(g3, learningGroups.get(2));
    }

    @Test
    public void buildGroupWithOneThread() throws Exception {

        LearningGroup g1 = new LearningGroup("Woodworking", "wood craft handcraft", "We make sculptures.");
        mLearningGroupDao.enterGroup(g1);

        ForumThread t1 = new ForumThread(1, "New idea", "Woodworking");
        mForumThreadDao.addForumThreadToGroup(t1);

        ForumThread te1 = LiveDataTestHelper.getValue(mForumThreadDao.loadAllForumThreadsForGroup("Woodworking")).get(0);
        assertEquals(t1, te1);

    }

    @Test
    public void buildGroupWithMultipleThreads() throws Exception {

        LearningGroup g1 = new LearningGroup("Woodworking", "wood craft handcraft", "We make sculptures.");
        mLearningGroupDao.enterGroup(g1);

        ForumThread t1 = new ForumThread(1, "New idea", "Woodworking");
        ForumThread t2 = new ForumThread(2, "Announcement", "Woodworking");
        ForumThread t3 = new ForumThread(3, "How does this work?", "Woodworking");
        mForumThreadDao.addForumThreadToGroup(t1);
        mForumThreadDao.addForumThreadToGroup(t2);
        mForumThreadDao.addForumThreadToGroup(t3);

        List<ForumThread> threads = LiveDataTestHelper.getValue(mForumThreadDao.loadAllForumThreadsForGroup("Woodworking"));

        assertEquals(t1, threads.get(0));
        assertEquals(t2, threads.get(1));
        assertEquals(t3, threads.get(2));
    }

    @Test
    public void buildGroupWithOneUser() throws Exception {

        LearningGroup g1 = new LearningGroup("Woodworking", "wood craft handcraft", "We make sculptures.");
        mLearningGroupDao.enterGroup(g1);

        User u1 = new User("Max");
        mUserDao.addUser(u1);
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Woodworking", "Max"));

        User ue1 = LiveDataTestHelper.getValue(mUserDao.loadUsersForGroup("Woodworking")).get(0);
        assertEquals(u1, ue1);
    }

    @Test
    public void buildGroupWithMultipleUsers() throws Exception {

        LearningGroup g1 = new LearningGroup("Woodworking", "wood craft handcraft", "We make sculptures.");
        mLearningGroupDao.enterGroup(g1);

        User u1 = new User("Max");
        User u2 = new User("xX_Mustermann_Xx");
        User u3 = new User("herrmann24");
        mUserDao.addUser(u1);
        mUserDao.addUser(u2);
        mUserDao.addUser(u3);
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Woodworking", "Max"));
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Woodworking", "xX_Mustermann_Xx"));
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Woodworking", "herrmann24"));

        List<User> users = LiveDataTestHelper.getValue(mUserDao.loadUsersForGroup("Woodworking"));

        assertEquals(u1, users.get(0));
        assertEquals(u2, users.get(1));
        assertEquals(u3, users.get(2));
    }

    @Test
    public void buildThreadWithOnePost() throws Exception {

        LearningGroup g1 = new LearningGroup("Woodworking", "wood craft handcraft", "We make sculptures.");
        mLearningGroupDao.enterGroup(g1);

        User u1 = new User("Max");
        mUserDao.addUser(u1);
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Woodworking", "Max"));

        ForumThread t1 = new ForumThread(1, "New idea", "Woodworking");
        mForumThreadDao.addForumThreadToGroup(t1);

        Post p1 = new Post(1,100L, "Let's make a birdhouse.", true, 1, "Max");
        mPostDao.addPostToForumThread(p1);

        Post pe1 = LiveDataTestHelper.getValue(mPostDao.loadAllPostsForForumThread(1)).get(0);
        assertEquals(p1, pe1);
    }

    @Test
    public void buildThreadWithMultiplePosts() throws Exception {

        LearningGroup g1 = new LearningGroup("Woodworking", "wood craft handcraft", "We make sculptures.");
        mLearningGroupDao.enterGroup(g1);

        User u1 = new User("Max");
        User u2 = new User("xX_Mustermann_Xx");
        User u3 = new User("herrmann24");
        mUserDao.addUser(u1);
        mUserDao.addUser(u2);
        mUserDao.addUser(u3);
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Woodworking", "Max"));
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Woodworking", "xX_Mustermann_Xx"));
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Woodworking", "herrmann24"));

        ForumThread t1 = new ForumThread(1, "New idea", "Woodworking");
        mForumThreadDao.addForumThreadToGroup(t1);

        Post p1 = new Post(1,100L, "Let's make a birdhouse.", true, 1, "Max");
        Post p2 = new Post(2,120L, "i agree", false, 1, "xX_Mustermann_Xx");
        Post p3 = new Post(3,300L, "how about no", false, 1, "herrmann24");
        mPostDao.addPostToForumThread(p1);
        mPostDao.addPostToForumThread(p2);
        mPostDao.addPostToForumThread(p3);

        List<Post> posts = LiveDataTestHelper.getValue(mPostDao.loadAllPostsForForumThread(1));
        assertEquals(p1, posts.get(0));
        assertEquals(p2, posts.get(1));
        assertEquals(p3, posts.get(2));
    }

    @Test
    public void buildMultipleGroupsWithMultipleThreadsWithMultiplePosts() throws Exception {

        // Semi-full simulation

        LearningGroup g1 = new LearningGroup("Woodworking", "wood craft handcraft", "We make sculptures.");
        LearningGroup g2 = new LearningGroup("Metalworking", "metal craft handcraft", "We make sculptures.");
        LearningGroup g3 = new LearningGroup("The Guitarists", "music sound guitar acoustic", "Music is life.");
        mLearningGroupDao.enterGroup(g1); // u1, u2, u5
        mLearningGroupDao.enterGroup(g2); // u2, u3
        mLearningGroupDao.enterGroup(g3); // u3, u5

        User u1 = new User("Max");
        User u2 = new User("xX_Mustermann_Xx");
        User u3 = new User("herrmann24");
        User u4 = new User("krasser_mensch");
        User u5 = new User("vollkornbrot83");
        mUserDao.addUser(u1);
        mUserDao.addUser(u2);
        mUserDao.addUser(u3);
        mUserDao.addUser(u4);
        mUserDao.addUser(u5);
        // Woodworking
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Woodworking", "Max"));
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Woodworking", "xX_Mustermann_Xx"));
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Woodworking", "vollkornbrot83"));
        // Metalworking
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Metalworking", "xX_Mustermann_Xx"));
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("Metalworking", "herrmann24"));
        // The Guitarists
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("The Guitarists", "herrmann24"));
        mLearningGroupUserJoinDao.addUserToLearningGroup(new LearningGroupUserJoin("The Guitarists", "vollkornbrot83"));

        ForumThread t1 = new ForumThread(1, "New idea", "Woodworking");
        ForumThread t2 = new ForumThread(2, "Old idea", "Woodworking");
        ForumThread t3 = new ForumThread(3, "I'm leaving.", "Metalworking");
        ForumThread t4 = new ForumThread(4, "Recruit more members?", "Metalworking");
        ForumThread t5 = new ForumThread(5, "Better technique", "The Guitarists");
        ForumThread t6 = new ForumThread(6, "Sunday Practice", "The Guitarists");
        mForumThreadDao.addForumThreadToGroup(t1);
        mForumThreadDao.addForumThreadToGroup(t2);
        mForumThreadDao.addForumThreadToGroup(t3);
        mForumThreadDao.addForumThreadToGroup(t4);
        mForumThreadDao.addForumThreadToGroup(t5);
        mForumThreadDao.addForumThreadToGroup(t6);

        Post p1 = new Post(1,100L, "Let's make a birdhouse.", true, 1, "Max");
        Post p2 = new Post(2,120L, "i agree", false, 1, "vollkornbrot83");
        Post p3 = new Post(3,300L, "how about no", true, 3, "xX_Mustermann_Xx");
        Post p4 = new Post(4,300L, "this should work", true, 4, "herrmann24");
        Post p5 = new Post(5,300L, "I suggest...", true, 5, "herrmann24");
        Post p6 = new Post(6,300L, "lol", true, 6, "herrmann24");
        Post p7 = new Post(7,300L, "this is bad", false, 6, "herrmann24");
        Post p8 = new Post(8,300L, "how did u do this?", false, 6, "vollkornbrot83");
        Post p9 = new Post(9,300L, "sample text", false, 6, "vollkornbrot83");
        mPostDao.addPostToForumThread(p1);
        mPostDao.addPostToForumThread(p2);
        mPostDao.addPostToForumThread(p3);
        mPostDao.addPostToForumThread(p4);
        mPostDao.addPostToForumThread(p5);
        mPostDao.addPostToForumThread(p6);
        mPostDao.addPostToForumThread(p7);
        mPostDao.addPostToForumThread(p8);
        mPostDao.addPostToForumThread(p9);

        List<LearningGroup> groups = LiveDataTestHelper.getValue(mLearningGroupDao.loadAllGroups());

        List<ForumThread> threads1 = LiveDataTestHelper.getValue(mForumThreadDao.loadAllForumThreadsForGroup(groups.get(0).name));
        List<ForumThread> threads2 = LiveDataTestHelper.getValue(mForumThreadDao.loadAllForumThreadsForGroup(groups.get(1).name));
        List<ForumThread> threads3 = LiveDataTestHelper.getValue(mForumThreadDao.loadAllForumThreadsForGroup(groups.get(2).name));

        List<Post> posts1_1 = LiveDataTestHelper.getValue(mPostDao.loadAllPostsForForumThread(threads1.get(0).id));
        List<Post> posts1_2 = LiveDataTestHelper.getValue(mPostDao.loadAllPostsForForumThread(threads1.get(1).id));
        List<Post> posts2_1 = LiveDataTestHelper.getValue(mPostDao.loadAllPostsForForumThread(threads2.get(0).id));
        List<Post> posts2_2 = LiveDataTestHelper.getValue(mPostDao.loadAllPostsForForumThread(threads2.get(1).id));
        List<Post> posts3_1 = LiveDataTestHelper.getValue(mPostDao.loadAllPostsForForumThread(threads3.get(0).id));
        List<Post> posts3_2 = LiveDataTestHelper.getValue(mPostDao.loadAllPostsForForumThread(threads3.get(1).id));

        List<User> userlist1 = LiveDataTestHelper.getValue(mUserDao.loadUsersForGroup(groups.get(0).name));
        List<User> userlist2 = LiveDataTestHelper.getValue(mUserDao.loadUsersForGroup(groups.get(1).name));
        List<User> userlist3 = LiveDataTestHelper.getValue(mUserDao.loadUsersForGroup(groups.get(2).name));

        assertEquals(g1, groups.get(0));
        assertEquals(g2, groups.get(1));
        assertEquals(g3, groups.get(2));

        assertEquals(t1, threads1.get(0));
        assertEquals(t2, threads1.get(1));
        assertEquals(t3, threads2.get(0));
        assertEquals(t4, threads2.get(1));
        assertEquals(t5, threads3.get(0));
        assertEquals(t6, threads3.get(1));

        assertEquals(p1, posts1_1.get(0));
        assertEquals(p2, posts1_1.get(1));
        assertTrue(posts1_2.isEmpty());
        assertEquals(p3, posts2_1.get(0));
        assertEquals(p4, posts2_2.get(0));
        assertEquals(p5, posts3_1.get(0));
        assertEquals(p6, posts3_2.get(0));
        assertEquals(p7, posts3_2.get(1));
        assertEquals(p8, posts3_2.get(2));
        assertEquals(p9, posts3_2.get(3));

        // group 1 members
        assertEquals(u1, userlist1.get(0));
        assertEquals(u2, userlist1.get(1));
        assertEquals(u5, userlist1.get(2));
        // group 2 members
        assertEquals(u2, userlist2.get(0));
        assertEquals(u3, userlist2.get(1));
        // group 3 members
        assertEquals(u3, userlist3.get(0));
        assertEquals(u5, userlist3.get(1));
    }

}