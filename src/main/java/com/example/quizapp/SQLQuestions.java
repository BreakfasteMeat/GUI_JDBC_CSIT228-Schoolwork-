package com.example.quizapp;

import java.sql.*;
import java.util.List;

public class SQLQuestions {
    static String URL = "jdbc:mysql://localhost:3306/csit228f2";
    static String USER = "root";
    static String PASSWORD = "";
    static String tablename = "tblquestions";
    public static void tableCheck(){
        String createTableQuestionsSQL = "CREATE TABLE IF NOT EXISTS " + tablename + "(" +
                " id INT AUTO_INCREMENT PRIMARY KEY," +
                " question VARCHAR(255) NOT NULL," +
                " a VARCHAR(255) NOT NULL," +
                " b VARCHAR(255) NOT NULL," +
                " c VARCHAR(255) NOT NULL," +
                " d VARCHAR(255) NOT NULL" +
                ");";

        try(
                Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
                Statement statement = connection.createStatement();
        ){
            statement.execute(createTableQuestionsSQL);
            System.out.println("Questions Table created");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void addQuestion(String question, String... choices){
        String URL = "jdbc:mysql://localhost:3306/csit228f2";
        String USER = "root";
        String PASSWORD = "";
        try(Connection connection = DriverManager.getConnection(URL,USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO tblquestions(question,a,b,c,d) VALUES (?,?,?,?,?)"
            )) {

            statement.setString(1,question);
            statement.setString(2,choices[0]);
            statement.setString(3,choices[1]);
            statement.setString(4,choices[2]);
            statement.setString(5,choices[3]);

            int rowsaffected = statement.executeUpdate();

            SQLQuizUsers.addQuestion(getLatestID(statement));

            if(rowsaffected > 0){
                System.out.println("Successfully added " + rowsaffected + " row/s");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getLatestID(Statement statement) throws SQLException {
        String getMaxIdQuery = "SELECT MAX(id) FROM tblquestions";
        try(ResultSet resultSet = statement.executeQuery(getMaxIdQuery)){
            if(resultSet.next()){
                return resultSet.getString(1);
            }
        }
        return null;
    }

    public static void addQuestion(Question q){
        addQuestion(q.question, q.choices);
    }
    public static void retrieveQuestions(List<Question> questionList){
        try(Connection connection = DriverManager.getConnection(URL,USER, PASSWORD);
            Statement statement = connection.createStatement();
        ) {
            String query = "SELECT * FROM tblquestions";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String question = resultSet.getString("question");
                String a = resultSet.getString("a");
                String b = resultSet.getString("b");
                String c = resultSet.getString("c");
                String d = resultSet.getString("d");
                questionList.add(new Question(id,question,a,b,c,d));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
