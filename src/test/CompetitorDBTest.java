package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.db.CompetitonDB;

public class CompetitorDBTest {
    static String user = "root";
    static String password = "";
    static String url = "jdbc:mysql://localhost:3306/competitiondb";

    @BeforeAll
    static void setup() {
        CompetitonDB.createDB();
        CompetitonDB.userTable();
        CompetitonDB.questionTable();
    }

    @Test
    void testDatabaseExists() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", user, password);
             ResultSet rs = conn.getMetaData().getCatalogs()) {
            boolean dbExists = false;
            while (rs.next()) {
                if ("competitiondb".equals(rs.getString(1))) {
                    dbExists = true;
                    break;
                }
            }
            assertTrue(dbExists, "Database does not exist");
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    @Test
    void testUserTableExists() {
        assertTrue(checkTableExists("user"), "User table does not exist");
    }

    @Test
    void testQuestionTableExists() {
        assertTrue(checkTableExists("question"), "Question table does not exist");
    }

    private boolean checkTableExists(String tableName) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            return rs.next();
        } catch (SQLException e) {
            fail("Table check failed: " + e.getMessage());
            return false;
        }
    }
}
