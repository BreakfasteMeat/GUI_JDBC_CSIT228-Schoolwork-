package com.example.quizapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentViewController {
    int score = 0;
    int page = 1;
    public Button btnBack;

    public Button btnNext;
    public Label lblQuestion;
    public Button a;
    public Button b;
    public Button c;
    public Button d;

    public Label lblScore;
    public List<Question> questions;
    public Button[] btnChoices;
    public void initialize(){
        questions = new ArrayList<>();
        SQLQuestions.retrieveQuestions(questions);
        lblQuestion.setText("Question 1: " + questions.get(0).question);
        lblScore.setText("Score: " + score + "/" + questions.size());
        List<Button> btnClones = new ArrayList<>();
        btnClones.add(a);
        btnClones.add(b);
        btnClones.add(c);
        btnClones.add(d);
        btnChoices = new Button[]{a,b,c,d};
        for(int i = 0;i < questions.get(0).choices.length;i++){
            int rand = (int)(Math.random() * btnClones.size());
            Button b = btnClones.remove(rand);
            b.setText(questions.get(page-1).choices[i]);
            b.setBackground(Background.fill(Paint.valueOf("WHITE")));
        }
        checkNavigationButton();
    }
    private void checkNavigationButton(){
        btnNext.setDisable(page >= questions.size());
        btnBack.setDisable(page <= 1);
    }
    public void onNavigationCLick(ActionEvent event){
        if(event.getSource() == btnBack){
            page--;
        } else {
            page++;
        }
        checkNavigationButton();



        lblQuestion.setText("Question " + (page) + ": " + questions.get(page-1).question);
        List<Button> btnClones = new ArrayList<>();
        btnClones.add(a);
        btnClones.add(b);
        btnClones.add(c);
        btnClones.add(d);
        for(int i = 0;i < questions.get(page-1).choices.length;i++){
            int rand = (int)(Math.random() * btnClones.size());
            Button b = btnClones.remove(rand);
            if(questions.get(page-1).isAnswered){
                b.setDisable(true);
            } else {
                b.setDisable(false);
            }
            b.setText(questions.get(page-1).choices[i]);
            b.setBackground(Background.fill(Paint.valueOf("WHITE")));
        }
    }

    @FXML
    public void onAnswerClick(ActionEvent event) {
        String ans = ((Button)event.getSource()).getText();
        if(Objects.equals(ans, questions.get(page - 1).choices[0])){
            score++;
            lblScore.setText("Score: " + score + "/" + questions.size());

        }
        for(Button b : btnChoices){
            String item = b.getText();
            if(Objects.equals(item, questions.get(page - 1).choices[0])){
                b.setBackground(Background.fill(Paint.valueOf("GREEN")));
            } else {
                b.setBackground(Background.fill(Paint.valueOf("RED")));
            }
            questions.get(page - 1).isAnswered = true;
        }
    }
}