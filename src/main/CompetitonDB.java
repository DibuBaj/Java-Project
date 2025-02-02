package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CompetitonDB {

    static void createDB(Connection conn) {
        String sql = "CREATE DATABASE IF NOT EXISTS COMPETITIONDB";
        
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Database Created.");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = ""; 

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Successful");

            createDB(conn);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
