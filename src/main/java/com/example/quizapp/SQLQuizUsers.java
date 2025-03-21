package com.example.quizapp;

import java.sql.*;

public class SQLQuizUsers {
    static String initURL = "jdbc:mysql://localhost:3306";
    static String DBNAME = "csit228f2";
    static String URL = "jdbc:mysql://localhost:3306/csit228f2";
    static String USER = "root";
    static String PASSWORD = "";
    static String tablename_student = "tblquizstudents";
    static String tablename_teacher = "tblquizteachers";
    public static void tableCheck(){
        String createTableStudentSQL = "CREATE TABLE IF NOT EXISTS " + tablename_student + "(" +
                " id INT AUTO_INCREMENT PRIMARY KEY," +
                " name VARCHAR(255) NOT NULL," +
                " score INT DEFAULT 0," +
                " isFinished BOOLEAN DEFAULT FALSE" +
                ");";

        String createTableTeacherSQL = "CREATE TABLE IF NOT EXISTS " + tablename_teacher + "(" +
                " id INT AUTO_INCREMENT PRIMARY KEY," +
                " name VARCHAR(255) NOT NULL" +
                ");";
        try(
                Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
                Statement statement = connection.createStatement();
                ){
            statement.execute(createTableStudentSQL);
            System.out.println("Student Table created");
            statement.execute(createTableTeacherSQL);
            System.out.println("Teacher Table created");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static boolean retrieveUserExists(String username, String tablename){
        try(Connection connection = DriverManager.getConnection(URL,USER, PASSWORD);
            Statement statement = connection.createStatement();
        ) {
            String query = "SELECT * FROM " + tablename +" WHERE name = '" + username + "'";
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addUser(String username, String table){
        try(Connection connection = DriverManager.getConnection(URL,USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + table +"(name) VALUES (?)"
            )) {

            statement.setString(1,username);

            int rowsaffected = statement.executeUpdate();
            if(rowsaffected > 0){
                System.out.println("Successfully added " + rowsaffected + " row/s");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addQuestion(String question_id){
        String colName = "question_" + question_id;
        String addColumnQuery = "ALTER TABLE " + tablename_student + " ADD COLUMN " + colName + " BOOLEAN DEFAULT NULL";
        try(Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
        Statement statement = connection.createStatement();){
            statement.executeUpdate(addColumnQuery);
            System.out.println("Successfully added " + question_id + " column");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void removeQuestion(String question_id){
        String colName = "question_" + question_id;
        String addColumnQuery = "ALTER TABLE" + tablename_student + " DELETE COLUMN " + colName;
        try(Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement();){
            statement.executeUpdate(addColumnQuery);
            System.out.println("Successfully removed " + question_id + " column");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void studentAnswerQuestion(String question_id, boolean isCorrect){

    }

}
