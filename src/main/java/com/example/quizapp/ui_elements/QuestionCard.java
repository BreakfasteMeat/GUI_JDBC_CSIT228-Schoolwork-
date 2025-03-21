package com.example.quizapp.ui_elements;

import javafx.scene.control.Button;

public class QuestionCard extends Button{
    int index;
    public QuestionCard(String text,int index){
        super(text);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
