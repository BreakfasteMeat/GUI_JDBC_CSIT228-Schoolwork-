package com.example.quizapp;

public class SQLSchemaUtils {

    public static void createTables(){
        SQLQuizUsers.tableCheck();
        SQLQuestions.tableCheck();
    }
}
