package main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CompetitonDB {
    static String user = "root";
    static String password = ""; 

    static void createDB() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", user, password);
            System.out.println("Connection Successful");

            String sql = "CREATE DATABASE IF NOT EXISTS competitiondb"; // Use consistent case

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Database Created.");

            stmt.close();
            conn.close(); // Close connection
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void userTable() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", user, password);
            System.out.println("Connection Successful");

            String sql = "CREATE TABLE IF NOT EXISTS user ("
                    + " id INT AUTO_INCREMENT PRIMARY KEY, "
                    + " name VARCHAR(100), "
                    + " age INT, "
                    + " level VARCHAR(100), "
                    + " country VARCHAR(100), "
                    + " scores JSON, "  // Cleaned indentation
                    + " username VARCHAR(100), "
                    + " password VARCHAR(100)" // Removed trailing comma
                    + ")";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Table Created.");

            stmt.close();
            conn.close(); // Close connection
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    static void questionTable() {
    	try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", user, password);
            System.out.println("Connection Successful");

            String sql = "CREATE TABLE IF NOT EXISTS question ("
                    + " id INT AUTO_INCREMENT PRIMARY KEY, "
                    + " level VARCHAR(100), "
                    + " question VARCHAR(100), "
                    + " option1 VARCHAR(100), "
                    + " option2 VARCHAR(100), "
                    + " option3 VARCHAR(100), "
                    + " option4 VARCHAR(100), "
                    + " correct VARCHAR(100) "                    
                    + ")";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Table Created.");

            stmt.close();
            conn.close(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        createDB();
        userTable();
        questionTable();
    }
}
