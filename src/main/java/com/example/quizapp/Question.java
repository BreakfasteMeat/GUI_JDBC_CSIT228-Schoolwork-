package com.example.quizapp;


import java.util.ArrayList;
import java.util.List;

public class Question {
    int id;
    String question;
    String[] choices;
    String answer;
    boolean isAnswered;


    public Question(int id, String question, String... choices) {
        this.question = question;
        this.choices = choices;
        answer = choices[0];
        isAnswered  = false;
        this.id = id;
    }

}

