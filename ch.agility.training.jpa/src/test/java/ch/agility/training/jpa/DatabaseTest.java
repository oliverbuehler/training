package ch.agility.training.jpa;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import ch.agility.training.jpa.DatabaseHelper.DatabaseMode;

public class DatabaseTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        DatabaseHelper.startDatabase(DatabaseMode.SERVER);
        DatabaseHelper.executeSql("create schema if not exists jpa");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        DatabaseHelper.stopDatabase();
    }

}
