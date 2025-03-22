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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(question);
        sb.append("\n");
        char character = 'A';
        for(int i = 0; i < choices.length; i++) {
            sb.append(character++);
            sb.append(". ");
            sb.append(choices[i]);
            sb.append("\n");
        }
        return sb.toString();
    }
}

