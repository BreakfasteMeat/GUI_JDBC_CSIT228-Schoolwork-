package com.example.quizapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public static boolean retrieveUserExists(String username, String tablename) {
        String query = "SELECT 1 FROM " + tablename + " WHERE BINARY name = ? LIMIT 1";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a record exists
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
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
    public static void studentAnswerQuestion(String question_id, boolean isCorrect, String student_name){
        String SQLQuery = "UPDATE " + tablename_student + " SET " + question_id + " = " + isCorrect + " WHERE (name = \"" + student_name + "\");";
        try (
                Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
                Statement statement = connection.createStatement();
                ){
            statement.executeUpdate(SQLQuery);
            System.out.println("Successfully updated " + question_id + " column");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Boolean> getStudentAnswers(String username, List<Question> questionList){
        List<Boolean> booleanList = new ArrayList<>();
        for(Question question : questionList){
            booleanList.add(getStudentQuestionIsCorrect(question.id, username));
        }
        return booleanList;
    }
    private static Boolean getStudentQuestionIsCorrect(int question_id, String username){
        String query = "SELECT " + ("question_" + question_id) + " FROM " + tablename_student + " WHERE (name = \"" + username + "\");";
        try(
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement();
                ){
            ResultSet rst = statement.executeQuery(query);
            if(rst.next()){

                Boolean isCorrect = rst.getBoolean("question_" + question_id);
                if(rst.wasNull()) return null;
                return isCorrect;
            } else {
                System.out.println("Did not find anything, damn");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static int getStudentScore(String username){
        String query = "SELECT" + " score " + "FROM " + tablename_student + " WHERE (name = \"" + username + "\");";
        try(
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement();
                ){
            ResultSet rst = statement.executeQuery(query);
            if(rst.next()){
                System.out.println("Returning score...");
                return rst.getInt("score");
            } else {
                return 0;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public static void updateStudentScore(String username, int score){
        String SQL = "UPDATE " + tablename_student + " SET score = " + score + " WHERE (name = \"" + username + "\");";
        try(
                Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
                Statement statement = connection.createStatement();
        ){
            statement.executeUpdate(SQL);
            System.out.println("Successfully updated " + username + " score");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void removeQuestionColumn(int question_id){
        String SQLQuery = "ALTER TABLE " + tablename_student + " DROP COLUMN question_" + question_id;
        try(
                Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
                Statement statement = connection.createStatement();
                ){
            statement.executeUpdate(SQLQuery);
            System.out.println("Successfully removed question_" + question_id + " column");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
