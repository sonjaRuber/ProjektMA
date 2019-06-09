package com.example.projektma;

import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import com.example.projektma.persistence.room.DatabaseGroups;

import static com.example.projektma.persistence.room.DatabaseGroups.MIGRATION_1_2;

@RunWith(AndroidJUnit4.class)
public class MigrationTest {

    private static final String TEST_DB = "migration-test";
    private static DatabaseGroups db;

    @Rule
    public MigrationTestHelper helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
            DatabaseGroups.class.getCanonicalName(),
            new FrameworkSQLiteOpenHelperFactory());

    @Before
    public void setUp() throws Exception {
        db = DatabaseGroups.getInstance(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void migrate1To2() throws IOException {

        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 1);

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        //db.execSQL("CREATE TABLE test1 (column1 data1)");

        // Prepare for the next version.
        db.close();

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2);

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
    }
}
