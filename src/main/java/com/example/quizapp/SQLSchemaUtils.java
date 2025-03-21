package com.example.quizapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLSchemaUtils {
    static String initURL = "jdbc:mysql://localhost:3306";
    static String DBNAME = "csit228f2";
    static String URL = "jdbc:mysql://localhost:3306/csit228f2";
    static String USER = "root";
    static String PASSWORD = "";
    public static void initDB(){
        try(Connection connection = DriverManager.getConnection(initURL,USER,PASSWORD);
            Statement statement = connection.createStatement();){
            String SQL = "CREATE DATABASE IF NOT EXISTS " + DBNAME;
            statement.execute(SQL);
            System.out.println("Database created successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createTables(){
        SQLQuizUsers.tableCheck();
        SQLQuestions.tableCheck();
    }
}
